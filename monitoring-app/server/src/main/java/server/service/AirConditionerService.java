package server.service;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import server.data.EquipmentData;
import server.data.ReleData;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Scope(value = "singleton")
public class AirConditionerService {

    private final Logger logger = LoggerFactory.getLogger(AirConditionerService.class);

    private static final String COMMAND_STATUS = "Received";

    @Autowired
    private PushService pushService;

    @Autowired
    private EquipmentDataService equipmentService;

    @Autowired
    private ReleDataService releDataService;

    @Autowired
    private AlertService alertService;

    @Autowired
    private SnapshotService snapshotService;

    @Autowired
    private EquipmentOnlineMonitor onlineMonitor;

    @Autowired
    @Qualifier("application_pool")
    private TaskExecutor taskExecutor;

    private AtomicBoolean isReseting = new AtomicBoolean(false);

    @Value("${cond.id}")
    private String condId;

    @Value("${serial.port}")
    private String portId;
    private SerialPort serialPort;
    private ReleData rele;

    public void init() throws SerialPortException {
        serialPort = new SerialPort(portId);
        serialPort.openPort();
        serialPort.setParams(SerialPort.BAUDRATE_9600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
        serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
                SerialPort.FLOWCONTROL_RTSCTS_OUT);
        serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
        rele = new ReleData(condId, false);
        releDataService.save(rele);
        pushService.send(rele);
        power(false);
    }

    public void power(Boolean state) {
        try {
            serialPort.writeString(state ? "1" : "0");
            rele.setPower(state);
            releDataService.save(rele);
            pushService.send(rele);
        } catch (SerialPortException ex) {
            logger.error("Could not write to serial port", ex);
            alertService.sendMessage("Serial port is down:\n" + ex);
            reset();
        }
    }

    private class PortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR() && event.getEventValue() > 0) {
                try {
                    String data = serialPort.readString(event.getEventValue());
                    String[] commands = data.split("\n");
                    for (String command : commands) {
                        if (!command.startsWith(COMMAND_STATUS)) {
                            String[] result = command.split(",");
                            EquipmentData equipment = new EquipmentData(condId, true, Float.parseFloat(result[0]), Float.parseFloat(result[1]));
                            equipmentService.save(equipment);
                            snapshotService.record(equipment);
                            onlineMonitor.record(equipment);
                            alertService.validate(equipment);
                            pushService.send(equipment);
                        }
                    }
                } catch (SerialPortException e) {
                    logger.error("Could not read from serial port 1", e);
                    alertService.sendMessage("Serial port is down:\n" + e);
                    reset();
                } catch (Exception e) {
                    logger.error("Could not read from serial port 2", e);
                }
            }
        }
    }

    public void destroy() throws SerialPortException {
        if (serialPort != null && serialPort.isOpened()) {
            serialPort.closePort();
            serialPort = null;
        }
    }

    @PostConstruct
    public void reset() {
        taskExecutor.execute(() -> {
            if (isReseting.get()) {
                return;
            }

            isReseting.set(true);

            while (isReseting.get()){
                try {
                    Thread.sleep(60000);
                    destroy();
                    init();
                    isReseting.set(false);
                    alertService.sendMessage("Serial port is up.");
                } catch (SerialPortException | InterruptedException e) {
                    logger.error("Could not re-initialize serial port", e);
                }
            }
        });
    }
}
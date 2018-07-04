package server.service;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import server.data.EquipmentData;
import server.data.ReleData;

import javax.annotation.PostConstruct;

@Component
@Scope(value = "singleton")
public class AirConditionerService {

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


    @Value("${cond.id}")
    private String condId;

    @Value("${serial.port}")
    private String portId;
    private SerialPort serialPort;
    private ReleData rele;

    @PostConstruct
    public void init() {
        serialPort = new SerialPort(portId);
        try {
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
        catch (SerialPortException ex) {
            System.out.println(ex);
//            todo : send email that serial not working
        }
    }

    public void power(Boolean state) {
        try {
            serialPort.writeString(state ? "1" : "0");
            rele.setPower(state);
            releDataService.save(rele);
            pushService.send(rele);
        } catch (SerialPortException ex) {
//            todo : send email that serial not working
        }
    }

    private class PortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR() && event.getEventValue() > 0){
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
                }
                catch (Exception ex) {
                    //            todo : send email that serial not working
                    System.out.println(ex);
                }
            }
        }
    }
}

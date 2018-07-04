package server.mqtt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import server.data.EquipmentData;
import server.data.ReleData;
import server.service.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SonoffMessageHandler implements MessageHandler {

    private static final Gson GSON = new GsonBuilder().create();
    private static final Pattern MQTT_TOPIC_PATTERN = Pattern.compile("tele/sonoff/([^/]+)/([^/]+)");
    private static final String MQTT_TOPIC_STATE = "STATE";
    private static final String MQTT_TOPIC_SENSOR = "SENSOR";

    private static final String MQTT_RELE_POWER_ON = "ON";

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

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();
        Matcher matcher = MQTT_TOPIC_PATTERN.matcher(topic);
        if (!matcher.find()) {
            return;
        }

        String name = matcher.group(1);
        String command = matcher.group(2);
        String payload = message.getPayload().toString();

        if(MQTT_TOPIC_STATE.equals(command)) {
            SonoffState state = GSON.fromJson(payload, SonoffState.class);
            ReleData rele = new ReleData(name, MQTT_RELE_POWER_ON.equals(state.getPower()));
            releDataService.save(rele);
            pushService.send(rele);
        } else if (MQTT_TOPIC_SENSOR.equals(command)) {
            SonoffSensor sensor = GSON.fromJson(payload, SonoffSensor.class);
            EquipmentData equipment = new EquipmentData(name, true, sensor.getTemperature(), sensor.getHumidity());
            equipmentService.save(equipment);
            snapshotService.record(equipment);
            onlineMonitor.record(equipment);
            alertService.validate(equipment);
            pushService.send(equipment);
        }
    }
}

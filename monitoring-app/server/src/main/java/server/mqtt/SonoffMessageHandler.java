package server.mqtt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import server.data.EquipmentData;
import server.service.AlertService;
import server.service.EquipmentDataService;
import server.service.PushService;
import server.service.SnapshotService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SonoffMessageHandler implements MessageHandler {

    private static final Gson GSON = new GsonBuilder().create();
    private static final Pattern MQTT_TOPIC_PATTERN = Pattern.compile("tele/sonoff/([^/]+)/([^/]+)");
    private static final String MQTT_TOPIC_STATE = "STATE";
    private static final String MQTT_TOPIC_SENSOR = "SENSOR";

    @Autowired
    private PushService pushService;

    @Autowired
    private EquipmentDataService equipmentService;

    @Autowired
    private AlertService alertService;

    @Autowired
    private SnapshotService snapshotService;


    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
        Matcher matcher = MQTT_TOPIC_PATTERN.matcher(topic);
        if (!matcher.find()) {
            return;
        }

        String name = matcher.group(1);
        String command = matcher.group(2);
        String payload = message.getPayload().toString();

        System.out.println("name=" + name);
        if(MQTT_TOPIC_STATE.equals(command)) {
            SonoffState state = GSON.fromJson(payload, SonoffState.class);


        } else if (MQTT_TOPIC_SENSOR.equals(command)) {
            SonoffSensor sensor = GSON.fromJson(payload, SonoffSensor.class);
            EquipmentData equipment = new EquipmentData(name, true, sensor.getTemperature(), sensor.getHumidity());
            equipmentService.save(equipment);
            snapshotService.record(equipment);
            alertService.validate(equipment);
            pushService.sendEquipment(equipment);
        }
    }


}

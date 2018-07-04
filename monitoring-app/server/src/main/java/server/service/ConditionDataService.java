package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.data.ConditionData;
import server.data.EquipmentData;
import server.data.ReleData;
import server.domain.Condition;
import server.repository.ConditionRepository;

import javax.script.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ConditionDataService {

    private static final String MQTT_RELE_POWER_ON = "ON";
    private static final String MQTT_RELE_POWER_OFF = "OFF";

    @Autowired
    private ConditionRepository releConditionRepository;

    @Autowired
    private EquipmentDataService equipmentDataService;

    @Autowired
    private ReleDataService releDataService;

    @Autowired
    private AirConditionerService airConditionerService;

    @Autowired
    @Qualifier("mqttOutboundChannel")
    private MessageChannel messageChannel;

    @Value("${cond.id}")
    private String condId;

    private Map<String, LocalDateTime> powered = new ConcurrentHashMap<>();

    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

    public void save(ConditionData data) {
        Condition condition = new Condition(data.getName(), data.getCondition(),
                data.getManual(), data.getActive());
        releConditionRepository.save(condition);
    }

    public void delete(ConditionData data) {
        releConditionRepository.deleteById(data.getName());
    }

    public Collection<ConditionData> list() {
        final Collection<ConditionData> conditions = new LinkedList<>();
        releConditionRepository.findAll().forEach((equipment -> conditions.add(ConditionData.from(equipment))));
        return conditions;
    }

    @Scheduled(initialDelay = 10000, fixedRate = 10000)
    public void execute() {
        final Map<String, EquipmentData> equipments = equipmentDataService.list().stream()
                .collect(Collectors.toMap(EquipmentData::getName, Function.identity()));
        final Map<String, ReleData> reles = releDataService.list().stream()
                .collect(Collectors.toMap(ReleData::getName, Function.identity()));
        final LocalDateTime currentTime = LocalDateTime.now();
        final LocalDateTime delayedTime = LocalDateTime.now().minusMinutes(10);

        releConditionRepository.findAll().forEach(condition -> {
            SimpleBindings simpleBindings = new SimpleBindings();
            simpleBindings.put("equipments", equipments);
            simpleBindings.put("reles", reles);

            ReleData rele = reles.get(condition.getName());
            Boolean newRelePower;
            Boolean force = Boolean.FALSE;

            if (condition.getManual()) {
                newRelePower = condition.getActive();
                force = Boolean.TRUE;
            } else {
                try {
                    engine.eval(condition.getCondition(), simpleBindings);
                    Bindings global = ((Bindings) simpleBindings.get("nashorn.global"));
                    newRelePower = (Boolean) global.get("result");
                    force = (Boolean) global.get("force");
                } catch (ScriptException e) {
                    newRelePower = Boolean.FALSE;
                }
            }

            if(!newRelePower.equals(rele.getPower())) {
                LocalDateTime time = powered.get(rele.getName());
                if (force) {
                    power(rele.getName(), newRelePower);
                } else if (!newRelePower && (time == null || delayedTime.isAfter(time))) {
                    powered.remove(rele.getName());
                    power(rele.getName(), newRelePower);
                } else if(newRelePower) {
                    powered.put(rele.getName(), currentTime);
                    power(rele.getName(), newRelePower);
                }
            }
        });
    }

    public void power(String name, Boolean state) {
        if (condId.equals(name)) {
            airConditionerService.power(state);
        } else {
            messageChannel.send(MessageBuilder.withPayload(state ? MQTT_RELE_POWER_ON : MQTT_RELE_POWER_OFF)
                    .setHeader(MqttHeaders.TOPIC, "cmnd/sonoff/" + name + "/POWER")
                    .build());
        }
    }
}

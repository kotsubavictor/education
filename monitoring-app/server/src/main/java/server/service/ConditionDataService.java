package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
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
    @Qualifier("mqtt_outbound_channel")
    private MessageChannel messageChannel;

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

    @Scheduled(fixedRate = 10000)
    public void execute() {
        final Map<String, EquipmentData> equipments = equipmentDataService.list().stream()
                .collect(Collectors.toMap(EquipmentData::getName, Function.identity()));
        final Map<String, ReleData> reles = releDataService.list().stream()
                .collect(Collectors.toMap(ReleData::getName, Function.identity()));

        releConditionRepository.findAll().forEach(condition -> {
            SimpleBindings simpleBindings = new SimpleBindings();
            simpleBindings.put("equipments", equipments);
            simpleBindings.put("reles", reles);

            ReleData rele = reles.get(condition.getName());
            Boolean newRelePower;

            if (condition.getManual()) {
                newRelePower = condition.getActive();
            } else {
                try {
                    engine.eval(condition.getCondition(), simpleBindings);
                    newRelePower = (Boolean) ((Bindings) simpleBindings.get("nashorn.global")).get("result");
                } catch (ScriptException e) {
                    newRelePower = Boolean.FALSE;
                }
            }

            if(!newRelePower.equals(rele.getPower())) {
                power(rele.getName(), newRelePower);
            }
        });
    }

    public void power(String name, Boolean state) {
        messageChannel.send(MessageBuilder.withPayload(state ? MQTT_RELE_POWER_ON : MQTT_RELE_POWER_OFF)
                .setHeader(MqttHeaders.TOPIC, "cmnd/sonoff/" + name + "/POWER")
                .build());
    }
}

package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.data.ConditionData;
import server.domain.Condition;
import server.repository.ConditionRepository;

import java.util.Collection;
import java.util.LinkedList;

@Component
public class ConditionDataService {

    @Autowired
    private ConditionRepository releConditionRepository;

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

//    todo:
//    public static void main(String[] args) throws ScriptException {
//        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
//        SimpleBindings simpleBindings = new SimpleBindings();
//        System.out.println("map = " + simpleBindings.values());
//        simpleBindings.put("a", "test");
//        engine.eval("b = a + '-script';", simpleBindings);
//        System.out.println("map = " + simpleBindings.get("b"));
//        System.out.println("map = " + simpleBindings.values());
//        System.out.println("map = " + ((Bindings) simpleBindings.get("nashorn.global")).get("b"));
//    }
}

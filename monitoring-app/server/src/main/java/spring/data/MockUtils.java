package spring.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MockUtils {
    private static final Collection<Equipment> EQUIPMENT = new ArrayList<>(4);
    static {
        EQUIPMENT.add(new Equipment("xmrig1", 21));
        EQUIPMENT.add(new Equipment("xmrig2", 22));
        EQUIPMENT.add(new Equipment("xmrig3", 23));
        EQUIPMENT.add(new Equipment("xmrig4", 24));
    }

    private static final long TIMING_STEP = 5000;

    public static final Collection<Equipment> getEquipment() {
        return EQUIPMENT;
    }

    public static final Equipment getEquipment(final String name) {
        return getEquipment().stream().filter(equipment -> equipment.getName().equals(name)).findFirst().get();
    }

    public static final Collection<Map<String, Object>> generateRecords(int amount) {
        int size = 50;
        Collection<Map<String, Object>> data = new ArrayList(size);
        long time = System.currentTimeMillis();
        for (int i = size; i > 0; i--) {
            data.add(generateRecord(time - i * TIMING_STEP));
        }
        return data;
    }

    public static final Map<String, Object> generateRecord() {
        return generateRecord(System.currentTimeMillis());
    }

    public static final Map<String, Object> generateRecord(long time) {
        Map<String, Object> record = new HashMap<>();
        record.put("timing", time);
        for (Equipment eq : EQUIPMENT) {
            record.put(eq.getName(), generateTemperature());
        }
        return record;
    }

    public static final Long generateTemperature() {
        return 55 + Math.round(Math.random() * 15);
    }
}


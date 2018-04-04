package collections;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;


/**
 *
 Interfaces	Hash table Implementations	Resizable array Implementations	Tree Implementations	Linked list Implementations	Hash table + Linked list Implementations
 Set	HashSet	 	TreeSet	 	LinkedHashSet
 List	 	ArrayList	 	LinkedList
 Queue
 Deque	 	ArrayDeque	 	LinkedList
 Map	HashMap	 	TreeMap	 	LinkedHashMap
 */
public class CollectionsBasic {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "5", "6", "7");
        list.stream()
                .filter(e -> e.compareTo("4") >= 0)
                .forEach(e -> System.out.println(e));

        list.parallelStream()
                .filter(e -> e.compareTo("4") < 0)
                .forEach(e -> System.out.println(e));

        System.out.println(list.stream().collect(Collectors.joining(", ")));
        System.out.println(list.stream().collect(Collectors.summarizingInt(Integer::parseInt)));

        for (String o : list)
            System.out.println(o);

        HashSet<String> set = list.stream().collect(Collectors.toCollection(HashSet::new));
        System.out.println(set);

        PriorityQueue queue = list.stream().collect(Collectors.toCollection(PriorityQueue::new));
        System.out.println(queue.peek());

        Map<String, String> map = new HashMap<>();
        map.put("1", "11");
        map.put("2", "22");
        map.put("3", "33");
        map.put("4", "44");
        map.put("5", "55");
        map.put("6", "66");

        Map<String, String> copy = new HashMap<>(map);
        System.out.println(copy);
        copy.keySet().remove("5");
        System.out.println(copy);
        copy.values().remove("66");
        System.out.println(copy);
        copy.entrySet();

        ArrayList cons = set.stream().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println(cons);

        System.out.println(list);
        try {
            list.add("dsf");
        } catch (UnsupportedOperationException e) {
            System.out.println("Arrays.asList is not modifiable collection. add/remove does not work");
        }

    }
}

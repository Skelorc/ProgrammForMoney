import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        Map<Integer,Integer> test = new HashMap<>();
        test.computeIfAbsent(123,value -> value+ 321);
        test.computeIfPresent(123,(key,value) -> value+ 777);


        System.out.println(test.toString());
    }
}

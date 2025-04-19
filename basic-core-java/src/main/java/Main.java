import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("Raj", "DAV");
        map.put("Neha", "SVM");

        if(LearnOptional.isPresent(map, "Rahul") == null){
            System.out.println("Rahul not found");
        }else{
            System.out.println("Rahul found");
        }
    }
}

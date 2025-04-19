import java.util.Map;
import java.util.Optional;

public class LearnOptional {
    public static String isPresent(Map<String, String> map, String name) {
        Optional<String> nameOfUser = Optional.ofNullable(map.get(name));
        return nameOfUser.orElse(null);
    }
}

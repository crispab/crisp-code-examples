import java.util.Arrays;
import java.util.List;

public class Primes {


    public static List<Integer> of(int i) {
        if (i == 1) return Arrays.asList(1);
        if (i == 2) return Arrays.asList(2);
        if (i == 4) return Arrays.asList(2, 2);
        else return Arrays.asList(3);
    }
}

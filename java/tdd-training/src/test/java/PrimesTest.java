import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class PrimesTest {

    @Test
    public void prime_of_1() throws Exception {
        Assert.assertEquals(Arrays.asList(1), Primes.of(1));
    }
}

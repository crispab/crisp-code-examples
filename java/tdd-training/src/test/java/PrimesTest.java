import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class PrimesTest {

    @Test
    public void prime_of_1() throws Exception {
        Assert.assertEquals(Arrays.asList(1), Primes.of(1));
        Assert.assertEquals(Arrays.asList(2), Primes.of(2));
        Assert.assertEquals(Arrays.asList(3), Primes.of(3));
        Assert.assertEquals(Arrays.asList(2, 2), Primes.of(4));
    }
}

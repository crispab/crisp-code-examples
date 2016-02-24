import org.junit.Assert;
import org.junit.Test;

public class PrimesTest {

    @Test
    public void prime_of_1() throws Exception {
        int[] expected = new int[]{1};
        Assert.assertArrayEquals(expected, Primes.of(1));
    }
}
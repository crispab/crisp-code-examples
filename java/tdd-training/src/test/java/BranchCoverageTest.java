import org.junit.Test;

import static org.junit.Assert.*;

public class BranchCoverageTest {

    @Test
    public void given_not_knowing_when_message_then_not_know_message() {
        BranchCoverage branchCoverage = new BranchCoverage();

        String result = branchCoverage.message(false);

        assertEquals(result, "I do not know branch coverage by heart.");
    }
}
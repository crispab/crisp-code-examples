public class BranchCoverage {

    public String message(boolean knowledgeable) {
        String result = "I do ";
        if (!knowledgeable) {
            result += "not ";
        }
        result += "know branch coverage by heart.";
        return result;
    }
}

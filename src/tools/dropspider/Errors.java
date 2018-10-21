package tools.dropspider;

import java.util.ArrayList;

public class Errors {

    public String mobName;
    public ArrayList<String> wrong = new ArrayList<>();

    public String createErrorLog() {
        StringBuilder sb = new StringBuilder();

        for (String w : wrong) {
            sb.append(mobName).append(" : ").append(w).append(System.lineSeparator());
        }

        return sb.toString();
    }
}

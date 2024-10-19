package lab12dsa;

import java.util.Scanner;

public class LinesReader {

    String concatLines(int howMany, Scanner scanner) {
        String str = "";
        StringBuffer sb = new StringBuffer(str);
        while (howMany > 0) {
            sb.append(scanner.next());
            howMany--;
        }
        return sb.toString();
    }
}
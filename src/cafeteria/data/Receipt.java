package cafeteria.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static cafeteria.data.RecordMethods.search;
import static cafeteria.util.util.MENU_DATA_LOC;

/* ---------------------------------------------------------
http://www.mets-blog.com/java-pos-thermal-printer-example/
https://www.geeksforgeeks.org/count-occurrences-elements-list-java/
Used Hashset to count frequency of entries then parse into
String builder and print. References before the sentence.
 ---------------------------------------------------------*/

public class Receipt {
    public static String createRows(ArrayList<String> list) {

        Set<String> st = new HashSet<String>(list);
        StringBuilder stringBuilder = new StringBuilder();

        for (String s : st) {
            String price = search(s, 0, 1, MENU_DATA_LOC); //price
            stringBuilder.append("  " + s + "             " + Collections.frequency(list, s) + "            " + price + "\n");
        }

        String finalString = stringBuilder.toString();
        return finalString;
    }
}



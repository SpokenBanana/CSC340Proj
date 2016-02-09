package Concordance;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class Concordance {

    public static void main(String[] args) {
        try {
            String str;
            HashMap Concordance = new HashMap();
            Scanner s = new Scanner(new FileReader("Alice\'s Adventures in Wonderland.txt"));
            Scanner in = new Scanner(System.in);
            System.out.println("Enter a word to search for.");
            str = in.nextLine();
            int linenum = 1;
            int numwordfound = 0;
            while (s.hasNextLine()) {
                String str3 = s.nextLine();
                if (!str3.equals("")) {
                    Scanner ss = new Scanner(str3);
                    String str2 = ss.next();
                    System.out.println(str2);
                    if (str.equals(str2)) {
                        numwordfound++;
                        Concordance.put(linenum, numwordfound);
                    }
                }

                linenum++;
                System.out.println(linenum + " " + numwordfound);
            }

        } catch (Exception e) {
            System.out.println("not found");
        }
    }
}

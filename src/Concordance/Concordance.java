package Concordance;

import com.sun.org.apache.bcel.internal.generic.LNEG;

import javax.sound.sampled.Line;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Concordance {

    public static void main(String[] args) {
        try {
            String str;
            HashMap Concordance = new HashMap();
            Scanner s = new Scanner(new FileReader("src/Texts/Alice\'s Adventures in Wonderland.txt"));
            Scanner in = new Scanner(System.in);
            System.out.println("Enter a word to search for.");
            str = in.nextLine();
            int linenum = 1;
            int numwordfound = 0;
            while (s.hasNextLine()) {
                String str3 = s.nextLine();
                if (!str3.equals("")) {
                    Scanner ss = new Scanner(str3);
                    while (ss.hasNext()) {
                        String str2 = ss.next();
                        System.out.println(str2);
                        if (str.equals(str2)) {
                            numwordfound++;
                            //TODO using hashmaps in java
                            if (Concordance.containsKey(linenum)) {
                                // updating a hasmap
                                Object d = Concordance.get(linenum);
                                // update d
                                Concordance.put(linenum, d);
                            }
                            else {
                                // create d
                                Concordance.put(linenum, numwordfound);
                            }
                        }
                    }

                }

                linenum++;
                System.out.println(linenum + " " + numwordfound);
            }

        } catch (Exception e) {
            System.out.println("not found");
        }
    }

    public class LineData {
        int count;
        String word;
        ArrayList<Integer> lineIn;
        public LineData() {
            lineIn = new ArrayList<>();
        }
    }

    public HashMap<String, LineData> create(String keyword) {
        HashMap<String, LineData> concordance = new HashMap<>();
        //TODO transfer your main method code to here. Adjust as necessary.
        return concordance;
    }
}

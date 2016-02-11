package Concordance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class Concordance {

    public class LineData {

        int count;
        String word;
        ArrayList<Integer> lineIn;

        public LineData() {
            lineIn = new ArrayList<>();
        }
    }

    public HashMap<String, LineData> create(Scanner file) {
        HashMap<String, LineData> concordance = new HashMap<>();
        int lineNum = 0;
        while(file.hasNextLine()) {
            Scanner line = new Scanner(file.nextLine());
            while (line.hasNext()) {
                String word = line.next().replaceAll("([a-z]+)[?.,;:!]*", "$1").toLowerCase();
                if (concordance.containsKey(word)) {
                    LineData data = concordance.get(word);
                    data.count++;
                    data.lineIn.add(lineNum);
                    concordance.put(word, data);
                }
                else {
                    LineData data = new LineData();
                    data.count = 1;
                    data.lineIn.add(lineNum);
                    concordance.put(word, data);
                }
            }
        }

        return concordance;
    }

    public HashMap<String, LineData> create(Scanner file, String keyword) {
        HashMap<String, LineData> concordance = new HashMap<>();
        try {
            HashMap Concordance = new HashMap();



            int linenum = 1;
            while (file.hasNextLine()) {
                int numwordfound = 0;
                String str3 = file.nextLine();
                if (!str3.equals("")) {
                    Scanner ss = new Scanner(str3);
                    while (ss.hasNext()) {
                        String str2 = ss.next().replaceAll("([a-z]+)[?.,;:!]*", "$1");
                        System.out.println(str2);
                        if (keyword.toLowerCase().equals(str2.toLowerCase())) {
                            numwordfound++;
                            if (Concordance.containsKey(linenum)) {
                                Object d = Concordance.get(linenum);
                                Concordance.put(linenum, d);
                            } else {
                                Concordance.put(linenum, numwordfound);
                            }

                        }

                    }
                }

                    linenum++;
                    System.out.println(linenum + " " + numwordfound);
                }


            }   catch (Exception e) {
            System.out.println("not found");
        }
        return concordance;
    }
}

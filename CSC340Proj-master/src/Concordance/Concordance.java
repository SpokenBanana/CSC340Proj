package Concordance;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class Concordance implements Serializable {
    public String bookTitle;
    public HashMap<String, LineData> concordanceData;

    public class LineData implements Serializable {

        public int count;
        public String word;
        public ArrayList<Integer> lineIn;

        public LineData() {
            lineIn = new ArrayList<>();
        }
        public int getCount() {
            return count;
        }
        public ArrayList<Integer> getLines() {
            return lineIn;
        }
    }

    public HashMap<String, LineData> create(Scanner file) {
        HashMap<String, LineData> concordance = new HashMap<>();
        int lineNum = 0;
        while(file.hasNextLine()) {
            String[] words = file.nextLine().split(" ");
            for(String word : words) {
                word = word.replaceAll("[^a-zA-Z ]", "").toLowerCase().trim();
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
            lineNum++;
        }
        concordanceData = concordance;

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
        concordanceData = concordance;
        return concordance;
    }

    @Override
    public String toString() {
        String result = "- " + bookTitle + "\n";
        if (concordanceData != null) {
            for (String key : concordanceData.keySet()) {
                result += String.format("%s | %d\n", key, concordanceData.get(key).count);
            }
        }
        return result;
    }
    
    /**
     * This method was written by Joel Wilhelm, and  will report words
     * within two lines of the first appearance of target word.
     * @param word - the center word
     * @return 
     */
    public String words_distance_first(String word){
       //if the concordance does not contain the wanted word, tell the user
        if(!this.concordanceData.containsKey(word.toLowerCase())){
            System.out.println("We are sorry, but the word you are looking"
                    + "for does not exist within this concordance.");
           
       }
        //create a scanner of the file to prepare to find the keyword
       IO.IO io = new IO.IO();
       Scanner scan;
        scan = io.read_file(this.bookTitle);
        
        //first stores the first line that word appears in
        int first = this.concordanceData.get(word).lineIn.get(0);
        //creates a counter 
        int counter = 0;
        
        
        //advances scanner to the appropriate line
        while(scan.hasNextLine()&&counter<(first-2)){
            scan.nextLine();
            counter++;
            
        }
        //create a new string to store the data in
        String words="";
        //add the first line above the keyword
        words = words.concat(scan.nextLine());
        words = words.concat("\n");
        //add the line containing the keyword
        words = words.concat(scan.nextLine());
        words = words.concat("\n");
        //add the line after the keyword
        words = words.concat(scan.nextLine());
        words = words.concat("\n");
        
        
        
        
       return words; 
    }
    
    /**
     * This method was written by Joel Wilhelm, and will report words within a
     * certain distance of all appearances of the target word. 
     * @param word
     * @param numwords
     * @return 
     */
    public String words_distance_all(String word, int numwords){
        
        if(!this.concordanceData.containsKey(word.toLowerCase())){
            System.out.println("We are sorry, but the word you are looking"
                    + "for does not exist within this concordance.");
           
       }
            //create a scanner of the file to prepare to find the keyword
       IO.IO io = new IO.IO();
       Scanner scan;
        scan = io.read_file(this.bookTitle);
        int numoccurances = 0;
        
        
        //create a new string to store the data in
        String words="";
        
        
        while(numoccurances<this.concordanceData.get(word).lineIn.size()){
        //first stores the first line that word appears in
        int first = this.concordanceData.get(word).lineIn.get(numoccurances);
        //creates a counter 
        int counter = 0;
        
        //advances scanner to the appropriate line
        while(scan.hasNextLine()&&counter<(first-2)){
            scan.nextLine();
            counter++;
            
        }
        
        
        //add the first line above the keyword
        words = words.concat("THE NEXT OCCURANCE OF THE WANTED WORDS IS WITHIN THESE"
                + " THREE LINES: \n \n");
        words = words.concat(scan.nextLine());
        words = words.concat("\n");
        //add the line containing the keyword
        words = words.concat(scan.nextLine());
        words = words.concat("\n");
        //add the line after the keyword
        words = words.concat(scan.nextLine());
        words = words.concat("\n");
        
        
        numoccurances++;
        }
       return words; 
        
        
        
    }
    
    
    
    
    
}

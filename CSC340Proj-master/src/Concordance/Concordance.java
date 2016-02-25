package Concordance;


import javax.sound.sampled.Line;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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

    public HashMap<String, LineData> createFromFile(Scanner file) {
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
                    data.word = word;
                    data.lineIn.add(lineNum);
                    concordance.put(word, data);
                }
            }
            lineNum++;
        }
        concordanceData = concordance;
        return concordance;
    }

    public HashMap<String, LineData> create(Scanner file) {
        boolean pastPreambles = false;
        while(file.hasNextLine()) {
            if (!pastPreambles) {
                if (file.nextLine().startsWith("*** START")) {
                    pastPreambles = true;
                }
            }
            else {
                createFromFile(file);
                break;
            }
        }

        return concordanceData;
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
    

    /*
        Finds and returns a list of words within the distance in lines of the target word.
     */
    public ArrayList<String> wordsDistanceInLines(String target, int lineDistance) {
        LineData targetData = concordanceData.get(target);
        ArrayList<String> words = new ArrayList<>();

        for (String key : concordanceData.keySet()) {
            // skip the target word
            if (key.equals(target)) continue;

            LineData word = concordanceData.get(key);
            // search through lines the target word is found
            for (int i : targetData.lineIn) {
                // search through lines the word is found
                word.lineIn.forEach((Integer line) ->{
                    if (!words.contains(word.word) && !word.word.equals(target) && line - lineDistance < i && i < line + lineDistance)
                        words.add(word.word); // add words within the distance given.
                });
            }
        }

        return words;
    }

    /*
        Finds and returns a list of words within the distance in words of the target word
     */

    public ArrayList<String> wordsDistanceInWords(String target, int wordDistance, Scanner file) {
        LineData targetData = concordanceData.get(target);
        ArrayList<String> words = new ArrayList<>();
        CircleQueue<String> wordsBefore = new CircleQueue<String>(wordDistance);
        int saving = 0;
        while (file.hasNextLine()) {
            String line = file.nextLine();
            String[] lineWords = line.split(" ");
            for (String word : lineWords) {
                word = strip(word);
                if (saving != 0){
                    if (!words.contains(word))
                        words.add(word);
                    saving--;
                }
                if (word.equals(target)) {
                    // start adding words to the words
                    while (!wordsBefore.isEmpty()) {
                        String toAdd = wordsBefore.pop();
                        if (!words.contains(toAdd))
                            words.add(toAdd);
                    }
                    saving = wordDistance;
                }
                else {
                    if (!wordsBefore.contains(word))
                        wordsBefore.add(word);
                }
            }
        }

        while (!wordsBefore.isEmpty()) {
            String toAdd = wordsBefore.pop();
            if (!words.contains(toAdd))
                words.add(toAdd);
        }

        return words;
    }

    /**
     * Finds the words around the phrase within the given distance in lines
     * @param phrase the phrase to look for
     * @param distance the distance in lines
     * @param file the file the book coressponds to
     * @return a list of unique words found within the range
     */
    public ArrayList<String> findPhraseInLines(String phrase, int distance, Scanner file) {
        ArrayList<String> words = new ArrayList<>();

        // holds the last "distance" lines.
        CircleQueue<String> lines = new CircleQueue<String>(distance);

        // holds the last phrase, we use the length + 1 to capture the last word.
        CircleQueue<String> phr = new CircleQueue<String>(phrase.split(" ").length + 1);

        // lets us know if we need to save the current line, since we want lines in
        // "distance" before and after the phrase.
        int saving = 0;

        while (file.hasNextLine()) {
            String line = file.nextLine();
            String[] parts = line.split(" ");

            // do we need to save this line?
            if (saving != 0) {
                // save all the words in this line
                for (String word : parts)
                    if (!words.contains(word) && !phr.contains(word))
                        words.add(word);
                saving--;
            }

            for (String part : parts) {
                // add a new word to the phrase queue
                phr.add(part);
                // is it full? does what it currently have match the phrase?
                if (phr.isFull() && phr.asString().equalsIgnoreCase(phrase)) {
                    // add words in lines, start adding words in the next <distance> lines.
                    while (!lines.isEmpty()) {
                        String[] lineParts = lines.pop().split(" ");
                        for (String lineWord: lineParts)
                            if (!words.contains(lineWord) && !phr.contains(lineWord))
                                words.add(lineWord);
                    }
                    // tells us to save the next "distance" lines too.
                    saving = distance;
                }
                // save the words in the current line also.
                if (saving != 0)
                    if (!words.contains(part) && !phr.contains(part))
                        words.add(part);
            }
            // add current line to the lines queue.
            lines.add(line);
        }

        return words;
    }

    public String strip(String s) {
        return s.toLowerCase().replaceAll("[^a-zA-Z ]", "").trim();
    }

    
}

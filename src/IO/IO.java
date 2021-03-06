package IO;
/*
 This IO Class was written by Joel Wilhelm, and I have abided by the UNCG
 Honor Code on this class. 2/9/2015
 */

//importing potential java stuffs
import Concordance.Concordance;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.io.*;

public class IO {
    String startPath;

    public IO() {
        startPath = "";
    }

    /**
     * This method should create an ArrayList of books that are stored on the
     * computer, and return that ArrayList
     *
     * @return
     */
    public ArrayList<String> get_book_list() {
        //create a file to read from
        //creates a file to search through
        File Bookfile = new File(startPath + "Texts");
        //creates an array of files to search through
        File[] filelist = Bookfile.listFiles();
        if (filelist == null) {
            Bookfile.mkdir();
            filelist = Bookfile.listFiles();
        }
        //creates an array list tostore the list of books in
        ArrayList<String> ListofBooks = new java.util.ArrayList<>();
        //searches through the file and adds all .txt files

        //loops through filelist
        for (int i = 0; i < filelist.length; i++) {
            //if the file is a txt file
            if (filelist[i].toString().contains(".txt")) {

                //ad it to the array
                ListofBooks.add(filelist[i].getName());
            }

        }

        //return the array
        return ListofBooks;

    }

    /**
     * This method should create an ArrayList of books that are stored on the
     * computer that contain the wanted keyword(s)
     *
     * @param title
     *
     *
     *
     * @return
     */
    public ArrayList<String> search_book_list(String title) {
        //create a file to read from
        //creates a file to search through
        File Bookfile = new File(startPath + "Texts");
        //creates an array of files to search through
        File[] filelist = Bookfile.listFiles();
        if (filelist == null) {
            Bookfile.mkdir();
            filelist = Bookfile.listFiles();
        }
        //creates an array list tostore the list of books in
        ArrayList<String> ListofBooks = new java.util.ArrayList<>();
        //searches through the file and adds all .txt files

        //loops through filelist
        for (int i = 0; i < filelist.length; i++) {
            //if the file is a txt file
            if (filelist[i].toString().contains(".txt")) {
                //if the title contains the wanted keywords
                if (filelist[i].toString().toLowerCase().contains(title.toLowerCase())) {
                    //add it
                    ListofBooks.add(filelist[i].getName().substring(0, filelist[i].getName().indexOf(".txt")));
                }

               
            }

        }

        //return the array
        return ListofBooks;

    }

    /**
     * This method should return only 1 file of the specific wanted book. It will
     * return null if there are 0 matches, or more than one match.
     *
     * @param title
     *
     *
     *
     *
     * @return
     */
    public File get_book(String title) {
        //create a file to read from
        //creates a file to search through
        File Bookfile = new File(startPath + "Texts");
        //creates an array of files to search through
        File[] filelist = Bookfile.listFiles();
        if (filelist == null) {
            Bookfile.mkdir();
            filelist = Bookfile.listFiles();
        }
        title += ".txt";

        //searches through the file and adds all .txt files
        //used to keep track of how many times 
        int count = 0;
        int location = 0;
        //loops through filelist
        for (int i = 0; i < filelist.length; i++) {
            //if the file is a txt file
            if (filelist[i].toString().contains(".txt")) {
                //if the title contains the wanted keywords
                if (filelist[i].toString().toLowerCase().contains(title.toLowerCase())) {
                    //increment count and set location as i
                    count++;
                    location = i;

                }

                
            }

        }

        //return the File if count == 1
        if (count == 1) {
            return filelist[location];
        } else {
            return null;
        }

    }

    /**
     * This method should create an ArrayList of the different Concordances
     * stored in the Concordances file
     *
     * @param keywords
     * @return
     */
    public ArrayList<String> search_concordance_list(String keywords) {
        //creates the concordances file
        File Concordancesfile = new File(startPath + "Concordances");
        //creates arraylist
        ArrayList<String> ListofConcordances = new java.util.ArrayList<>();
        //creates filelist to write to arraylist
        File[] filelist = Concordancesfile.listFiles();
        if (filelist == null) {
            Concordancesfile.mkdir();
            filelist = Concordancesfile.listFiles();
        }
        //puts each of the filelist concordances into the ArrayList
        for (int i = 0; i < filelist.length; i++) {
            //check if keyword is in the filename
            if (filelist[i].getName().endsWith(".ser") && filelist[i].toString().toLowerCase().contains(keywords.toLowerCase())) {
                ListofConcordances.add(filelist[i].getName().substring(0, filelist[i].getName().indexOf(".ser")));
            }

        }
        //return ListofConcordances;
        return ListofConcordances;
    }

    /**
     * This method will return exactly one concordance file, that is specified.
     * If multiple files are found, will return null.
     *
     * @param keywords
     * @return
     */
    public File get_concordance(String keywords) {
        //creates the concordances file
        File Concordancesfile = new File("Concordances");
        //creates a count to make sure only one concordance is returned
        int count = 0;
        //creates an interger to keep track of where in the filelist we are
        int location = 0;
        //creates filelist to write to arraylist
        File[] filelist = Concordancesfile.listFiles();
        if (filelist == null) {
            Concordancesfile.mkdir();
            filelist = Concordancesfile.listFiles();
        }
        //counts each time a title with that keyword is found
        for (int i = 0; i < filelist.length; i++) {
            //checks to see if the file contains keyword
            if (filelist[i].toString().toLowerCase().contains(keywords.toLowerCase())) {
                //ups the count
                count++;
                //lets us remember where in the filelist the concordance file 
                //was found
                location = i;
            }

        }
        //return ListofConcordances;

        if (count == 1) {
            return filelist[location];
        } else {
            return null;
        }
    }

    /**
     * This method should create an ArrayList of the different Concordances
     * stored in the Concordances file
     *
     * @return
     */
    public ArrayList<String> get_concordance_list() {
        //creates the concordances file
        File Concordancesfile = new File(startPath + "Concordances");
        //creates arraylist
        ArrayList<String> ListofConcordances = new java.util.ArrayList<>();
        //creates filelist to write to arraylist
        File[] filelist = Concordancesfile.listFiles();
        if (filelist == null) {
            Concordancesfile.mkdir();
            filelist = Concordancesfile.listFiles();
        }
        //puts each of the filelist concordances into the ArrayList
        for (int i = 0; i < filelist.length; i++) {
            if (filelist[i].toString().endsWith(".ser"))
                ListofConcordances.add(filelist[i].getName().substring(0, filelist[i].getName().indexOf(".ser")));
        }
        //return ListofConcordances;
        return ListofConcordances;
    }

    /**
     * This method will create a scanner for a file passed through a string
     *
     * @param filename
     * @return
     */
    public Scanner read_file(String filename) {
        //create scanner
        File f = new File(filename);
        Scanner newscan;
        try {
            newscan = new Scanner(f);
        } catch (IOException e) {
            return null;
        }
        //return
        return newscan;
    }
    /**
     * This method, when passed a file object, will create a scanner for that
     * object. 
     * @param filename
     * @return 
     */
    public Scanner scan_file(File filename) {
        //create scanner
        
        Scanner newscan;
        try {
            newscan = new Scanner(filename);
        } catch (IOException e) {
            return null;
        }
        //return
        return newscan;
    }

    public void save_concordance(HashMap<String, Concordance.LineData> data) {
        // save the data in "data" to a file
        // or Serialize.
        // into src/Concordances.

    }

    /**
     * serializes the concordance object
     * @param concordance concordance to serialize
     */
    public void save(Concordance concordance) {
        File file = new File(startPath + "Concordances");
        // we must enforce use of these directories.
        if (!file.exists()) file.mkdir();
        try {
            FileOutputStream fout = new FileOutputStream(startPath + "Concordances/" +concordance.bookTitle +".ser");
            ObjectOutputStream writer = new ObjectOutputStream(fout);
            writer.writeObject(concordance);
            writer.close();

        } catch (Exception e) {
            System.out.println("could not write to file, try again.");
        }
    }

    /**
     * copies the book into our library
     * @param file file to add to our library
     */
    public void saveNewBook(File file) {
        File directory = new File(startPath + "Texts");
        if (!directory.exists()) directory.mkdir();
        File newFile = new File(startPath + "Texts/" + file.getName());
        try {
            Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Could not copy the file");
        }
    }

    public Concordance loadConcordance(String title) throws Exception {
        FileInputStream fin = new FileInputStream(startPath + "Concordances/" + title + ".ser");
        ObjectInputStream in = new ObjectInputStream(fin);
        return (Concordance) in.readObject();
    }

}

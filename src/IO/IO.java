package IO;
/*
 This IO Class was written by Joel Wilhelm, and I have abided by the UNCG
 Honor Code on this class. 2/9/2015
 */

//importing potential java stuffs
import java.util.*;
import java.io.*;

public class IO {

    /*
     This method should create an ArrayList of books that are stored on the
     computer, and return that ArrayList
     */
    public ArrayList<String> get_book_list(String title) {
        //create a file to read from
        //creates a file to search through
        File Bookfile = new File("Texts");
        //creates an array of files to search through
        File[] filelist = Bookfile.listFiles();
        //creates an array list tostore the list of books in
        ArrayList<String> ListofBooks = new java.util.ArrayList<>();
        //searches through the file and adds all .txt files

        //loops through filelist
        for (int i = 0; i < filelist.length; i++) {
            //if the file is a txt file
            if (filelist[i].toString().contains(".txt")) {
                //add it to the array
                ListofBooks.add(filelist[i].toString());
            }

        }

        //return the array
        return ListofBooks;

    }
    /*
     This method should create an ArrayList of the different Concordances stored
     in the Concordances file
     */

    public ArrayList<String> search_concordnace(String keywords) {
        //creates the concordances file
        File Concordancesfile = new File("Concordances");
        //creates arraylist
        ArrayList<String> ListofConcordances = new java.util.ArrayList<>();
        //creates filelist to write to arraylist
        File[] filelist = Concordancesfile.listFiles();
        //puts each of the filelist concordances into the ArrayList
        for (int i = 0; i < filelist.length; i++) {
            ListofConcordances.add(filelist[i].toString());
        }
        //return ListofConcordances;
        return ListofConcordances;
    }
    /*
    This method will create a scanner for the wanted text
    */
    public Scanner read_file(String filename) {
        //create scanner
        Scanner newscan = new Scanner(filename);
        //return
        return newscan;
    }

}

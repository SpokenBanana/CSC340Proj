import Concordance.Concordance;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    /*
        Notes:
        start of actual book after:
        *** START OF THIS PROJECT GUTENBERG EBOOK TOM SAWYER ***
     */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(System.in);

		IO.IO io = new IO.IO();
		Concordance concordance = new Concordance();

		String command = "";
		do {
            System.out.print("Enter command (-h for help) (ENTER to exit): ");
            command = scanner.nextLine();

            if (command.equals("")){
                System.out.println("Good-bye.");
                return;
            }

            String[] commands = command.split(" ");
            // commands
            switch (commands[0]) {
                case "-h":
                    System.out.println("Here are the commands\n" +
                            "-getconcordance <concordance name> | checks if concordance exists\n" +
                            "-getbook <book name> | checks if book exists.\n" +
                            "-createconcordnace <book file name> | creates and saves a concordance. \n" +
                            "-createconcordance | Will open up a file explorer for you to pick a file you want to create a concordance from.\n" +
                            "-loadconcordance <concordance file name> | loads the concordance and allows you to perform queries on it.\n" +
                            "-books | returns a list of books.\n" +
                            "-concordances | returns a list of saved concordances.\n" +
                            "-searchconcordance <keyword> | returns a list of concordances that contain the given keyword in the title.\n" +
                            "-searchbook <keyword> | returns a list of books that contain the given keyword in the title.");
                    break;
                case "-getconcordance": {
                    String s = "";
                    for (int i = 1; i < commands.length; i++)
                        s += commands[i];
                    File cfile = io.get_book(s);
                    if (cfile != null) {
                        System.out.println(String.format("Concordance found. %s", cfile.getName()));
                    }
                    else {
                        System.out.println("Concordance does not exist");
                    }
                    break;
                }
                case "-getbook": {
                    String s = "";
                    for (int i = 1; i < commands.length; i++)
                        s += commands[i];
                    File book = io.get_book(s);
                    if (book != null) {
                        System.out.println(String.format("Book found. %s", book.getName()));
                    }
                    else {
                        System.out.println("Book does not exist");
                    }
                    break;
                }
                case "-searchconcordance": {
                    ArrayList<String> result = io.search_concordance_list(commands[1]);
                    System.out.println("Concordances: ");
                    if (result.isEmpty())
                        System.out.println("No concordance found.");
                    else{
                        result.forEach(System.out::println);
                    }
                    break;
                }
                case "-searchbook": {
                    ArrayList<String> result = io.search_book_list(commands[1]);
                    System.out.println("Books: ");
                    if (result.isEmpty())
                        System.out.println("No books found.");
                    else{
                        result.forEach(System.out::println);
                    }
                    break;
                }
                case "-createconcordance":
                    // choose a file
                    if (commands.length == 1) {
                        JFileChooser  chooser = new JFileChooser();
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                        chooser.setFileFilter(filter);
                        int returnval = chooser.showOpenDialog(new JFrame("Choose a file"));
                        if (returnval == JFileChooser.APPROVE_OPTION) {
                            File file = chooser.getSelectedFile();
                            io.saveNewBook(file);
                            Scanner fileChosen = new Scanner(file);
                            concordance.bookTitle = file.getName().substring(0, file.getName().indexOf(".txt"));
                            concordance.createFromFile(fileChosen);
                            io.save(concordance);
                            System.out.println("Concordance created.");
                        }
                        else {
                            System.out.println("Try again.");
                        }
                    } // open selected file
                    else {
                        String book = "";
                        for (int i = 1; i < commands.length; i++) {
                            book += commands[i];
                        }
                        // because we want it to be a .ser when saved
                        concordance.bookTitle = book.substring(0, book.indexOf(".txt"));
                        try {
                            Scanner file = new Scanner(io.get_book(book));
                            concordance.create(file);
                            io.save(concordance);
                            System.out.println("Concordance created.");
                        } catch (Exception e) {
                            System.out.println("Book not found.");
                        }
                    }
                    break;
                case "-books":
                    ArrayList<String> files = io.get_book_list();
                    System.out.println("Books:");
                    files.forEach(System.out::println);
                    break;
                case "-concordances":
                    ArrayList<String> concordances = io.get_concordance_list();
                    System.out.println("Concordances: ");
                    if (concordances.size() == 0)
                        System.out.println("No saved concordances.");
                    else
                        concordances.forEach(System.out::println);
                    break;
                case "-loadconcordance":
                    String name = "";
                    for (int i = 1; i < commands.length; i++)
                        name += commands[i];
                    Concordance c;
                    try{
                        System.out.println("loading...");
                        // de-serialize
                        FileInputStream fin = new FileInputStream("CSC340Proj-master/src/Concordances/" + name);
                        ObjectInputStream in = new ObjectInputStream(fin);
                        c = (Concordance) in.readObject();
                    } catch (Exception e) {
                        System.out.println("Could not find the concordance, try again");
                        break;
                    }
                    String line = "";
                    System.out.println("Concordance loaded");

                    Scanner query = new Scanner(System.in);
                     do {
                        System.out.println("What queries would you like to do? (-h for query commands) (ENTER to exit): ");
                        line = query.nextLine();
                        String[] qs = line.split(" ");
                        switch (qs[0]) {
                            case "-h":
                                System.out.println("-count <word> | Returns the count of the word given.\n" +
                                        "-lines <word> | Returns the line numbers the word appears in.\n" +
                                        "wordsInDistanceLines <word> <distance> | Returns a list of words " +
                                        "within <distance> lines of <word>, <distance> being an integer.\n" +
                                        "wordsInDistanceWords <word> <distance> | Returns a list of words" +
                                        "within <distance> words of <word>, <distance> being an integer.");
                                break;
                            case "-count": {
                                if (c.concordanceData.containsKey(qs[1])) {
                                    Concordance.LineData data = c.concordanceData.get(qs[1]);
                                    System.out.println(data.getCount());
                                } else {
                                    System.out.println("Word not found.");
                                }
                                break;
                            }
                            case "-lines": {
                                if (c.concordanceData.containsKey(qs[1])){
                                    Concordance.LineData data = c.concordanceData.get(qs[1]);
                                    data.getLines().forEach(System.out::println);
                                }
                                else {
                                    System.out.println("Word not found.");
                                }
                                break;
                            }
                            case "wordsInDistanceLines": {
                                if (qs.length < 2) {
                                    System.out.println("The parameters of this command are <word1> <distance:integer>");
                                    break;
                                }
                                int distance = 0;
                                try {
                                    distance = Integer.parseInt(qs[2]);
                                } catch (Exception e) {
                                    System.out.println("The last parameter but be an integer value");
                                    break;
                                }
                                ArrayList<String> words = c.wordsDistanceInLines(qs[1], distance);
                                System.out.println(String.format("Words within %d distance of %s", distance, qs[1]));
                                words.forEach(System.out::println);
                                break;
                            }
                            case "wordsInDistanceWords": {
                                if (qs.length < 2) {
                                    System.out.println("The parameters of this command are <word> <distance:integer>");
                                    break;
                                }
                                int distance = 0;
                                try {
                                    distance = Integer.parseInt(qs[2]);
                                } catch (Exception e) {
                                    System.out.println("The last parameter must be an integer value.");
                                }
                                Scanner bookScanner = new Scanner(io.get_book(c.bookTitle));
                                ArrayList<String> words = c.wordsDistanceInWords(qs[1], distance, bookScanner);
                                System.out.println(String.format("Words within %d distance in words of %s:", distance, qs[1]));
                                words.forEach(System.out::println);
                                break;
                            }
                            default:
                                if (!line.equals(""))
                                    System.out.println("Command not recognized.");
                                break;
                        }
                    }while (!line.equals(""));

                    break;
                default:
                    System.out.println("command not recognized");
                    break;
            }
		} while (!command.equals(""));
	}
}

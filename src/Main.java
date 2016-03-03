import Concordance.Concordance;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
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
        Main main = new Main();

		String command = "";
		do {
            System.out.print("Enter command (-h for help) (ENTER to exit): ");
            command = scanner.nextLine();

            if (command.equals("")){
                System.out.println("Good-bye.");
                break;
            }

            String[] commands = command.split(" ");
            // commands
            switch (commands[0]) {
                case "h":
                    System.out.println("Here are the commands\n" +
                            "getconcordance <concordance name> | checks if concordance exists\n" +
                            "getbook <book name> | checks if book exists.\n" +
                            "createconcordnace <book title> | creates and saves a concordance. \n" +
                            "createconcordance | Will open up a file explorer for you to pick a file you want to create a concordance from.\n" +
                            "loadconcordance <title of book> | loads the concordance and allows you to perform queries on it.\n" +
                            "books | returns a list of books.\n" +
                            "concordances | returns a list of saved concordances.\n" +
                            "searchconcordance <keyword> | returns a list of concordances that contain the given keyword in the title.\n" +
                            "searchbook <keyword> | returns a list of books that contain the given keyword in the title.");
                    break;
                case "getconcordance": {
                    String s = "";
                    for (int i = 1; i < commands.length; i++)
                        s += commands[i];
                    File cfile = io.get_book(s);
                    if (cfile != null) {
                        System.out.println(String.format("Concordance found. %s", cfile.getName()));
                    }
                    else {
                        System.out.println("\nConcordance does not exist\n");
                    }
                    break;
                }
                case "getbook": {
                    String s = "";
                    for (int i = 1; i < commands.length; i++)
                        s += commands[i];
                    File book = io.get_book(s);
                    if (book != null) {
                        System.out.println(String.format("\nBook found. %s", book.getName()));
                    }
                    else {
                        System.out.println("\nBook does not exist\n");
                    }
                    break;
                }
                case "searchconcordance": {
                    ArrayList<String> result = io.search_concordance_list(commands[1]);
                    System.out.println("\n\t==== Concordances matching your title ==== \n");
                    if (result.isEmpty())
                        System.out.println("\nNo concordance found.\n");
                    else{
						for (String s : result) System.out.println(s);
                        System.out.println();
                    }
                    break;
                }
                case "searchbook": {
                    ArrayList<String> result = io.search_book_list(commands[1]);
                    System.out.println("\n\t==== Books matching the title ==== \n");
                    if (result.isEmpty())
                        System.out.println("\n\t\tNo books found.\n");
                    else{
						for (String s : result) System.out.println(s);
                        System.out.println();
                    }
                    break;
                }
                case "createconcordance":
                    // choose a file
                    if (commands.length == 1) {
                        JFileChooser  chooser = new JFileChooser();
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                        chooser.setFileFilter(filter);
                        int returnval = chooser.showOpenDialog(new JFrame("Choose a file"));
                        if (returnval == JFileChooser.APPROVE_OPTION) {
                            File file = chooser.getSelectedFile();
                            io.saveNewBook(file);
                            BufferedReader fileChosen = new BufferedReader(new FileReader(file));
                            concordance.bookTitle = file.getName().substring(0, file.getName().indexOf(".txt"));
                            System.out.println("Creating concordance....");
                            concordance.createFromFile(fileChosen);
                            io.save(concordance);
                            System.out.println("Concordance created.");
                            System.out.println(String.format("\nYou can now use the command \"loadconcordance %s\" to load the concordance " +
                                    "and perform queries on it.\n", concordance.bookTitle));
                        }
                        else {
                            System.out.println("\nTry again.\n");
                        }
                    } // open selected file
                    else {
                        String book = "";
                        for (int i = 1; i < commands.length; i++) {
                            book += commands[i];
                        }
                        // because we want it to be a .ser when saved
                        concordance.bookTitle = book;
                        try {
                            BufferedReader file = new BufferedReader(new FileReader(io.get_book(book)));
                            System.out.println("\nCreating concordance...");
                            if (!concordance.create(file)){
                                file = new BufferedReader(new FileReader(io.get_book(book)));
                                concordance.createFromFile(file);
                            }

                            io.save(concordance);
                            System.out.println("Concordance created.\n");
                            System.out.println(String.format("You can now use the command \"loadconcordance %s\" to load the concordance" +
                                    "and perform queries on it.\n", concordance.bookTitle));
                        } catch (Exception e) {
                            System.out.println("Book not found.");
                        }
                    }
                    break;
                case "books":
                    ArrayList<String> files = io.get_book_list();
                    System.out.println("\n\t=== Books saved ===\n");
					for (String s : files) System.out.println(s);
                    System.out.println();
                    break;
                case "concordances":
                    ArrayList<String> concordances = io.get_concordance_list();
                    System.out.println("\n\t=== Concordances saved ===\n ");
                    if (concordances.size() == 0)
                        System.out.println("No saved concordances.");
					else {
						for (String s : concordances) System.out.println(s);
                        System.out.println();
                    }
                    break;
                case "loadconcordance":
                    String name = "";
                    for (int i = 1; i < commands.length; i++)
                        name += commands[i];
                    Concordance c;
                    ArrayList<Concordance.LineData> ranks = new ArrayList<>();
                    System.out.println("loading...");
                    try{
                        // de-serialize
                        c = io.loadConcordance(name);
                    } catch (Exception e) {
                        System.out.println("Could not find the concordance, try again");
                        break;
                    }
                    String line = "";
                    System.out.println("\nConcordance loaded. You can now perform queries on this concordance\n");

                    Scanner query = new Scanner(System.in);
                     do {
                        System.out.print("What queries would you like to do? (-h for query commands) (ENTER to exit): ");
                        line = query.nextLine();
                        String[] qs = line.split(" ");
                        switch (qs[0]) {
                            case "h":
                                System.out.println("count <word> | Returns the count of the word given.\n" +
                                        "lines <word> | Returns the line numbers the word appears in.\n" +
                                        "wordsInDistanceLines <word> <distance> | Returns a list of words " +
                                        "within <distance> lines of <word>, <distance> being an integer.\n" +
                                        "wordsInDistanceWords <word> <distance> | Returns a list of words" +
                                        "within <distance> words of <word>, <distance> being an integer.\n" +
                                        "rank <word> | returns the rank of the given word.\n" +
                                        "exit | exits out of query mode.\n" +
                                        "h | displays the list of legal query commands");
                                break;
                            case "count": {
                                if (c.concordanceData.containsKey(qs[1])) {
                                    Concordance.LineData data = c.concordanceData.get(qs[1]);
                                    System.out.println(data.getCount());
                                } else {
                                    System.out.println("Word not found.");
                                }
                                break;
                            }
                            case "lines": {
                                if (c.concordanceData.containsKey(qs[1])){
                                    Concordance.LineData data = c.concordanceData.get(qs[1]);
									for (Integer s : data.getLines()) System.out.println(s);
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
								for (String s : words) System.out.println(s);
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
                                    break;
                                }
                                BufferedReader bookScanner = new BufferedReader(new FileReader(io.get_book(c.bookTitle)));
                                ArrayList<String> words = c.wordsDistanceInWords(qs[1], distance, bookScanner);
                                System.out.println(String.format("Words within %d distance in words of %s:", distance, qs[1]));
								for (String s : words) System.out.println(s);
                                break;
                            }
                            case "phraseInDistanceLines": {
                                if (qs.length < 2) {
                                    System.out.println("the parameters of this command are <distance:integer> <phrase>");
                                    break;
                                }
                                String phrase = "";
                                for (int i = 2; i < qs.length; i++)
                                    phrase += qs[i] + " ";
                                phrase = phrase.substring(0, phrase.length() - 1);
                                int distance = 0;
                                try {
                                    distance = Integer.parseInt(qs[1]);
                                } catch (Exception e) {
                                    System.out.println("The first parameter must be an integer.");
                                    break;
                                }
                                BufferedReader bookScanner = new BufferedReader(new FileReader(io.get_book(c.bookTitle)));
                                ArrayList<String> words = c.findPhraseInLines(phrase, distance, bookScanner);
                                System.out.println(String.format("Words within %d distance in lines of \"%s\"", distance, phrase));
								for (String s : words) System.out.println(s);
                                break;
                            }
                            case "rank": {
                                if (qs.length < 2) {
                                    System.out.println("\nNeed the word you want to get the of.");
                                }
                                // don't load in ranks until we need to.
                                if (ranks.isEmpty()) {
                                    ranks = c.rankData();
                                }

                                int i;
                                for ( i = 0; i < ranks.size(); i++) {
                                    if (ranks.get(i).word.equals(qs[1]))
                                        break;
                                }
                                if (i < ranks.size()) {
                                    Concordance.LineData data = ranks.get(i);
                                    System.out.println(String.format("%s is ranked %d out of %d with %d appearances.", data.word, i, ranks.size(), data.count));
                                }
                                else {
                                    System.out.println("Word not found in the text.");
                                }
                                break;
                            }
                            case "exit": {
                                line = "";
                                break;
                            }
                            default:
                                if (!line.equals(""))
                                    System.out.println("Command not recognized.");
                                break;
                        }
                    }while (!line.equals(""));

                    break;
                case "exit": {
                    command = "";
                    break;
                }
                default:
                    System.out.println("command not recognized");
                    break;
            }
		} while (!command.equals(""));
	}
}

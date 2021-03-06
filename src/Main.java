import Concordance.Concordance;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    /*
        Main class serves to only provide the command line so far. Final product should break these commands into their
        own method the main class handles so that it isn't one long method.
     */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(System.in);
		IO.IO io = new IO.IO();
		String command = "";

        System.out.println("\t\t==== Welcome to ConcordanceCreate4000! ====");
        System.out.println("\n\t You can now create concordance from our library and query it with our query commands!");
        System.out.println("\t Get started with the \"h\" command to see the commands you can use!");

        boolean done = false;
		do {
            System.out.print("Enter command (h for help): ");
            command = scanner.nextLine();

            if (command.equals("")){
                System.out.println("Good-bye.");
                done = true;
                break;
            }

            String[] commands = command.split(" ");
            // commands are in commands[0], when adding a new command create another "case "yourcommand": " and
            // handle it within that block.
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
                    // get name, may have spaces
                    for (int i = 1; i < commands.length; i++)
                        s += commands[i];
                    File cfile = io.get_book(s);
                    // may not find file
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
                        // open file explorer
                        Concordance concordance = new Concordance();
                        JFileChooser  chooser = new JFileChooser();
                        // restrict it to only select text files
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                        chooser.setFileFilter(filter);
                        int returnval = chooser.showOpenDialog(new JFrame("Choose a file"));
                        if (returnval == JFileChooser.APPROVE_OPTION) {
                            File file = chooser.getSelectedFile();
                            io.saveNewBook(file);
                            BufferedReader fileChosen = new BufferedReader(new FileReader(file));
                            concordance.bookTitle = file.getName().substring(0, file.getName().indexOf(".txt"));
                            System.out.println("Creating concordance....");
                            // will return false if it has no preambles
                            if (!concordance.create(fileChosen)) {
                                // no preambles, so just read from start
                                fileChosen = new BufferedReader(new FileReader(file));
                                concordance.createFromFile(fileChosen, 0);
                            }
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
                        Concordance concordance = new Concordance();
                        // because we want it to be a .ser when saved
                        concordance.bookTitle = book;
                        try {
                            BufferedReader file = new BufferedReader(new FileReader(io.get_book(book)));
                            System.out.println("\nCreating concordance...");
                            if (!concordance.create(file)){
                                file = new BufferedReader(new FileReader(io.get_book(book)));
                                concordance.createFromFile(file, 0);
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
                    if (files.size() == 0) System.out.println("No books found.\n");
                    else
                        for (String s : files) System.out.println(s);
                    System.out.println("\nYour favorite book isn't here?\n" +
                            "Download it from https://www.gutenberg.org/ and use the command \"createconcordance\" (no arguments)\n" +
                            "and to navigate to where you downloaded the book, select it, and we will store it here!\n");
                    break;
                case "concordances":
                    ArrayList<String> concordances = io.get_concordance_list();
                    System.out.println("\n\t=== Concordances saved ===\n");
                    if (concordances.size() == 0)
                        System.out.println("No saved concordances.");
					else {
						for (String s : concordances) System.out.println(s);
                        System.out.println();
                    }
                    break;
                case "concordanceWith":
                    if (commands.length < 3) {
                        System.out.println("Invalid arguments, need a word then a number");
                        break;
                    }
                    String word = commands[1];

                    int n = 0;
                    try {
                        n = Integer.parseInt(commands[2]);
                    } catch (Exception e) {
                        System.out.println("The second argument needs to be a number.");
                    }

                    ArrayList<String> cons = new ArrayList<>();
                    for (String s : io.get_concordance_list()) {
                        try{
                            Concordance c = io.loadConcordance(s);
                            if (c.hasWordNTimes(word, n))
                                cons.add(s);
                        } catch (Exception e) { }
                    }
                    System.out.println(String.format("\n\tConcordances with \"%s\" appearing %d or more times.\n", word, n));
                    for (String s : cons)
                        System.out.println(s);
                    System.out.println();
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
                        System.out.println(String.format("Could not find the concordance. Check your spelling, or try creating the concordance first with \"createconcordance %s\" " +
                                "if you have the book saved.\nIf not, save the book with \"createconcordance\" and navigate to your book on your machine.", name));
                        break;
                    }
                    String line = "";
                    System.out.println("\nConcordance loaded. You can now perform queries on this concordance\n");
                    Scanner query = new Scanner(System.in);

                    ArrayList<String> toIgnore = new ArrayList<>();

                    // begin query mode
                    boolean finished = false;
                     do {
                        System.out.print("What queries would you like to do? (h for query commands): ");
                        line = query.nextLine();
                         if (line.equals("")) {
                             finished = true;
                             break;
                         }
                        String[] qs = line.split(" ");
                         // commands for queries here
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
                                        "h | displays the list of legal query commands.\n" +
                                        "ignore <words> | marks the words to be ignored in queries.\n" +
                                        "de-ignore <words> | words specified will no longer be ignored.");
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
                                ArrayList<String> words = c.wordsDistanceInLines(qs[1], distance, toIgnore);
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
                                ArrayList<String> words = c.wordsDistanceInWords(qs[1], distance, bookScanner, toIgnore);
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
                                ArrayList<String> words = c.findPhraseInLines(phrase, distance, bookScanner, toIgnore);
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
                                    ranks = c.rankData(toIgnore);
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
                            case "ignore": {
                                if (qs.length < 2) {
                                    System.out.println("Please enter in words that you would want to exclude from queries, separated by spaces.");
                                    break;
                                }
                                for (int i = 1; i < qs.length; i++){
                                    System.out.println(String.format("%s will be ignore in future queries.", qs[i]));
                                    toIgnore.add(qs[i]);
                                }
                                break;
                            }
                            case "de-ignore": {
                                if (qs.length < 2) {
                                    System.out.println("Please enter in words that you previously marked as ignored separated by spaces.");
                                    break;
                                }
                                for (int i = 0; i < qs.length; i++){
                                    if (toIgnore.contains(qs[i])) {
                                        System.out.println(String.format("%s will no longer be ignored in future queries.", qs[i]));
                                        toIgnore.remove(qs[i]);
                                    }
                                    else{
                                        System.out.println(String.format("%s was not found in the list of words to ignore.", qs[i]));
                                    }
                                }
                            }
                            case "exit": {
                                line = "";
                                System.out.println("Exiting query mode.");
                                finished = true;
                                break;
                            }
                            default:
                                if (!line.equals(""))
                                    System.out.println("Command not recognized.");
                                break;
                        }
                    }while (!line.equals("") && !finished);

                    break;
                case "exit": {
                    command = "";
                    System.out.println("Good-bye");
                    done = true;
                    break;
                }
                default:
                    System.out.println("command not recognized");
                    break;
            }
		} while (!command.equals("") && !done);
        scanner.close();
	}
}

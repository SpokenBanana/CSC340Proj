import Concordance.Concordance;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		IO.IO io = new IO.IO();
		Concordance concordance = new Concordance();

        // to let loop pass once at least - will replace with do-while.
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
                case "-h":
                    System.out.println("Here are the commands\n" +
                            "-searchcon <concordance name> | checks if concordance exists\n" +
                            "-serachbook <book name> | checks if book exists.\n" +
                            "-createconcordnace <book file name> | creates and saves a concordance.\n" +
                            "-loadconcordance <concordance file name> | loads the concordance and allows you to perform queries on it.\n" +
                            "-books | returns a list of books.\n" +
                            "-concordances | returns a list of saved concordances\n");
                    break;
                case "-searchcon": {
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
                case "-searchbook": {
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
                case "-createconcordance":
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
                    try{
                        System.out.println("loading...");

                        // de-serialize
                        FileInputStream fin = new FileInputStream("src/Concordances/" + name);
                        ObjectInputStream in = new ObjectInputStream(fin);
                        Concordance c = (Concordance) in.readObject();

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
                                            "-lines <word> | Returns the line numbers the word appears in.");
                                    break;
                                case "-count": {
                                    Concordance.LineData data = c.concordanceData.get(qs[1]);
                                    if (data != null) {
                                        System.out.println(data.getCount());
                                    } else {
                                        System.out.println("Word not found.");
                                    }
                                    break;
                                }
                                case "-lines": {
                                    Concordance.LineData data = c.concordanceData.get(qs[1]);
                                    data.getLines().forEach(System.out::println);
                                    break;
                                }
                                default:
                                    if (!line.equals(""))
                                        System.out.println("Command not recognized.");
                                    break;
                            }
                        }while (!line.equals(""));
                    } catch (Exception e) {
                        System.out.println("Could not find the concordance, try again");
                    }
                    break;
                default:
                    System.out.println("command not recognized");
                    break;
            }
		}while (!command.equals(""));
	}
}

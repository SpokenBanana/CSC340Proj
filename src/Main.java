import Concordance.Concordance;

import java.io.File;
import java.util.Scanner;

public class Main {
	/*
		valid commands
		-searchcon <name>
		-searchbook <name>
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		IO.IO io = new IO.IO();
		Concordance concordance = new Concordance();
		System.out.println("Enter command: ");
		String commmand = scanner.nextLine();
		String[] commands = commmand.split(" ");
		switch (commands[0]) {
			case "-h":
				System.out.println("Here are the commands\n" +
						"-searchcon <concordance name> returns a list of saved concordances\n" +
						"-serachbook <book name> returns a list of saved books.");
				break;
			case "-searchcon": {
				String s = "";
				for (int i = 1; i < commands.length; i++)
					s += commands[i];
				System.out.println(io.get_concordance_list().toString());
				break;
			}
			case "-searchbook": {
				String s = "";
				for (int i = 1; i < commands.length; i++)
					s += commands[i];
				System.out.println(io.get_book_list().size());
				break;
			}
			case "-createconcordance":
				String book = "";
				for (int i = 1; i < commands.length; i++) {
					book += commands[i];
				}
				try {
					Scanner file = new Scanner(io.get_book(book));
					io.save_concordance(concordance.create(file));
					System.out.println("Concordance created.");
				} catch (Exception e) {
					System.out.println("Book not found.");
				}
				break;
		}
	}
}

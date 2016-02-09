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
		System.out.println("Enter command: ");
		String commmand = scanner.nextLine();
		String[] commands = commmand.split(" ");
		if (commands[0].equals("-h")) {
			System.out.println("Here are the commands\n" +
					"-searchcon <concordance name> returns a list of saved concordances\n" +
					"-serachbook <book name> returns a list of saved books.");
		}
		else if (commands[0].equals("-searchcon")) {
			String s = "";
			for (int i = 1;  i < commands.length; i++)
				s += commands[i];
			System.out.println(io.search_concordnace(s).toString());
		}
		else if (commands[0].equals("-searchbook")) {
            String s = "";
			for (int i = 1;  i < commands.length; i++)
				s += commands[i];
			System.out.println(io.get_book_list(s).size());
		}
	}
}

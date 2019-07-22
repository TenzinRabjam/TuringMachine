import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TuringMachine {

	private static final String LEFT = "L";
	private static final String RIGHT = "R";
	private static final String FIRST_RUN = "";
	private static final int MAX_RUN = 100;

	public static void main(String[] args) throws FileNotFoundException {
		String file = args[0];// "doublea.txt";// 
		String word =  args[1];// "bbaa";// 
		
		// Split word into list
		ArrayList<Character> letters = new ArrayList<>();
		for (int i = 0; i < word.length(); i++)
			letters.add(word.charAt(i));

		ArrayList<Item> states = getFileItems(file);

		String nextState = FIRST_RUN;
		boolean success = false;
		int counter = 0;

		try {
			for (int i = 0; i < letters.size(); i++) {

				for (int j = 0; j < states.size(); j++) {
					
					if(success) 
						break;

					if(counter++ > MAX_RUN)
						throw new RejectedWordException();

					Item state = states.get(j);

					// Find the next state to run if it has value
					if (nextState.isEmpty() || (!nextState.isEmpty() && nextState.equals(state.from))) {
						
						Character currentLetter = letters.get(i);
						
						if (currentLetter.toString().equalsIgnoreCase(state.letter)) {
							if (isDirectionValid(i, state)) {
								throw new RejectedWordException();
							} 

							// Now we have found the matching letter
							Character oldLetter = state.letter.trim().charAt(0);
							Character newLetter = state.newLetter.trim().charAt(0);

							if (!oldLetter.equals(newLetter)) {
								// Replace old letter with new letter
								letters.set(i, newLetter);
							}

							// Check which direction to move
							if (state.direction.equals(LEFT)) {
								if(i < 1){
									i--;
									throw new RejectedWordException();
								}
									
							}

							// Where to go next
							nextState = state.toState;

							if (nextState.contains("Halt")) {
								success = true;
							} else if (i == letters.size() - 1) {
								letters.add('_');
							}

							 break;
						}
					}

				}
			}
		} catch (RejectedWordException ex) {
			
		}
		
		if (success) {
			System.out.printf("Accepted: %s", word);
		} else {
			System.out.printf("Rejected: %s", word);
		}
	}

	// The direction of the first letter cannot be 'L'
	private static boolean isDirectionValid(int index, Item state) {
		return index == 0 && state.direction.equalsIgnoreCase(LEFT);
	}

	public static ArrayList<Item> getFileItems(String file) throws FileNotFoundException {
		Scanner input = new Scanner(new File(file));

		// Skip the first line
		input.nextLine();

		ArrayList<Item> states = new ArrayList<>();
		while (input.hasNext()) {
			String[] lines = input.nextLine().split(",");
			if (lines.length > 1)
				states.add(new Item(lines[0], lines[1], lines[2], lines[3], lines[4]));
		}

		input.close();
		return states;
	}
}

class RejectedWordException extends Exception {

	public RejectedWordException() {
		super("Rejected");
	}

	public RejectedWordException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return "Rejected";
	}
}

class Item {

	String from;
	String letter;
	String newLetter;
	String direction;
	String toState;

	public Item(String from, String letter, String newLetter, String direction, String toState) {
		this.from = from.trim();
		this.letter = letter.trim();
		this.newLetter = newLetter.trim();
		this.direction = direction.trim();
		this.toState = toState.trim();
	}

}

public class Consumer implements Runnable {

	private final SharedArea sharedArea; 							// reference to shared object
	private int number = 0;
	private int correctThisTime = 0;
	private int[] myGuesses;
	private String guesses = "";
	private int guess;
	private String again;
	private boolean quit = false;

	public Consumer( SharedArea shared ) 
	{ 
		sharedArea = shared;
	} 

	@Override
	public void run()                                           
	{
		while (!quit) {
			correctThisTime = 0;										//reset variables specific to individual game
			myGuesses = new int[5];
			try { number = sharedArea.get(); }
			catch (InterruptedException e) { e.printStackTrace(); }

			System.out.println("==============================\n\tGuess the Number\n==============================\n");

			for(int i = 0;i<5;i++)									//Loops for 5 guesses
			{
				do {
					guess = Handy.readInt("  Guess Number "+(i+1)+": ");
					if (guess < 1 || guess > 10) {
						System.out.println("Please choose between 1 and 10");
					}
				} while (guess < 1 || guess > 10);

				myGuesses[i] = guess;
			}

			for (int k =0 ; k <5; k++) {							//compare guesses to random number
				guesses += myGuesses[k] + " ";
				if ((myGuesses[k]) == number) {
					((SyncSharedArea)sharedArea).setCorrect();
					correctThisTime++;
				}
			}

																	//Print game statisticxs
			System.out.printf("\n\tNumber:\t\t\t\t%d\n\tYou guessed:\t\t\t%s\n\tCorrect guesses this time:\t%d\n\tTotal correct guesses:\t\t%d\n", 
					number, guesses, correctThisTime, ((SyncSharedArea)sharedArea).getCorrect());

																	//check if player wants to quit or continue
			again = Handy.clearReadString("\tWould you like to play again?\n\t\tYes\t-\tPress Y\n\t\tNo\t-\tany other key\t");
			if( again.equalsIgnoreCase("y"))
			{
				quit = false;
				guesses = "";
			}
			else
			{
				System.out.println("==============================\n\tGoodbye\n==============================\n");
				((SyncSharedArea)sharedArea).setLoop(false);
				return;
			}
		}
	}
}
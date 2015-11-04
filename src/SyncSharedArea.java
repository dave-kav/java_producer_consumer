
public class SyncSharedArea implements SharedArea {

	private int sharedNumber = -1;						// shared by producer and consumer threads
	private boolean occupied = false;					// state of buffer
	private int correctGuesses = 0;
	private boolean loop = true;

	public synchronized void set( int value ) throws InterruptedException
	{
		while ( occupied ) 
		{
			wait();										// while no empty locations - thread in waiting state
		} 

		sharedNumber = value; 							// set new shared value
		occupied = true;								// indicate that producer can't store another until consumer current buffer value is retrieved 
		notify(); 										// tell waiting thread to enter runnable state 
	} 

	public synchronized int get() throws InterruptedException
	{
		while ( !occupied )								//if nothing in buffer, wait
		{
			wait();
		} 
		occupied = false;								//indicate buffer is no longer occupied
		notify();										//tell waiting thread to enter runnable state
		return sharedNumber;
	}

	public int getCorrect() { return correctGuesses; }
		
	public void setCorrect() { correctGuesses++; }
	
	public boolean getLoop() { return loop; }

	public void setLoop(boolean l) { loop = l; }

}

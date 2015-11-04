import java.util.Random;

public class Producer implements Runnable {

	private final static Random generator = new Random();
	private final SharedArea sharedArea; // reference to shared object
	private boolean loop = true;

	public Producer( SharedArea shared )
	{
		sharedArea = shared;
	} 

	@Override
	public void run() {
		while (loop) {
			try {
				sharedArea.set( generator.nextInt(10)+1 ); // set value in sharedArea
			}

			catch ( InterruptedException exception ) 
			{
				exception.printStackTrace();
			} 
			loop = ((SyncSharedArea)sharedArea).getLoop();
		}
	}
}

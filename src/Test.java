import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

	public static void main ( String [] args) {
		//create shared area and thread pool to manage threads 
		ExecutorService application;
		SharedArea sharedArea;
		application = Executors.newCachedThreadPool();		
		sharedArea = new SyncSharedArea();

		// execute the Producer and Consumer tasks
		application.execute( new Producer( sharedArea ) );
		application.execute( new Consumer( sharedArea ) );

		//terminate threads
		application.shutdown();
	}
}

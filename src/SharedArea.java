
public interface SharedArea {

	public void set( int value ) throws InterruptedException; 

	public int get() throws InterruptedException;

}

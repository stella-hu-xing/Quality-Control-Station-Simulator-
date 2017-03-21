package solution2;

/**
 * A short belt is similar to Belt but only receive bicycle from inspector and
 * sent them to Consumer
 * 
 * @author xinghu
 *
 */
public class ShortBelt extends Belt {

	// the items in the belt segments
	protected Bicycle[] segment_shortBelt;

	// the length of this belt
	protected int shortBeltLength = 2;

	// to help format output trace
	final private static String indentation = "                  ";

	/**
	 * Create a new, empty belt, initialised as empty
	 */
	public ShortBelt() {
		segment_shortBelt = new Bicycle[shortBeltLength];
		for (int i = 0; i < segment_shortBelt.length; i++) {
			segment_shortBelt[i] = null;
		}
	}

	/**
	 * Put a bicycle on the belt.
	 * 
	 * @param bicycle
	 *            the bicycle to put onto the belt.
	 * @param index
	 *            the place to put the bicycle
	 * @throws InterruptedException
	 *             if the thread executing is interrupted.
	 */
	public synchronized void put(Bicycle bicycle, int index) throws InterruptedException {

		// while there is another bicycle in the way, block this thread
		while (segment_shortBelt[index] != null) {
			wait();
		}

		// insert the element at the specified location
		segment_shortBelt[index] = bicycle;

		// make a note of the event in output trace
		System.out.println(bicycle + " arrived" + "  segment " + (index + 1) + " at Short Belt");

		// notify any waiting threads that the belt has changed
		notifyAll();
	}

	/**
	 * Take a bicycle off the end of the belt
	 * 
	 * @return the removed bicycle
	 * @throws InterruptedException
	 *             if the thread executing is interrupted
	 */
	public synchronized Bicycle getEndBelt() throws InterruptedException {

		Bicycle bicycle;

		// while there is no bicycle at the end of the belt, block this thread
		while (segment_shortBelt[segment_shortBelt.length - 1] == null) {
			wait();
		}

		// get the next item
		bicycle = segment_shortBelt[segment_shortBelt.length - 1];
		segment_shortBelt[segment_shortBelt.length - 1] = null;

		// make a note of the event in output trace

		System.out.println(indentation + indentation + bicycle + " departed from short Belt");

		// notify any waiting threads that the belt has changed
		notifyAll();
		return bicycle;
	}

	/**
	 * Move the belt along one segment
	 * 
	 * @throws OverloadException
	 *             if there is a bicycle at position beltLength.
	 * @throws InterruptedException
	 *             if the thread executing is interrupted.
	 */
	public synchronized void move() throws InterruptedException, OverloadException {

		// if there is something at the end of the belt,
		// or the belt is empty, do not move the belt
		while (isEmpty() || segment_shortBelt[segment_shortBelt.length - 1] != null) {
			wait();
		}

		// double check that a bicycle cannot fall of the end
		if (segment_shortBelt[segment_shortBelt.length - 1] != null) {
			String message = "Bicycle fell off end of " + " belt";
			throw new OverloadException(message);
		}

		// move the elements along, making position 0 null
		for (int i = segment_shortBelt.length - 1; i > 0; i--) {
			if (this.segment_shortBelt[i - 1] != null) {
				System.out.println(indentation + this.segment_shortBelt[i - 1] + " [ shortBlet_s" + (i)
						+ " ->shortBlet_s " + (i + 1) + " ]");
			}
			segment_shortBelt[i] = segment_shortBelt[i - 1];
		}
		segment_shortBelt[0] = null;

		// notify any waiting threads that the belt has changed
		notifyAll();
	}

	/**
	 * Check whether the belt is currently empty
	 * 
	 * @return true if the belt is currently empty, otherwise false
	 */
	private boolean isEmpty() {
		for (int i = 0; i < segment_shortBelt.length; i++) {
			if (segment_shortBelt[i] != null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return the maximum size of this belt
	 */
	public int length() {
		return shortBeltLength;
	}

}

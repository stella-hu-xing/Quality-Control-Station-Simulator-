package solution1;

/**
 * 
 * The bicycle quality control belt, contains all its attributes and control
 * methods.
 *
 */
public class Belt {

	// the items in the belt segments
	protected Bicycle[] segment;

	// the length of this belt
	protected int beltLength = 5;

	// the segment which the sensor would check and robot would take bicycle and
	// put back
	protected int segmentToCheck = 3;

	// the figure represents whether the belt has moved once
	private boolean hasMoved = false;

	// the move status of the belt
	private volatile boolean canMove = true;

	// to help format output trace
	final public static String indentation = "                  ";

	/**
	 * Create a new, empty belt, initialised as empty
	 */
	public Belt() {
		segment = new Bicycle[beltLength];
		for (int i = 0; i < segment.length; i++) {
			segment[i] = null;
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
		while (segment[index] != null) {
			wait();
		}

		// insert the element at the specified location
		segment[index] = bicycle;

		// make a note of the event in output trace
		System.out.println(bicycle + " arrived" + "  segment " + (index + 1));

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
		while (segment[segment.length - 1] == null) {
			wait();
		}

		// get the next item
		bicycle = segment[segment.length - 1];
		segment[segment.length - 1] = null;

		// make a note of the event in output trace
		System.out.print(indentation + indentation);
		System.out.println(bicycle + " departed");

		// notify any waiting threads that the belt has changed
		notifyAll();

		return bicycle;
	}

	/**
	 * Take a bicycle off the segment 3 from the belt
	 * 
	 * @return the removed bicycle
	 * @throws InterruptedException
	 *             if the thread executing is interrupted
	 * @throws DefKnownException
	 *             if the removed bicycle is not tagged as defective
	 */
	public synchronized Bicycle removeBicycle() throws InterruptedException, DefKnownException {

		// get the elemnt at the check segment
		Bicycle bike = segment[segmentToCheck - 1];

		// clear the current segment
		segment[segmentToCheck - 1] = null;

		// if the bike is not tagged as defective, throw an exception
		if (bike.isTagged() == false) {
			throw new DefKnownException("get a wrong bicycle without tag");
		}

		// make a note of the event in output trace
		System.out.println(indentation + indentation + indentation + "the bike " + bike
				+ " has been taken from belt by robot arm ");

		return bike;
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

		// if there is something at the end of the belt, or the belt is empty,
		// do not move the belt
		while (isEmpty() || segment[segment.length - 1] != null) {
			wait();
		}

		// double check that a bicycle cannot fall of the end
		if (segment[segment.length - 1] != null) {
			String message = "Bicycle fell off end of " + " belt";
			throw new OverloadException(message);
		}

		// move the elements along, making position 0 null
		for (int i = segment.length - 1; i > 0; i--) {
			if (this.segment[i - 1] != null) {
				System.out.println(indentation + this.segment[i - 1] + " [ s" + (i) + " -> s" + (i + 1) + " ]");
			}
			segment[i] = segment[i - 1];
		}
		segment[0] = null;

		// declare the a movement has been taken
		hasMoved = !hasMoved;

		// notify any waiting threads that the belt has changed
		notifyAll();
	}

	/**
	 * @return the maximum size of this belt
	 */
	public int length() {
		return beltLength;
	}

	/**
	 * Peek at what is at a specified segment
	 * 
	 * @param index
	 *            the index at which to peek
	 * @return the bicycle in the segment (or null if the segment is empty)
	 */
	public Bicycle peek(int index) {
		Bicycle result = null;
		if (index >= 0 && index < beltLength) {
			result = segment[index];
		}
		return result;
	}

	/**
	 * Check whether the belt is currently empty
	 * 
	 * @return true if the belt is currently empty, otherwise false
	 */
	private boolean isEmpty() {
		for (int i = 0; i < segment.length; i++) {
			if (segment[i] != null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Get the final position of the belt
	 * 
	 * @return the final position on the belt
	 */
	public int getEndPos() {
		return beltLength - 1;
	}

	/**
	 * Set the belt status to be runnable from stop
	 */
	public void setBeltCanMove() {
		canMove = true;
	}

	/**
	 * Set the belt status to be stop
	 */
	public void setBeltStop() {
		canMove = false;
	}

	/**
	 * Check whether the belt is able to move currently
	 * 
	 * @return true if the belt is currently able to move
	 */
	public boolean isCanMove() {
		return canMove;
	}

	/**
	 * Check whether the belt has moved
	 * 
	 * @return true if the belt has moved
	 */
	public boolean haveMovedOnce() {
		return hasMoved;
	}

	public String toString() {
		return java.util.Arrays.toString(segment);
	}
}

package solution2;

import java.util.Random;

/**
 * A consumer continually tries to take bicycles from the end of a quality
 * control belt
 */

public class Consumer extends BicycleHandlingThread {

	// the belt from which the consumer takes the bicycles
	protected Belt belt;
	protected ShortBelt shortBelt;

	/**
	 * Create a new Consumer that consumes from a belt
	 */
	public Consumer(Belt belt, ShortBelt shortBelt) {
		super();
		this.belt = belt;
		this.shortBelt = shortBelt;
	}

	/**
	 * Loop indefinitely trying to get bicycles from the quality control belt
	 */
	public void run() {
		while (!isInterrupted()) {
			try {
				// if the last position of main belt is not vacant, get the last
				// element
				if (belt.segment[belt.length() - 1] != null) {
					belt.getEndBelt();
				}
				// if the last position of short belt is not vacant, get the
				// last element
				if (shortBelt.segment_shortBelt[shortBelt.length() - 1] != null) {
					shortBelt.getEndBelt();

				}

				// let some time pass ...
				Random random = new Random();
				int sleepTime = Params.CONSUMER_MIN_SLEEP
						+ random.nextInt(Params.CONSUMER_MAX_SLEEP - Params.CONSUMER_MIN_SLEEP);
				sleep(sleepTime);
			} catch (InterruptedException e) {
				this.interrupt();
			}
		}
		System.out.println("Consumer terminated");
	}
}

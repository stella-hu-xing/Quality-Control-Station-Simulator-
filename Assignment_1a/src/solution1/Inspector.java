package solution1;

/**
 * A inspector is aimed to check the tagged bicycle defective or not
 */
public class Inspector {

	// to represent the inspector is available currently or not
	protected volatile boolean isInspectorAvailable = true;

	// to help format output trace
	final public static String indentation = "                  ";

	/**
	 * Inspect the given tagged bicycle whether is defective or not
	 * 
	 * @param bike
	 *            given by robot which need to check
	 * @return the bicycle has been checked and make modification if it is
	 *         wrongly tagged
	 * @throws InterruptedException
	 *             if the thread executing is interrupted.
	 * @throws DefKnownException
	 *             if the bicycle is not tagged
	 */
	protected synchronized Bicycle inspect(Bicycle bike) throws InterruptedException, DefKnownException {

		// while there is no bicycle at the inspector, block this thread and
		// wait for a bicycle
		while (isInspectorAvailable == true) {
			wait();
		}

		// make a note of the event in output trace
		System.out.println(indentation + indentation + indentation
				+ "The Inspctor has received the bicycle and is ready to check " + bike + "in a inspect time");

		// the inspection need time to execute
		Thread.sleep(Params.INSPECT_TIME);

		// if the bike is not tagged, throw an exception to report this problem
		if (bike.isTagged() == false) {
			String message = "Robot get a wrong bicycle";
			throw new DefKnownException(message);
		}

		// if the bike is tagged but not defective, clear its tag and set it as
		// inspected in case of repeated inspection
		if (bike.isTagged() == true && bike.isDefective() != true) {

			// clear its tag
			bike.setNotTagged();

			// set it as inspected
			bike.setInspected();

			// make a note of the event in output trace
			System.out.println(indentation + indentation + indentation + "inspector have checked the wrong-tagged bike "
					+ bike + " and correct");

		} else {
			// make a note of the event in output trace
			System.out.println(indentation + indentation + indentation + "inspector have checked the tagged bike "
					+ bike + " and prove the tag");

		}
		// release the block of inspector and set it available again
		setInspectorAvailable();

		// notify any waiting threads that the inspector has changed
		notifyAll();

		return bike;
	}

	/**
	 * Check whether the inspector is in use currently
	 * 
	 * @return true if the inspector is available
	 */
	public boolean isInspectorAvailable() {
		return isInspectorAvailable;
	}

	/**
	 * Set the inspector is currently not in use
	 */
	public void setInspectorAvailable() {
		isInspectorAvailable = true;
	}

	/**
	 * Set the inspctor is currently in use
	 */
	public void setInspectorOccupied() {
		isInspectorAvailable = false;
	}
}

package solution2;

/**
 * A robot is responsible to transfer target bicycle between belt and inspector
 * 
 * @author xinghu
 *
 */
public class Robot extends BicycleHandlingThread {

	// to represent the robot is currently available or not
	protected volatile boolean isRobotOccupied = false;

	// the belt which robot would get bicycle from
	protected Belt belt;

	// the belt which robot would put bicycle to
	protected ShortBelt shortBelt;

	// the inspector which robot would put bicycle to and get from
	protected Inspector inspector;

	/**
	 * Create a new robot, holding a main belt, a short belt and a inspector
	 * 
	 * @param belt
	 * @param inspector
	 * @param shortBelt
	 */
	public Robot(Belt belt, Inspector inspector, ShortBelt shortBelt) {
		this.belt = belt;
		this.inspector = inspector;
		this.shortBelt = shortBelt;
	}

	/**
	 * Loop indefinitely trying to get bicycles from the quality control belt if
	 * condition suits and without interruption
	 */
	public void run() {
		while (!isInterrupted()) {

			// if robot and inspector both are accessible,transfer the bicycle
			// to inspect
			if (isRobotOccupied == true && inspector.isInspectorAvailable() == true) {
				try {

					// transfer bicycle to inspector,waiting inspection and put
					// it back
					transferBetweenInspectorAndBelt();

					// after inspection, set the robot to be available back
					setRobotAvailable();

				} catch (InterruptedException e) {

					e.printStackTrace();
					this.interrupt();
				} catch (DefKnownException e) {

					e.printStackTrace();
				}

			}

		}
	}

	/**
	 * this is a 3 in 1 action.It undertakes the transfer bicycle from belt to
	 * inspector, waiting the inspection result and put the bicycle back from
	 * inspector to belt
	 * 
	 * @throws InterruptedException
	 *             if the thread executing is interrupted
	 * @throws DefKnownException
	 *             if the removed bicycle is not tagged as defective
	 */
	protected synchronized void transferBetweenInspectorAndBelt() throws InterruptedException, DefKnownException {

		// make a note of the event in output trace
		System.out.println();
		System.out.println(belt.indentation + belt.indentation + belt.indentation
				+ "robot is ready to take tagged bicycle from main belt");

		// get bicycle from belt
		Bicycle bike = belt.removeBicycle();

		if (bike != null) {

			// the robot need some time to execute the transfer movement
			sleep(Params.ROBOT_MOVE_TIME);

			// make a note of the event in output trace
			System.out.println(belt.indentation + belt.indentation + belt.indentation + "robot get bicycle and put "
					+ bike + "  to inspector");

			// set the inspector to be not accessible
			inspector.setInspectorOccupied();

			// let inspector check the bicycle and get the result
			bike = inspector.inspect(bike);

			// make a note of the event in output trace
			System.out.println(belt.indentation + belt.indentation + belt.indentation
					+ "robot is waiting segment3 availabe to put  " + bike + "  back to belt");

			// the robot need some time to execute the transfer movement
			sleep(Params.ROBOT_MOVE_TIME);

			// put the checked bicycle to the short belt
			shortBelt.put(bike, 0);

			// notify all waiting threads of main belt the changes of belt
			synchronized (belt) {

				// set belt can move now
				belt.setBeltCanMove();

				/// make a note of the event in output trace
				System.out.println();
				System.out.println(belt.indentation + "the main belt now can move due to the avaiable robot");

				belt.notifyAll();

			}
		}
	}

	/**
	 * Check whether the robot is available currently
	 * 
	 * @return true if the robot is occupied
	 */
	public boolean isRobotOccupied() {
		return isRobotOccupied;
	}

	/**
	 * Set the robot state to be occupied
	 */
	public void setRobotOccupied() {
		isRobotOccupied = true;
	}

	/**
	 * Set the robot state to be available
	 */
	public void setRobotAvailable() {
		isRobotOccupied = false;
	}

}

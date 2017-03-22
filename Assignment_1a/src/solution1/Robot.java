package solution1;

/**
 * A robot is responsible to transfer target bicycle between belt and inspector
 * 
 * @author xinghu
 *
 */
public class Robot extends BicycleHandlingThread {

	// to represent the robot is currently available or not
	protected volatile boolean isRobotOccupied = false;

	// the belt which robot would get bicycle from and put to
	protected Belt belt;

	// the inspector which robot would put bicycle to and get from
	protected Inspector inspector;

	/**
	 * Create a new robot, holding a belt and a inspector
	 * 
	 * @param belt
	 * @param inspector
	 */
	public Robot(Belt belt, Inspector inspector) {
		super();
		this.belt = belt;
		this.inspector = inspector;
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

				} catch (InterruptedException | DefException e) {

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
	 * @throws DefException
	 *             if the removed bicycle has not been inspected
	 * @throws DefKnownException
	 *             if the removed bicycle is not tagged as defective
	 */
	protected synchronized void transferBetweenInspectorAndBelt()
			throws InterruptedException, DefException, DefKnownException {

		// make a note of the event in output trace
		System.out.println(
				belt.indentation + belt.indentation + belt.indentation + "robot is ready to take bicycle from belt");

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

			// put the checked bicycle back to the belt
			belt.put(bike, 2);

			// make a note of the event in output trace
			System.out.println(belt.indentation + belt.indentation + belt.indentation + "robot has sucessfully put "
					+ bike + "  back to belt");
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

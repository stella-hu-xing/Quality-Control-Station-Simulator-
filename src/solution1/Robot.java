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
		this.belt = belt;
		this.inspector = inspector;
	}

	/**
	 * Loop indefinitely trying to get bicycles from the quality control belt if
	 * condition suits and without interruption
	 */
	public void run() {

		while (!isInterrupted()) {

			if (isRobotOccupied == true && inspector.isInspectorAvailable() == true) {
				try {

					transferBetweenInspectorAndBelt();
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

	protected synchronized void transferBetweenInspectorAndBelt()
			throws InterruptedException, DefException, DefKnownException {

		System.out.println(
				belt.indentation + belt.indentation + belt.indentation + "robot is ready to take bicycle from belt");

		Bicycle bike = belt.removeBicycle();

		if (bike != null) {

			sleep(Params.ROBOT_MOVE_TIME);

			System.out.println(belt.indentation + belt.indentation + belt.indentation + "robot get bicycle and put "
					+ bike + "  to inspector");

			inspector.setInspectorOccupied();

			bike = inspector.inspect(bike);

			System.out.println(belt.indentation + belt.indentation + belt.indentation
					+ "robot is waiting segment3 availabe to put  " + bike + "  back to belt");

			sleep(Params.ROBOT_MOVE_TIME);

			belt.put(bike, 2);

			System.out.println(belt.indentation + belt.indentation + belt.indentation + "robot has sucessfully put "
					+ bike + "  back to belt");
		}
	}

	public boolean isRobotOccupied() {
		return isRobotOccupied;
	}

	public void setRobotOccupied() {
		isRobotOccupied = true;
	}

	public void setRobotAvailable() {
		isRobotOccupied = false;
	}

}

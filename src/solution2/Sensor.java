package solution2;

/**
 * Sensor is responsible to check all bicycle at the segment 3 of the belt
 * 
 * @author xinghu
 *
 */
public class Sensor extends BicycleHandlingThread {

	// the belt which sensor would check
	protected Belt belt;

	// the robot which sensor would inform
	protected Robot robot;

	/**
	 * Create a new Sensor with belt and robot
	 * 
	 * @param belt
	 * @param robot
	 */
	public Sensor(Belt belt, Robot robot) {
		super();
		this.belt = belt;
		this.robot = robot;

	}

	// a temporary variable to check whether the belt has moved or not
	private boolean temp = false;

	public void run() {

		while (!isInterrupted()) {

			// get the bicycle at segment3 of the belt
			Bicycle bike = belt.peek(2);

			// only bicycle is not null, tagged, it
			// would be inspected
			if (bike != null && bike.isTagged() == true) {

				// make a note of the event in output trace
				System.out.println(
						belt.indentation + belt.indentation + belt.indentation + "sensor find a tagged bike " + bike);

				// if the robot is not available ,then stop the belt, otherwise
				// block the robot
				if (robot.isRobotOccupied() == false) {
					robot.setRobotOccupied();
				} else {
					belt.setBeltStop();
				}
			}

		}

		System.out.println("Sensor terminated");
	}

}

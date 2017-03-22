package solution1;

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
			
			// only if the belt has moved once, the sensor would check the segment 3's bicycle
			while (belt.haveMovedOnce() == temp) {
				synchronized (belt) {
					try {

						belt.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			// get the bicycle at segment3 of the belt
			Bicycle bike = belt.peek(2);

			// only bicycle is not null, tagged and has not been inspected, it
			// would be inspected
			if (bike != null && bike.isTagged() == true && bike.isInspected() == false) {

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
			// finish the sensor checking in this belt movement
			temp = belt.haveMovedOnce();

		}

		System.out.println("Sensor terminated");
	}

}

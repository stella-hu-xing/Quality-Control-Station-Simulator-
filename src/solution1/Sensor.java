package solution1;

public class Sensor extends BicycleHandlingThread {

	protected Belt belt;
	protected Robot robot;


	public Sensor(Belt belt, Robot robot) {
		super();
		this.belt = belt;
		this.robot = robot;

	}

	private boolean temp = false;

	public void run() {

		while (!isInterrupted()) {

			if (belt.haveMovedOnce() != temp) {
				Bicycle bike = belt.peek(2);

				if (bike != null && bike.isTagged() == true && bike.isInspected() == false
						&& robot.isRobotOccupied() == false) {
					
					System.out.println(belt.indentation + belt.indentation + belt.indentation
							+ "sensor find a tagged bike " + bike);

					robot.setRobotOccupied();
					// belt.canMove = true;
				} else if (bike != null && bike.isTagged() == true && bike.isInspected() == false
						&& robot.isRobotOccupied() == true) {
					// belt.canMove = false;
					belt.setBeltStop();
					
				}
				temp = belt.haveMovedOnce();
			}

		}

		System.out.println("Sensor terminated");
	}

}

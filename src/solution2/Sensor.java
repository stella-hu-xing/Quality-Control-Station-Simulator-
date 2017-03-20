package solution2;

public class Sensor extends BicycleHandlingThread {

	protected Belt belt;
	protected Robot robot;

	public Sensor(Belt belt, Robot robot) {
		super();
		this.belt = belt;
		this.robot = robot;

	}

	public void run() {

		while (!isInterrupted()) {

			Bicycle bike = belt.peek(2);

			if (bike != null && bike.isTagged() == true && robot.isRobotOccupied() == false) {

				System.out.println();
				System.out.println(
						belt.indentation + belt.indentation + belt.indentation + "sensor find a tagged bike " + bike);

				robot.setRobotOccupied();
			} else if (bike != null && bike.isTagged() == true && robot.isRobotOccupied() == true) {
				belt.setBeltStop();
			}
		}

		System.out.println("Sensor terminated");
	}

}

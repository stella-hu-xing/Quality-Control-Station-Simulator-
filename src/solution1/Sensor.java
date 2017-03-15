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

			if (belt.hasMoved != temp) {
				Bicycle bike = belt.peek(2);




				if (bike != null && bike.isTagged() == true && bike.hasInspected == false
						&& robot.robotAction == false) {
					System.out.println("sensor find bike " + bike);

					robot.robotAction = true;
				}
				temp = belt.hasMoved;
			}

		}

		System.out.println("Sensor terminated");
	}

}

package solution1;

public class Robot extends BicycleHandlingThread {

	protected boolean isRobotOccupied = false;

	protected Belt belt;

	protected Inspector inspector;

	public Robot(Belt belt, Inspector inspector) {
		this.belt = belt;
		this.inspector = inspector;
	}

	public void run() {
		while (!isInterrupted()) {
			if (isRobotOccupied == true && inspector.isInspectorAvailable() == true) {
				try {
					// sleep(Params.ROBOT_MOVE_TIME);
					transferBetweenInspectorAndBelt();
					// isWorking = false;
					setRobotAvailable();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					this.interrupt();
				}

			}

		}
	}

	protected synchronized void transferBetweenInspectorAndBelt() throws InterruptedException {

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

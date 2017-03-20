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

		Bicycle bike = belt.removeBicycle();

		if (bike != null) {
			System.out.println("robot put " + bike + "  to inspector");

			// inspector.isInspectorAvailable() = false;
			inspector.setInspectorOccupied();
			bike = inspector.inspect(bike);
			Thread.sleep(Params.INSPECT_TIME);

			belt.put(bike, 2);

			System.out.println("robot put " + bike + "  back to belt");
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

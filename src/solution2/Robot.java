package solution2;

public class Robot extends BicycleHandlingThread {

	protected volatile boolean isRobotOccupied = false;

	protected Belt belt;

	protected ShortBelt shortBelt;

	protected Inspector inspector;

	public Robot(Belt belt, Inspector inspector, ShortBelt shortBelt) {
		this.belt = belt;
		this.inspector = inspector;
		this.shortBelt = shortBelt;
	}

	public void run() {
		while (!isInterrupted()) {
			if (isRobotOccupied == true && inspector.isInspectorAvailable() == true) {
				try {
					// sleep(Params.ROBOT_MOVE_TIME);
					transferBetweenInspectorAndBelt();
					setRobotAvailable();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					this.interrupt();
				} catch (DefKnownException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	}

	protected synchronized void transferBetweenInspectorAndBelt() throws InterruptedException, DefKnownException {
		System.out.println();
		System.out.println(belt.indentation + belt.indentation + belt.indentation
				+ "robot is ready to take tagged bicycle from main belt");

		Bicycle bike = belt.removeBicycle();

		if (bike != null) {
			sleep(Params.ROBOT_MOVE_TIME);

			System.out.println();
			System.out.println(belt.indentation + belt.indentation + belt.indentation + "robot get the tagged " + bike
					+ " and put it to inspector");

			inspector.setInspectorOccupied();

			bike = inspector.inspect(bike);

			System.out.println();
			System.out.println(belt.indentation + belt.indentation + belt.indentation + "robot is putting  " + bike
					+ "  to the short belt");

			sleep(Params.ROBOT_MOVE_TIME);
			shortBelt.put(bike, 0);

			synchronized (belt) {
				belt.setBeltCanMove();

				System.out.println();
				System.out.println(belt.indentation + "the main belt now can move due to the avaiable robot");

				belt.notifyAll();

			}
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

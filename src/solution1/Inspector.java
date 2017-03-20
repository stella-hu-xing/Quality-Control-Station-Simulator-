package solution1;

public class Inspector extends BicycleHandlingThread {

	protected boolean isInspectorAvailable = true;

	public void run() {

		while (!isInterrupted()) {

		}

		System.out.println("Inspector terminated");

	}

	protected synchronized Bicycle inspect(Bicycle bike) throws InterruptedException {

		if (bike.tagged == true && bike.defective != true) {
			bike.setNotTagged();
			// bike.hasInspected = true;
			bike.setInspected();
			System.out.println("inspector checked the bike " + bike.getId());

		}
		// isAvaliable = true;
		setInspectorAvailable();
		return bike;
	}

	public boolean isInspectorAvailable() {
		return isInspectorAvailable;
	}

	public void setInspectorAvailable() {
		isInspectorAvailable = true;
	}

	public void setInspectorOccupied() {
		isInspectorAvailable = false;
	}
}

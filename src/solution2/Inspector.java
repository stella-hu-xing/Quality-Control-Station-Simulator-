package solution2;

public class Inspector extends BicycleHandlingThread {

	protected boolean isAvaliable = true;
	protected Bicycle bikeToCheck;

	public void run() {

		while (!isInterrupted()) {

		}

		System.out.println("Inspector terminated");

	}

	protected synchronized Bicycle inspect(Bicycle bike) throws InterruptedException {

		if (bike.tagged == true && bike.defective != true) {
			bike.setNotTagged();
			bike.hasInspected = true;
			System.out.println("inspector checked the bike " + bike.getId());

		}
        isAvaliable = true;
		return bike;
	}

}

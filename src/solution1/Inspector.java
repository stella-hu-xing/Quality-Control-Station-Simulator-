package solution1;

public class Inspector  {

	protected volatile boolean isInspectorAvailable = true;

	// to help format output trace
	final public static String indentation = "                  ";


	protected synchronized Bicycle inspect(Bicycle bike) throws InterruptedException {

		while(isInspectorAvailable == true){
			wait();
		}
		System.out.println(indentation + indentation + indentation + "The Inspctor is ready to check " + bike
				+ "in a inspect time");

		Thread.sleep(Params.INSPECT_TIME);

		if (bike.isTagged() == true && bike.isDefective() != true) {

			bike.setNotTagged();
			// bike.hasInspected = true;

			bike.setInspected();

			System.out.println(indentation + indentation + indentation + "inspector have checked the wrong-tagged bike "
					+ bike + " and correct");

		} else {

			System.out.println(indentation + indentation + indentation + "inspector have checked the tagged bike "
					+ bike + " and prove the tag");

		}
		// isAvaliable = true;
		setInspectorAvailable();
		notifyAll();
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

package solution2;

public class Robot extends BicycleHandlingThread {

	protected boolean isWorking = false;

	Belt belt;

	protected ShortBelt shortBelt;

	protected Inspector inspector;

	public Robot(Belt belt, Inspector inspector, ShortBelt shortBelt) {
		this.belt = belt;
		this.inspector = inspector;
		this.shortBelt = shortBelt;
	}

	public void run() {
		while (!isInterrupted()) {
			if (isWorking == true && inspector.isAvaliable == true) {
				try {
					// sleep(Params.ROBOT_MOVE_TIME);
					transferBetweenInspectorAndBelt();
					isWorking = false;
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

			inspector.isAvaliable = false;
			bike = inspector.inspect(bike);
			Thread.sleep(Params.INSPECT_TIME);
			System.out.println("Inspector has finished inspecting" + bike);
			System.out.println("robot put " + bike + "   to short belt");
			shortBelt.put(bike, 0);
			           
    		synchronized (belt){
    			belt.canMove = true;

				belt.notifyAll();
				System.out.println("notify all 了啊！！ ");
			}
		}
	}

}

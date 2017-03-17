package solution1;

public class Sensor extends BicycleHandlingThread {

	protected Belt belt;
	protected Robot robot;
    protected BeltMover mover;

	public Sensor(Belt belt, Robot robot, BeltMover mover) {
		super();
		this.belt = belt;
		this.robot = robot;
		this.mover = mover;
	}

	private boolean temp = false;

	public void run() {

		while (!isInterrupted()) {

			if (belt.hasMoved != temp) {
				Bicycle bike = belt.peek(2);


				if (bike != null && bike.isTagged() == true && bike.hasInspected == false
						&& robot.isWorking == false) {
					System.out.println("sensor find bike " + bike);

					robot.isWorking = true;
					mover.canMove = true;
				}else if(bike!=null&&bike.isTagged()==true && bike.hasInspected==false && robot.isWorking==true){
					mover.canMove = false;
					System.out.println("waiting for");
				}
				temp = belt.hasMoved;
			}

		}

		System.out.println("Sensor terminated");
	}

}

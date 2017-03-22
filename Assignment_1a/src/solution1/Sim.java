package solution1;

/**
 * The driver of the simulation
 */

public class Sim {
	/**
	 * Create all components and start all of the threads.
	 */
	public static void main(String[] args) {

		Belt belt = new Belt();
		Inspector inspector = new Inspector();

		Producer producer = new Producer(belt);
		Consumer consumer = new Consumer(belt);
		BeltMover mover = new BeltMover(belt);

		Robot robot = new Robot(belt, inspector);
		Sensor sensor = new Sensor(belt, robot);

		// start all threads
		consumer.start();
		producer.start();
		mover.start();
		sensor.start();
		robot.start();

		while (consumer.isAlive() && producer.isAlive() && mover.isAlive() && sensor.isAlive())
			try {
				Thread.sleep(50);

			} catch (InterruptedException e) {
				BicycleHandlingThread.terminate(e);
			}

		// interrupt other threads
		consumer.interrupt();
		producer.interrupt();
		mover.interrupt();
		sensor.interrupt();

		System.out.println("Sim terminating");
		System.out.println(BicycleHandlingThread.getTerminateException());
		System.exit(0);
	}
}

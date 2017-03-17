package solution2;
/**
 * The driver of the simulation 
 */

public class Sim {
    /**
     * Create all components and start all of the threads.
     */
    public static void main(String[] args) {
        
        Belt belt = new Belt();
        ShortBelt shortBelt = new ShortBelt();
        Producer producer = new Producer(belt);
        Consumer consumer = new Consumer(belt,shortBelt);
        BeltMover mover1 = new BeltMover(belt);
        BeltMover mover2 = new BeltMover(shortBelt);
        
       
        Inspector inspector = new Inspector();
        Robot robot = new Robot(belt,inspector,shortBelt);
        Sensor sensor = new Sensor(belt,robot);
        
        consumer.start();
        producer.start();
        mover1.start();
        mover2.start();
        sensor.start();
        robot.start();
        inspector.start();

        while (consumer.isAlive() && 
               producer.isAlive() && 
               mover1.isAlive()&&mover2.isAlive())
            try {
                Thread.sleep(50);
           
            } catch (InterruptedException e) {
                BicycleHandlingThread.terminate(e);
            }

        // interrupt other threads
        consumer.interrupt();
        producer.interrupt();
        mover1.interrupt();
        mover2.interrupt();
        sensor.interrupt();
        inspector.interrupt();
        robot.interrupt();

        System.out.println("Sim terminating");
        System.out.println(BicycleHandlingThread.getTerminateException());
        System.exit(0);
    }
}

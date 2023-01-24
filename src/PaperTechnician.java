import java.util.Random;
public class PaperTechnician extends Thread {

    private ServicePrinter printer;
    public PaperTechnician(ThreadGroup threadGroup, ServicePrinter printer, String name) {
        super(threadGroup, name);
        this.printer = printer;
    }

    @Override
    public void run() {
        Random random = new Random();
        int COUNT_OF_REFILLS = 3;

        for (int i = 1; i <= COUNT_OF_REFILLS; i++) {
            printer.refillPaper();

            int MIN_SLEEP_TIME = 1000;
            int MAX_SLEEP_TIME = 5000;
            int sleepingTime = MIN_SLEEP_TIME + random.nextInt(MAX_SLEEP_TIME - MIN_SLEEP_TIME);

            try {
                Thread.sleep(sleepingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.printf("During sleeping, The Paper technician %s was disturbed " + "refilling paper no. %d.\n", sleepingTime, i);
            }
        }

        System.out.printf("The Paper Technician %s  ended up & trying to refill the printer with paper packs\n", getName());
    }
}

import java.util.Random;

public class TonerTechnician extends Thread {


    private ServicePrinter printer;

    public TonerTechnician(ThreadGroup threadGroup, ServicePrinter printer, String name) {
        super(threadGroup, name);
        this.printer = printer;
    }

    @Override
    public void run() {
        Random random = new Random();
        int COUNT_OF_REFILLS = 3;

        for (int i = 1; i <= COUNT_OF_REFILLS; i++) {
            printer.replaceTonerCartridge();

            int MIN_SLEEP_TIME = 1000;
            int MAX_SLEEP_TIME = 5000;
            int sleepingTime = MIN_SLEEP_TIME + random.nextInt(MAX_SLEEP_TIME - MIN_SLEEP_TIME);
            try {
                Thread.sleep(sleepingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.printf("During sleeping, the Toner Technician %s was disturbed " + "refilling toner cartridge no. %d.\n", sleepingTime, i);
            }
        }

        System.out.printf(" The Toner Technician %s ended up & trying to refill the toner cartridges.\n", getName());
    }
}

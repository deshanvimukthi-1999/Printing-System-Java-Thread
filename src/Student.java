import java.util.Random;
public class Student extends Thread {
    private Printer printer;

    public Student(ThreadGroup threadGroup, Printer printer, String name) {
        super(threadGroup, name);
        this.printer = printer;
    }

    @Override
    public void run() {
        Random random = new Random();
        int document_Count_Per_Student = 5;

        for (int i = 1; i <= document_Count_Per_Student; i++) {

            int MINIMUM_PAGE_COUNT_FOR_EACH_DOCUMENT = 1;
            int MAXIMUM_PAGE_COUNT_FOR_EACH_DOCUMENT = 10;

            int pageCount = MINIMUM_PAGE_COUNT_FOR_EACH_DOCUMENT +
                    random.nextInt(MAXIMUM_PAGE_COUNT_FOR_EACH_DOCUMENT - MINIMUM_PAGE_COUNT_FOR_EACH_DOCUMENT);
            String documentName = "CWK" + i;
            Document document = new Document(this.getName(), documentName, pageCount);
            printer.printDocument(document);

            boolean lastDocument = i == document_Count_Per_Student;
            if (!lastDocument) {
                int MIN_SLEEP_TIME = 1000;
                int MAX_SLEEP_TIME = 5000;
                int sleepingTime = MIN_SLEEP_TIME + random.nextInt(MAX_SLEEP_TIME - MIN_SLEEP_TIME);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.printf("%s was disturbed during the sleep after printing \'%s\' document.\n", sleepingTime, documentName);
                }
            }
        }

        System.out.printf("The student %s has completed printing documents.\n", this.getName());
    }
}

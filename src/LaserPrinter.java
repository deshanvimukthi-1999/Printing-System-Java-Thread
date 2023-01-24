public class LaserPrinter implements ServicePrinter {

    private String id;
    private int currentStateOfPaper;
    private int currentStateOfToner;
    private int amountOfPrintedDocuments;
    private ThreadGroup students;

    public LaserPrinter(String id, int initialPaperLevel, int initialTonerLevel, ThreadGroup students) {
        this.id = id;
        this.currentStateOfPaper = initialPaperLevel;
        this.currentStateOfToner = initialTonerLevel;
        this.students = students;
        this.amountOfPrintedDocuments = 0;
    }
    @Override
    public synchronized void replaceTonerCartridge() {
        boolean unableToReplaceToner = currentStateOfToner >= MINIMUM_TONER_LEVEL;
        while (unableToReplaceToner) {
            System.out.printf("| Technician | : Inspecting the toner... " +
                    "Right now, toner does not have to be replaced. Toner Level Currently is " + currentStateOfPaper + "\n");
            try {
                wait(5000);
                if(printingCompletedByStudents()) {
                    return;
                }
                unableToReplaceToner = currentStateOfToner >= MINIMUM_TONER_LEVEL;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("| Technician | :Inspecting the toner...  " +
                "Low toner level, The toner level is " + currentStateOfPaper + "Changing the toner cartridge...");
        currentStateOfToner += PAGES_PER_TONER_CARTRIDGE;
        System.out.printf("Toner cartridge replaced successfully. Toner Level is " + currentStateOfPaper + "\n" );

        notifyAll();
    }
    private boolean printingCompletedByStudents() {
        return students.activeCount() < 1;
    }

    @Override
    public synchronized void refillPaper() {
        boolean unableToRefillPrinter = (currentStateOfPaper + SHEETS_PER_PACK) > FULL_PAPER_TRAY;
        while (unableToRefillPrinter) {
            System.out.printf("| Technician | : Inspecting the paper..."  +
                    "At this moment, the Paper Tray cannot be refilled.Maximum paper level exceeded. " +
                    "Paper level at this time is " + currentStateOfPaper + "and Maximum level of paper is" + FULL_PAPER_TRAY + "\n");
            try {
                wait(5000);
                if(printingCompletedByStudents()) {
                    return;
                }
                unableToRefillPrinter = (currentStateOfPaper + SHEETS_PER_PACK) > FULL_PAPER_TRAY;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.print("| Technician | : Inspecting the paper & refilling the printer. ");
        currentStateOfPaper += SHEETS_PER_PACK;
        System.out.printf("Paper pack refilled in tray. Paper Level is:" + currentStateOfPaper + "\n");

        notifyAll();
    }

    @Override
    public synchronized void printDocument(Document document) {
        
        boolean inadequatePaperLevel = document.getNumberOfPages()> currentStateOfPaper;
        boolean inadequateTonerLevel = document.getNumberOfPages() > currentStateOfToner;

        while (inadequatePaperLevel || inadequateTonerLevel) {
            // User cannot print
            if(inadequatePaperLevel && inadequateTonerLevel) {
                System.out.printf(document.getUserID() + " " + document.getDocumentName() + " " + document.getNumberOfPages() + " " +
                        "No more paper or toner. Level of Current Paper is " + " " + currentStateOfPaper + " " + "and the toner level is "+ " " + currentStateOfToner + " " + "\n" );
            }
            else if(inadequatePaperLevel) {
                System.out.printf(document.getUserID() + " " + document.getDocumentName() + " " + document.getNumberOfPages() + " " +
                        "No more paper or toner. Level of Current Paper is " + " " + currentStateOfPaper + " " + "and the toner level is "+ " " + currentStateOfToner + " " + "\n");
            }
            else {
                System.out.printf(document.getUserID() + " " + document.getDocumentName() + " " + document.getNumberOfPages() + " " +
                        "No more toner. Level of Current Toner is " + " " + currentStateOfToner + "\n");
            }

            try {
                wait();
                inadequatePaperLevel = document.getNumberOfPages() > currentStateOfPaper;
                inadequateTonerLevel = document.getNumberOfPages()> currentStateOfToner;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf( document.getUserID() + " " + document.getDocumentName() + " " + document.getNumberOfPages() + " " +
                "Document printing with page length:" + " " + document.getNumberOfPages() + "\n");
        currentStateOfPaper -= document.getNumberOfPages();
        currentStateOfToner -= document.getNumberOfPages();
        amountOfPrintedDocuments++;
        System.out.printf( document.getUserID() + " " + document.getDocumentName() + " " + document.getNumberOfPages() + " " +
                "The document was printed successfully. Paper Level is" + " " + currentStateOfPaper + " " + "and Toner Level is "+ " " + currentStateOfToner + "\n");
    }
    @Override
    public synchronized String toString() {
        return "Laser Printer{ " +
                "Printer ID: '" + id + '\'' + " | " +
                "Paper Level: " + currentStateOfPaper + " | " +
                "Toner Level: " + currentStateOfToner + " | " +
                "Documents Printed: " + amountOfPrintedDocuments +
                '}';
    }

}

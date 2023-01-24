public class PrintingSystem {

    public static void main(String[] args) throws InterruptedException {

        ThreadGroup students = new ThreadGroup("students");
        ThreadGroup technicians = new ThreadGroup("technicians");

        ServicePrinter printer = new LaserPrinter("CANON Printer", 10, 50, students);

        Thread student1 = new Student(students, printer, "Student1");
        Thread student2 = new Student(students, printer, "Student2");
        Thread student3 = new Student(students, printer, "Student3");
        Thread student4 = new Student(students, printer, "Student4");

        Thread paperTechnician = new PaperTechnician(technicians, printer, "PaperTechnician");
        Thread tonerTechnician = new TonerTechnician(technicians, printer, "TonerTechnician");

        student1.start();
        student2.start();
        student3.start();
        student4.start();
        paperTechnician.start();
        tonerTechnician.start();

        student1.join();
        student2.join();
        student3.join();
        student4.join();
        paperTechnician.join();
        tonerTechnician.join();

        System.out.println("Completed every task. checking the state of the printer...");

        System.out.println(" ");
        System.out.print("Summary of PRINTER\n");
        System.out.println(printer.toString());
    }

}

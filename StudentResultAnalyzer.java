import java.io.*;
import java.util.*;

public class StudentResultAnalyzer {
    static final String FILE_NAME = "results.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n=== Student Result Analyzer ===");
            System.out.println("1. Add Result");
            System.out.println("2. Show All");
            System.out.println("3. Show Topper");
            System.out.println("4. Search by Roll No");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            try {
                switch (choice) {
                    case 1: addStudent(sc);
                    break;
                    case 2: showAllStudents();
                    break;
                    case 3: showTopper();
                    break;
                    case 4: searchStudent(sc);
                    break;
                    case 5: System.out.println("Exiting...");
                    break;
                    default: System.out.println("Invalid choice!");
                }
            } catch (IOException e) {
                System.out.println("I/O Error: " + e.getMessage());
            }
        } while (choice != 5);
        sc.close();
    }

    static void addStudent(Scanner sc) throws IOException {
        sc.nextLine(); // consume newline
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();
        int[] marks = new int[5];
        System.out.println("Enter marks for 5 subjects:");
        for (int i = 0; i < 5; i++) {
            System.out.print(" Subject " + (i + 1) + ": ");
            marks[i] = sc.nextInt();
        }

        Student s = new Student(name, roll, marks);
        // Ensure file exists (creates if missing)
        File file = new File(FILE_NAME);
        if (!file.exists()) file.createNewFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(s.toFileFormat());
            writer.newLine();
        }
        System.out.println("Result added successfully!");
    }

    static List<Student> loadStudents() throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists() || file.length() == 0) {
            // no file or empty file → return empty list
            return new ArrayList<>();
        }

        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                students.add(Student.fromFileFormat(line));
            }
        }
        return students;
    }

    static void showAllStudents() throws IOException {
        List<Student> students = loadStudents();
        if (students.isEmpty()) {
            System.out.println("No records found. Please add a student first.");
            return;
        }
        for (Student s : students) {
            System.out.println("\n---------------------");
            s.display();
        }
    }

    static void showTopper() throws IOException {
        List<Student> students = loadStudents();
        if (students.isEmpty()) {
            System.out.println("No records found. Please add a student first.");
            return;
        }
        Student topper = students.get(0);
        for (Student s : students) {
            if (s.average > topper.average) topper = s;
        }
        System.out.println("Topper Details:");
        topper.display();
    }

    static void searchStudent(Scanner sc) throws IOException {
        List<Student> students = loadStudents();
        if (students.isEmpty()) {
            System.out.println("No records found. Please add a student first.");
            return;
        }
        System.out.print("Enter roll number to search: ");
        int roll = sc.nextInt();
        for (Student s : students) {
            if (s.roll == roll) {
                System.out.println("Student Found:");
                s.display();
                return;
            }
        }
        System.out.println("Student not found.");
    }
}

import java.io.*;
import java.util.*;

// Main class to analyze and manage student results
public class StudentResultAnalyzer {
    static final String FILE_NAME = "results.txt"; // File to store student data
    static String[] subjectNames; // Store subject names globally

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Prompt for number of subjects and their names at the start
        System.out.print("Enter number of subjects: ");
        int numSubjects = sc.nextInt();
        sc.nextLine(); // consume newline
        subjectNames = new String[numSubjects];
        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter name for subject " + (i + 1) + ": ");
            subjectNames[i] = sc.nextLine();
        }

        int choice;
        // Display menu and loop until user chooses to exit
        do {
            System.out.println("\n=== Student Result Analyzer ===");
            System.out.println("1. Add Result");
            System.out.println("2. Show All");
            System.out.println("3. Show Topper");
            System.out.println("4. Search by Roll No");
            System.out.println("5. Delete by Roll number");
            System.out.println("6. Remove duplicates");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            // Use switch-case to handle user options
            try {
                switch (choice) {
                    case 1:
                        addStudent(sc);  // Add new student result
                        break;
                    case 2:
                        showAllStudents();  // Display all student records
                        break;
                    case 3:
                        showTopper();  // Find and display student with highest average
                        break;
                    case 4:
                        searchStudent(sc);  // Search student by roll number
                        break;
                    case 5:
                        deleteStudent(sc);
                        break;
                    case 6:
                        removeDuplicates();
                        break;
                    case 7:
                        System.out.println("Exiting...");  // Exit program
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (IOException e) {
                System.out.println("I/O Error: " + e.getMessage());
            }
        } while (choice != 7); // Continue until user chooses to exit

        sc.close(); // Close scanner to prevent memory leak
    }

    // Adds a student's data and writes it to a file
    static void addStudent(Scanner sc) throws IOException {
        sc.nextLine(); // Consume newline character
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();

        // Read marks for each subject
        int[] marks = new int[subjectNames.length];
        System.out.println("Enter marks for each subject:");
        for (int i = 0; i < subjectNames.length; i++) {
            System.out.print(" " + subjectNames[i] + ": ");
            marks[i] = sc.nextInt();
        }

        Student s = new Student(name, roll, marks); // Create student object

        // Ensure the results file exists
        File file = new File(FILE_NAME);
        if (!file.exists()) file.createNewFile();

        // Append student data to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(s.toFileFormat());
            writer.newLine();
        }

        System.out.println("Result added successfully!");
    }

    // Loads student data from the file and returns a list
    static List<Student> loadStudents() throws IOException {
        File file = new File(FILE_NAME);

        // If file doesn't exist or is empty, return an empty list
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        List<Student> students = new ArrayList<>();

        // Read each line and convert it into a Student object
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                students.add(Student.fromFileFormat(line, subjectNames.length));
            }
        }

        return students;
    }

    // Displays details of all students
    static void showAllStudents() throws IOException {
        List<Student> students = loadStudents();

        if (students.isEmpty()) {
            System.out.println("No records found. Please add a student first.");
            return;
        }

        for (Student s : students) {
            System.out.println("\n---------------------");
            s.display(subjectNames); // Use Student class display method with subject names
        }
    }

    // Finds and shows the student with the highest average
    static void showTopper() throws IOException {
        List<Student> students = loadStudents();

        if (students.isEmpty()) {
            System.out.println("No records found. Please add a student first.");
            return;
        }

        Student topper = students.get(0);
        for (Student s : students) {
            if (s.average > topper.average) {
                topper = s;
            }
        }

        System.out.println("Topper Details:");
        topper.display(subjectNames);
    }

    // Searches for a student by roll number
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
                s.display(subjectNames);
                return;
            }
        }

        System.out.println("Student not found.");
    }

    static void deleteStudent(Scanner sc) throws IOException {
        List<Student> students = loadStudents();

        if (students.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        System.out.print("Enter roll number to delete: ");
        int roll = sc.nextInt();

        boolean removed = students.removeIf(s -> s.roll == roll);

        if (removed) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (Student s : students) {
                    writer.write(s.toFileFormat());
                    writer.newLine();
                }
            }
            System.out.println("Student record deleted.");
        } else {
            System.out.println("Roll number not found.");
        }
    }

    static void removeDuplicates() throws IOException {
        List<Student> students = loadStudents();
        Set<Integer> seenRolls = new HashSet<>();
        List<Student> uniqueStudents = new ArrayList<>();

        for (Student s : students) {
            if (!seenRolls.contains(s.roll)) {
                uniqueStudents.add(s);
                seenRolls.add(s.roll);
            }
        }

        // Overwrite file with unique student list
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : uniqueStudents) {
                writer.write(s.toFileFormat());
                writer.newLine();
            }
        }

        System.out.println("Duplicates (if any) removed.");
    }
}
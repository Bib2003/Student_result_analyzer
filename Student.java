// Represents a student and their academic performance
public class Student {
    // Instance variables to store student details
    String name;
    int roll;
    int[] marks;
    double average;
    char grade;

    // Constructor to initialize student details and calculate derived data
    public Student(String name, int roll, int[] marks) {
        this.name = name;
        this.roll = roll;
        this.marks = marks;
        calculateAverage(); // Calculate average based on marks
        assignGrade();      // Assign grade based on average
    }

    // Private method to calculate average marks
    private void calculateAverage() {
        int total = 0;
        for (int i = 0; i < marks.length; i++) {
            total += marks[i];  // Add each subject mark to total
        }
        this.average = total / 5.0; // Calculate average for 5 subjects
    }

    // Private method to assign a grade based on average
    private void assignGrade() {
        if (average >= 90) grade = 'A';
        else if (average >= 75) grade = 'B';
        else if (average >= 60) grade = 'C';
        else if (average >= 40) grade = 'D';
        else grade = 'F'; // Grade 'F' for failing scores
    }

    // Converts the student's data to a comma-separated string for file storage
    public String toFileFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(",").append(roll).append(",");
        for (int mark : marks) {
            sb.append(mark).append(",");
        }
        sb.append(average).append(",").append(grade);
        return sb.toString();
    }

    // Creates a Student object from a comma-separated string (reverse of toFileFormat)
    public static Student fromFileFormat(String line) {
        String[] parts = line.split(",");
        String name = parts[0];
        int roll = Integer.parseInt(parts[1]);
        int[] marks = new int[5];
        for (int i = 0; i < 5; i++) {
            marks[i] = Integer.parseInt(parts[i + 2]); // Extract marks from string
        }
        // Average and grade will be recalculated in constructor
        return new Student(name, roll, marks);
    }

    // Prints the student's information to the console
    public void display() {
        System.out.println("Name: " + name);
        System.out.println("Roll: " + roll);
        System.out.println("Marks: ");
        for (int i = 0; i < marks.length; i++) {
            System.out.println(" Subject " + (i + 1) + ": " + marks[i]);
        }
        System.out.println("Average: " + average);
        System.out.println("Grade: " + grade);
    }
}

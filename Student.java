// Represents a student and their academic performance
public class Student {
    String name;
    int roll;
    int[] marks;
    double average;
    String grade;  // Use String instead of char for grades like A+

    // Constructor
    public Student(String name, int roll, int[] marks) {
        this.name = name;
        this.roll = roll;
        this.marks = marks;
        calculateAverage();
        assignGrade();
    }

    // Calculate average marks
    private void calculateAverage() {
        int total = 0;
        for (int mark : marks) {
            total += mark;
        }
        this.average = total / (double) marks.length;

    }

    // Assign grade (realistic)
    private void assignGrade() {
        if (average >= 90) {
            grade = "A+";
        } else if (average >= 80) {
            grade = "A";
        } else if (average >= 70) {
            grade = "B+";
        } else if (average >= 60) {
            grade = "B";
        } else if (average >= 50) {
            grade = "C+";
        } else if (average >= 40) {
            grade = "C";
        } else {
            grade = "F";
        }
    }

    // Converts data to file storage format
    public String toFileFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(",").append(roll).append(",");
        for (int mark : marks) {
            sb.append(mark).append(",");
        }
        sb.append(average).append(",").append(grade);
        return sb.toString();
    }

    // Create object from file
   public static Student fromFileFormat(String line, int subjectCount) {
    String[] parts = line.split(",");
    String name = parts[0];
    int roll = Integer.parseInt(parts[1]);
    int[] marks = new int[subjectCount];
    for (int i = 0; i < subjectCount; i++) {
        marks[i] = Integer.parseInt(parts[i + 2]);
    }
    return new Student(name, roll, marks);
}
    
    // Display student info with subject names
    public void display(String[] subjectNames) {
        System.out.println("Name: " + name);
        System.out.println("Roll: " + roll);
        System.out.println("Marks: ");
        for (int i = 0; i < marks.length; i++) {
            String subject = (subjectNames != null && i < subjectNames.length) ? subjectNames[i] : "Subject " + (i + 1);
            System.out.println(" " + subject + ": " + marks[i]);
        }
        System.out.println("Average: " + average);
        System.out.println("Grade: " + grade);
    }
    
}

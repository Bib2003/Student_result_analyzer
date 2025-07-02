public class Student {
    String name;
    int roll;
    int[] marks;
    double average;
    char grade;

    public Student(String name, int roll, int[] marks) {
        this.name = name;
        this.roll = roll;
        this.marks = marks;
        calculateAverage();
        assignGrade();
    }


    private void calculateAverage() {
        int total = 0;
        for (int i = 0; i < marks.length; i++) {
            total += marks[i];  // same as total = total + marks[i];
        }
        this.average = total / 5.0;
    }

    private void assignGrade() {
        if (average >= 90) grade = 'A';
        else if (average >= 75) grade = 'B';
        else if (average >= 60) grade = 'C';
        else if (average >= 40) grade = 'D';
        else grade = 'F';

    }

    public String toFileFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(",").append(roll).append(",");
        for (int mark : marks) {
            sb.append(mark).append(",");
        }
        sb.append(average).append(",").append(grade);
        return sb.toString();
    }

    public static Student fromFileFormat(String line) {
        String[] parts = line.split(",");
        String name = parts[0];
        int roll = Integer.parseInt(parts[1]);
        int[] marks = new int[5];
        for (int i = 0; i < 5; i++) {
            marks[i] = Integer.parseInt(parts[i + 2]);
        }
        // Don't parse average or grade from file — just reconstruct them in constructor
        return new Student(name, roll, marks);
    }


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


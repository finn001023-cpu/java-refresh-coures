import java.util.*;
import java.util.stream.Collectors;

// Interface
interface Gradable {
    double calculateGPA();
    String getGrade();
}

// Abstract class
abstract class Person {
    protected String name;
    protected int age;
    protected String email;
    
    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
    
    // Abstract method
    public abstract void displayInfo();
    
    // Concrete method
    public void sendEmail(String message) {
        System.out.println("Sending email to " + email + ": " + message);
    }
    
    // Getters and setters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
}

// Student class implementing interface and extending abstract class
public class StudentManager {
    
    public static class Student extends Person implements Gradable, Comparable<Student> {
        private String studentId;
        private Map<String, Double> courses; // course -> grade
        private String major;
        
        public Student(String name, int age, String email, String studentId, String major) {
            super(name, age, email);
            this.studentId = studentId;
            this.major = major;
            this.courses = new HashMap<>();
        }
        
        public void addCourse(String courseName, double grade) {
            courses.put(courseName, grade);
            System.out.println(name + " enrolled in " + courseName + " with grade: " + grade);
        }
        
        @Override
        public double calculateGPA() {
            if (courses.isEmpty()) return 0.0;
            
            double sum = courses.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();
            return sum / courses.size();
        }
        
        @Override
        public String getGrade() {
            double gpa = calculateGPA();
            if (gpa >= 3.5) return "A";
            else if (gpa >= 3.0) return "B";
            else if (gpa >= 2.0) return "C";
            else return "F";
        }
        
        @Override
        public void displayInfo() {
            System.out.println("\nStudent Information:");
            System.out.println("ID: " + studentId);
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Major: " + major);
            System.out.println("GPA: " + String.format("%.2f", calculateGPA()));
            System.out.println("Grade: " + getGrade());
            System.out.println("Courses: " + courses);
        }
        
        @Override
        public int compareTo(Student other) {
            return Double.compare(other.calculateGPA(), this.calculateGPA()); // Descending by GPA
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Student student = (Student) obj;
            return Objects.equals(studentId, student.studentId);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(studentId);
        }
        
        @Override
        public String toString() {
            return String.format("Student[%s: %s - GPA: %.2f]", studentId, name, calculateGPA());
        }
    }
    
    public static void demonstrateCollections() {
        System.out.println("\n=== Collections Demo ===");
        
        // List
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("Alice", 20, "alice@edu.com", "S001", "Computer Science"));
        studentList.add(new Student("Bob", 22, "bob@edu.com", "S002", "Mathematics"));
        studentList.add(new Student("Charlie", 21, "charlie@edu.com", "S003", "Physics"));
        
        // Add courses and grades
        studentList.get(0).addCourse("Java Programming", 3.8);
        studentList.get(0).addCourse("Data Structures", 3.6);
        studentList.get(1).addCourse("Calculus", 3.2);
        studentList.get(1).addCourse("Linear Algebra", 3.4);
        studentList.get(2).addCourse("Quantum Physics", 3.9);
        studentList.get(2).addCourse("Thermodynamics", 3.7);
        
        // Sort students by GPA (descending)
        Collections.sort(studentList);
        
        System.out.println("\nStudents sorted by GPA (descending):");
        for (Student student : studentList) {
            student.displayInfo();
        }
        
        // Map demonstration
        Map<String, Student> studentMap = studentList.stream()
            .collect(Collectors.toMap(s -> s.studentId, s -> s));
        
        System.out.println("\nStudent Map:");
        for (Map.Entry<String, Student> entry : studentMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue().getName());
        }
        
        // Set demonstration
        Set<Student> studentSet = new HashSet<>(studentList);
        System.out.println("\nStudent Set size: " + studentSet.size());
        
        // Stream operations
        System.out.println("\n=== Stream Operations ===");
        double averageGPA = studentList.stream()
            .mapToDouble(Student::calculateGPA)
            .average()
            .orElse(0.0);
        System.out.println("Average GPA: " + String.format("%.2f", averageGPA));
        
        List<Student> topStudents = studentList.stream()
            .filter(s -> s.calculateGPA() > 3.5)
            .collect(Collectors.toList());
        System.out.println("Top students (GPA > 3.5): " + topStudents.size());
    }
    
    public static void main(String[] args) {
        System.out.println("=== Student Manager Demo ===");
        
        // Create and demonstrate students
        Student student1 = new Student("John Doe", 20, "john@university.edu", "S1001", "Computer Science");
        student1.addCourse("Algorithms", 3.7);
        student1.addCourse("Database Systems", 3.9);
        student1.displayInfo();
        student1.sendEmail("Your academic performance is excellent!");
        
        // Demonstrate collections and advanced features
        demonstrateCollections();
        
        // Exception handling demo
        try {
            Student nullStudent = null;
            nullStudent.displayInfo(); // This will throw NullPointerException
        } catch (NullPointerException e) {
            System.out.println("\nCaught NullPointerException: " + e.getMessage());
        } finally {
            System.out.println("Exception handling demonstration completed.");
        }
    }
}

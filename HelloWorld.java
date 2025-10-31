public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("=== Java Refresh Course ===");
        
        // Basic variable types
        int age = 25;
        double salary = 50000.75;
        char grade = 'A';
        boolean isJavaFun = true;
        String name = "John Doe";
        
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Salary: $" + salary);
        System.out.println("Grade: " + grade);
        System.out.println("Is Java Fun? " + isJavaFun);
        
        // Method calls
        demonstrateOperators();
        demonstrateConditionals();
        demonstrateLoops();
    }
    
    public static void demonstrateOperators() {
        System.out.println("\n--- Operators ---");
        int a = 15, b = 4;
        
        System.out.println("a + b = " + (a + b));
        System.out.println("a - b = " + (a - b));
        System.out.println("a * b = " + (a * b));
        System.out.println("a / b = " + (a / b));
        System.out.println("a % b = " + (a % b));
        
        // Comparison operators
        System.out.println("a == b: " + (a == b));
        System.out.println("a != b: " + (a != b));
        System.out.println("a > b: " + (a > b));
        
        // Logical operators
        boolean x = true, y = false;
        System.out.println("x && y: " + (x && y));
        System.out.println("x || y: " + (x || y));
        System.out.println("!x: " + (!x));
    }
    
    public static void demonstrateConditionals() {
        System.out.println("\n--- Conditionals ---");
        int score = 85;
        
        // If-else if-else
        if (score >= 90) {
            System.out.println("Grade: A");
        } else if (score >= 80) {
            System.out.println("Grade: B");
        } else if (score >= 70) {
            System.out.println("Grade: C");
        } else {
            System.out.println("Grade: F");
        }
        
        // Switch statement
        String day = "Monday";
        switch (day.toLowerCase()) {
            case "monday":
                System.out.println("Start of work week");
                break;
            case "friday":
                System.out.println("Weekend is near!");
                break;
            default:
                System.out.println("Regular day");
        }
    }
    
    public static void demonstrateLoops() {
        System.out.println("\n--- Loops ---");
        
        // For loop
        System.out.print("For loop: ");
        for (int i = 1; i <= 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        
        // While loop
        System.out.print("While loop: ");
        int count = 5;
        while (count > 0) {
            System.out.print(count + " ");
            count--;
        }
        System.out.println();
        
        // Do-while loop
        System.out.print("Do-while: ");
        int num = 1;
        do {
            System.out.print(num + " ");
            num++;
        } while (num <= 3);
        System.out.println();
        
        // For-each loop with array
        System.out.print("For-each: ");
        int[] numbers = {10, 20, 30, 40, 50};
        for (int number : numbers) {
            System.out.print(number + " ");
        }
        System.out.println();
    }
}

markdown

# Java Refresh Course: Complete Guide for Returning Developers

## Table of Contents
1. [Introduction to Java](#introduction)
2. [Basic Syntax and Structure](#basic-syntax)
3. [Object-Oriented Programming](#oop)
4. [Collections Framework](#collections)
5. [Exception Handling](#exceptions)
6. [File I/O Operations](#file-io)
7. [Multithreading](#multithreading)
8. [Modern Java Features](#modern-features)
9. [Best Practices](#best-practices)
10. [Practical Projects](#projects)

## 1. Introduction to Java <a name="introduction"></a>

### What is Java?
- **Platform Independence**: Write Once, Run Anywhere (WORA)
- **Object-Oriented**: Everything is an object (except primitives)
- **Strongly Typed**: Compile-time type checking
- **Automatic Memory Management**: Garbage collection
- **Rich Ecosystem**: Vast library collection and community support

### Java Architecture

Source Code (.java) → Compiler → Bytecode (.class) → JVM → Machine Code
text


### Key Versions
- **Java 8** (2014): Lambdas, Streams, Optional
- **Java 11** (2018): LTS, HTTP Client, var keyword
- **Java 17** (2021): Current LTS, sealed classes, pattern matching

## 2. Basic Syntax and Structure <a name="basic-syntax"></a>

### Hello World Revisited
```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}

Data Types

Primitive Types:

    byte, short, int, long - integers

    float, double - floating points

    char - single character

    boolean - true/false

Reference Types:

    String - character sequences

    Arrays - int[], String[]

    Classes and Objects

Control Structures
java

// If-else
if (condition) {
    // code
} else if (anotherCondition) {
    // code
} else {
    // code
}

// Switch (enhanced in Java 14+)
switch (variable) {
    case value1 -> System.out.println("Value 1");
    case value2 -> System.out.println("Value 2");
    default -> System.out.println("Default");
}

// Loops
for (int i = 0; i < 10; i++) { }
while (condition) { }
do { } while (condition);
for (String item : collection) { } // for-each

3. Object-Oriented Programming <a name="oop"></a>
Four Pillars of OOP
1. Encapsulation
java

public class BankAccount {
    private double balance;  // data hiding
    
    public double getBalance() {  // controlled access
        return balance;
    }
    
    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }
}

2. Inheritance
java

class Animal {
    void makeSound() { System.out.println("Some sound"); }
}

class Dog extends Animal {
    @Override
    void makeSound() { System.out.println("Woof!"); }
}

3. Polymorphism
java

Animal myAnimal = new Dog();  // Upcasting
myAnimal.makeSound();  // Outputs "Woof!" - runtime polymorphism

4. Abstraction
java

abstract class Shape {
    abstract double area();  // abstract method
}

interface Drawable {
    void draw();  // interface method
}

Classes and Objects
java

// Constructor
public class Person {
    private String name;
    private int age;
    
    // Constructor
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Method overloading
    public void celebrateBirthday() {
        age++;
    }
    
    public void celebrateBirthday(String message) {
        age++;
        System.out.println(message);
    }
}

4. Collections Framework <a name="collections"></a>
Core Interfaces

    List - Ordered collection with duplicates

    Set - Unique elements, no duplicates

    Map - Key-value pairs

    Queue - FIFO ordering

Common Implementations
java

List<String> arrayList = new ArrayList<>();  // Fast random access
List<String> linkedList = new LinkedList<>(); // Fast insertions/deletions

Set<String> hashSet = new HashSet<>();  // Unordered, O(1) operations
Set<String> treeSet = new TreeSet<>();  // Sorted order

Map<String, Integer> hashMap = new HashMap<>();  // Key-value pairs
Map<String, Integer> treeMap = new TreeMap<>();  // Sorted keys

Stream API (Java 8+)
java

List<String> result = list.stream()
    .filter(s -> s.startsWith("A"))
    .map(String::toUpperCase)
    .sorted()
    .collect(Collectors.toList());

5. Exception Handling <a name="exceptions"></a>
Exception Hierarchy
text

Throwable
    ├── Error (unchecked)
    └── Exception
        ├── RuntimeException (unchecked)
        └── IOException (checked)

Try-Catch-Finally
java

try {
    // risky code
    FileReader file = new FileReader("file.txt");
} catch (FileNotFoundException e) {
    // handle specific exception
    System.err.println("File not found: " + e.getMessage());
} catch (IOException e) {
    // handle other IO exceptions
    System.err.println("IO Error: " + e.getMessage());
} finally {
    // always executes (cleanup code)
    System.out.println("Cleanup completed");
}

Try-with-Resources (Java 7+)
java

try (FileReader reader = new FileReader("file.txt");
     BufferedReader br = new BufferedReader(reader)) {
    // auto-close resources
    String line = br.readLine();
}

6. File I/O Operations <a name="file-io"></a>
Traditional I/O
java

// Reading
BufferedReader reader = new BufferedReader(new FileReader("file.txt"));
String line = reader.readLine();

// Writing
BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
writer.write("Hello World");

NIO.2 (Java 7+)
java

// Modern file operations
Path path = Paths.get("file.txt");
List<String> lines = Files.readAllLines(path);
Files.write(path, lines, StandardOpenOption.APPEND);

7. Multithreading <a name="multithreading"></a>
Creating Threads
java

// Method 1: Extend Thread class
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread running");
    }
}

// Method 2: Implement Runnable
class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Runnable running");
    }
}

// Usage
new MyThread().start();
new Thread(new MyRunnable()).start();

Executor Framework
java

ExecutorService executor = Executors.newFixedThreadPool(5);
executor.submit(() -> {
    // task code
});
executor.shutdown();

8. Modern Java Features <a name="modern-features"></a>
Lambda Expressions
java

// Instead of anonymous classes
button.addActionListener(e -> System.out.println("Button clicked"));

// Functional interfaces
Function<String, Integer> stringToInt = Integer::parseInt;
Predicate<String> isEmpty = String::isEmpty;

Optional Class
java

Optional<String> optional = Optional.ofNullable(getString());
String result = optional.orElse("default");
optional.ifPresent(System.out::println);

Records (Java 16+)
java

record Person(String name, int age) { }
// Auto-generates: constructor, getters, equals, hashCode, toString

Pattern Matching (Java 16+)
java

if (obj instanceof String s) {
    System.out.println(s.length());  // s is automatically cast to String
}

9. Best Practices <a name="best-practices"></a>
Code Style

    Use meaningful variable names

    Follow Java naming conventions

    Write self-documenting code

    Keep methods small and focused

Performance

    Use StringBuilder for string concatenation in loops

    Prefer interface types for declarations

    Use enhanced for loops when possible

    Close resources properly

Security

    Validate input parameters

    Don't ignore exceptions

    Use prepared statements for SQL

    Follow principle of least privilege

10. Practical Projects <a name="projects"></a>
Included Examples:

    HelloWorld.java - Basic syntax and control structures

    BankAccount.java - OOP principles and encapsulation

    StudentManager.java - Collections, interfaces, and advanced OOP

    FileProcessor.java - File I/O and serialization

    SimpleWebServer.java - Networking and HTTP handling

How to Run:
bash

# Compile all files
javac *.java

# Run individual examples
java HelloWorld
java BankAccount
java StudentManager
java FileProcessor
java SimpleWebServer

Next Steps:

    Explore Spring Framework for enterprise applications

    Learn about database connectivity with JDBC

    Study design patterns specific to Java

    Practice with unit testing using JUnit

    Explore build tools like Maven or Gradle

This comprehensive refresh course covers everything from basic syntax to advanced features, helping you regain Java proficiency quickly and effectively.
text


This comprehensive Java refresh course provides:

1. **Complete educational structure** - From basics to advanced concepts
2. **Practical code examples** - 5 working Java files demonstrating real-world scenarios
3. **Modern Java features** - Includes streams, lambdas, NIO, and HTTP server
4. **Progressive learning** - Starts simple, builds complexity gradually
5. **Ready-to-run code** - All files can be compiled and executed immediately

The course is designed specifically for developers who haven't used Java in a while but need to quickly regain proficiency for modern development.

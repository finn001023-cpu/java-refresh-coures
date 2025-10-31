import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileProcessor {
    
    // Read file using BufferedReader (traditional way)
    public static List<String> readFileTraditional(String filePath) {
        List<String> lines = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        return lines;
    }
    
    // Read file using Java NIO (modern way)
    public static List<String> readFileModern(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    // Write to file
    public static void writeToFile(String filePath, List<String> content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : content) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Successfully wrote to file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    
    // Process file content with streams
    public static Map<String, Long> analyzeText(String filePath) {
        List<String> lines = readFileModern(filePath);
        
        Map<String, Long> wordFrequency = lines.stream()
            .flatMap(line -> Arrays.stream(line.split("\\s+")))
            .filter(word -> !word.isEmpty())
            .map(String::toLowerCase)
            .collect(Collectors.groupingBy(
                word -> word,
                Collectors.counting()
            ));
        
        return wordFrequency;
    }
    
    // File operations utility
    public static void demonstrateFileOperations() {
        System.out.println("\n=== File Operations ===");
        
        String testFilePath = "test_file.txt";
        String outputFilePath = "output_analysis.txt";
        
        // Create a test file
        List<String> sampleContent = Arrays.asList(
            "Java is a powerful programming language",
            "Java is platform independent",
            "File processing in Java is straightforward",
            "Streams make Java code more readable"
        );
        
        writeToFile(testFilePath, sampleContent);
        
        // Read and display file content
        System.out.println("\nFile Content:");
        List<String> content = readFileModern(testFilePath);
        content.forEach(System.out::println);
        
        // Analyze text
        System.out.println("\nWord Frequency Analysis:");
        Map<String, Long> wordFreq = analyzeText(testFilePath);
        wordFreq.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .forEach(entry -> 
                System.out.println(entry.getKey() + ": " + entry.getValue())
            );
        
        // Write analysis to output file
        List<String> analysisOutput = new ArrayList<>();
        analysisOutput.add("=== Text Analysis Report ===");
        analysisOutput.add("Total lines: " + content.size());
        analysisOutput.add("Total words: " + wordFreq.values().stream().mapToLong(Long::longValue).sum());
        analysisOutput.add("\nTop 5 words:");
        
        wordFreq.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .forEach(entry -> 
                analysisOutput.add(entry.getKey() + ": " + entry.getValue())
            );
        
        writeToFile(outputFilePath, analysisOutput);
        
        // Demonstrate file properties
        try {
            Path path = Paths.get(testFilePath);
            System.out.println("\nFile Properties:");
            System.out.println("File name: " + path.getFileName());
            System.out.println("File size: " + Files.size(path) + " bytes");
            System.out.println("Last modified: " + Files.getLastModifiedTime(path));
        } catch (IOException e) {
            System.err.println("Error getting file properties: " + e.getMessage());
        }
        
        // Cleanup
        try {
            Files.deleteIfExists(Paths.get(testFilePath));
            System.out.println("\nCleaned up test files");
        } catch (IOException e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }
    
    // Serialization example
    static class User implements Serializable {
        private static final long serialVersionUID = 1L;
        private String username;
        private transient String password; // transient fields are not serialized
        private Date createdDate;
        
        public User(String username, String password) {
            this.username = username;
            this.password = password;
            this.createdDate = new Date();
        }
        
        @Override
        public String toString() {
            return String.format("User{username='%s', password='%s', createdDate=%s}", 
                               username, password, createdDate);
        }
    }
    
    public static void demonstrateSerialization() {
        System.out.println("\n=== Serialization Demo ===");
        
        String serializedFile = "user_data.ser";
        
        // Serialize object
        User user = new User("john_doe", "secret123");
        try (ObjectOutputStream oos = new ObjectOutputStream(
             new FileOutputStream(serializedFile))) {
            oos.writeObject(user);
            System.out.println("Object serialized: " + user);
        } catch (IOException e) {
            System.err.println("Serialization error: " + e.getMessage());
        }
        
        // Deserialize object
        try (ObjectInputStream ois = new ObjectInputStream(
             new FileInputStream(serializedFile))) {
            User deserializedUser = (User) ois.readObject();
            System.out.println("Object deserialized: " + deserializedUser);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Deserialization error: " + e.getMessage());
        }
        
        // Cleanup
        try {
            Files.deleteIfExists(Paths.get(serializedFile));
        } catch (IOException e) {
            System.err.println("Cleanup error: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== File Processor Demo ===");
        
        demonstrateFileOperations();
        demonstrateSerialization();
        
        // Additional file operations
        System.out.println("\n=== Additional File Operations ===");
        
        // Create directory structure
        String baseDir = "test_directory";
        try {
            Files.createDirectories(Paths.get(baseDir, "subdir1", "subdir2"));
            System.out.println("Created directory structure");
            
            // List files in current directory
            System.out.println("\nFiles in current directory:");
            Files.list(Paths.get("."))
                .filter(Files::isRegularFile)
                .map(Path::getFileName)
                .forEach(System.out::println);
                
        } catch (IOException e) {
            System.err.println("Directory operation error: " + e.getMessage());
        }
    }
}

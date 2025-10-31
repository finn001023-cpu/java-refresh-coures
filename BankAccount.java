import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    // Static variable - shared across all instances
    private static int accountCounter = 1000;
    
    // Instance variables
    private final String accountNumber;
    private String accountHolder;
    private double balance;
    private List<String> transactionHistory;
    
    // Constructors
    public BankAccount(String accountHolder) {
        this.accountNumber = "ACC" + (++accountCounter);
        this.accountHolder = accountHolder;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
        addTransaction("Account created with initial balance: $" + balance);
    }
    
    public BankAccount(String accountHolder, double initialBalance) {
        this(accountHolder);
        this.balance = initialBalance;
        addTransaction("Initial deposit: $" + initialBalance);
    }
    
    // Methods
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addTransaction("Deposit: +$" + amount);
            System.out.println("Successfully deposited $" + amount);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }
    
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            addTransaction("Withdrawal: -$" + amount);
            System.out.println("Successfully withdrew $" + amount);
        } else {
            System.out.println("Insufficient funds or invalid amount");
        }
    }
    
    public void transfer(BankAccount recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            this.withdraw(amount);
            recipient.deposit(amount);
            addTransaction("Transfer to " + recipient.accountNumber + ": -$" + amount);
            System.out.println("Transfer successful to " + recipient.accountHolder);
        } else {
            System.out.println("Transfer failed: Insufficient funds");
        }
    }
    
    private void addTransaction(String transaction) {
        String timestamp = java.time.LocalDateTime.now().toString();
        transactionHistory.add(timestamp + " - " + transaction + " | Balance: $" + balance);
    }
    
    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getAccountHolder() {
        return accountHolder;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void printStatement() {
        System.out.println("\n=== Bank Statement ===");
        System.out.println("Account: " + accountNumber);
        System.out.println("Holder: " + accountHolder);
        System.out.println("Current Balance: $" + balance);
        System.out.println("\nTransaction History:");
        for (String transaction : transactionHistory) {
            System.out.println("  " + transaction);
        }
    }
    
    // Static method
    public static int getTotalAccounts() {
        return accountCounter - 1000;
    }
    
    // Override toString method
    @Override
    public String toString() {
        return String.format("BankAccount[%s: %s - $%.2f]", 
                           accountNumber, accountHolder, balance);
    }
    
    // Main method for testing
    public static void main(String[] args) {
        System.out.println("=== Bank Account Demo ===");
        
        // Create accounts
        BankAccount alice = new BankAccount("Alice Johnson", 1000);
        BankAccount bob = new BankAccount("Bob Smith", 500);
        
        // Perform transactions
        alice.deposit(200);
        alice.withdraw(150);
        alice.transfer(bob, 300);
        
        // Print statements
        alice.printStatement();
        bob.printStatement();
        
        // Static method call
        System.out.println("\nTotal accounts created: " + BankAccount.getTotalAccounts());
        
        // Demonstrate toString
        System.out.println("\nAccount details:");
        System.out.println(alice);
        System.out.println(bob);
    }
}

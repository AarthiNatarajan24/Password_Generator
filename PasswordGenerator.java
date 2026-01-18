import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PasswordGenerator {
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_=+[]{}|;:,.<>?";
    
    private static final SecureRandom random = new SecureRandom();
    private static final List<PasswordHistoryEntry> passwordHistory = new ArrayList<>();
    
    static class PasswordHistoryEntry {
        String password;
        String timestamp;
        double entropy;
        String strength;
        
        PasswordHistoryEntry(String password, double entropy, String strength) {
            this.password = password;
            this.entropy = entropy;
            this.strength = strength;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.timestamp = LocalDateTime.now().format(formatter);
        }
        
        @Override
        public String toString() {
            return String.format("[%s] %s | Entropy: %.2f bits | Strength: %s", 
                               timestamp, password, entropy, strength);
        }
    }
    
    /**
     * Generates a password with specified criteria
     * Ensures at least one character from each selected type
     */
    public static String generatePassword(int length, boolean useUppercase, 
                                         boolean useLowercase, boolean useDigits, 
                                         boolean useSpecial) {
        if (length < 1) {
            throw new IllegalArgumentException("Password length must be at least 1");
        }
        
        StringBuilder charSet = new StringBuilder();
        List<Character> guaranteedChars = new ArrayList<>();
        
        if (useUppercase) {
            charSet.append(UPPERCASE);
            guaranteedChars.add(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        }
        if (useLowercase) {
            charSet.append(LOWERCASE);
            guaranteedChars.add(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        }
        if (useDigits) {
            charSet.append(DIGITS);
            guaranteedChars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        }
        if (useSpecial) {
            charSet.append(SPECIAL);
            guaranteedChars.add(SPECIAL.charAt(random.nextInt(SPECIAL.length())));
        }
        
        if (charSet.length() == 0) {
            throw new IllegalArgumentException("At least one character type must be selected");
        }
        
        if (length < guaranteedChars.size()) {
            throw new IllegalArgumentException("Password length must be at least " + 
                                              guaranteedChars.size() + 
                                              " to include all selected character types");
        }
        
        // Fill remaining length with random characters
        StringBuilder password = new StringBuilder();
        for (int i = guaranteedChars.size(); i < length; i++) {
            int index = random.nextInt(charSet.length());
            password.append(charSet.charAt(index));
        }
        
        // Add guaranteed characters
        for (char c : guaranteedChars) {
            password.append(c);
        }
        
        // Shuffle to randomize positions
        List<Character> passwordChars = new ArrayList<>();
        for (char c : password.toString().toCharArray()) {
            passwordChars.add(c);
        }
        Collections.shuffle(passwordChars, random);
        
        StringBuilder finalPassword = new StringBuilder();
        for (char c : passwordChars) {
            finalPassword.append(c);
        }
        
        return finalPassword.toString();
    }
    
    /**
     * Calculates password entropy in bits
     * Entropy = log2(pool_size^length)
     */
    public static double calculateEntropy(int length, boolean useUppercase, 
                                         boolean useLowercase, boolean useDigits, 
                                         boolean useSpecial) {
        int poolSize = 0;
        if (useUppercase) poolSize += 26;
        if (useLowercase) poolSize += 26;
        if (useDigits) poolSize += 10;
        if (useSpecial) poolSize += SPECIAL.length();
        
        return length * (Math.log(poolSize) / Math.log(2));
    }
    
    /**
     * Calculates password strength based on entropy
     */
    public static String calculateStrength(double entropy) {
        if (entropy < 28) return "Very Weak";
        if (entropy < 36) return "Weak";
        if (entropy < 60) return "Medium";
        if (entropy < 128) return "Strong";
        return "Very Strong";
    }
    
    /**
     * Displays entropy meter visually
     */
    public static void displayEntropyMeter(double entropy) {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║     PASSWORD STRENGTH METER        ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.printf("║  Entropy: %.2f bits%n", entropy);
        
        String strength = calculateStrength(entropy);
        System.out.println("║  Strength: " + strength);
        
        // Visual meter
        int bars = Math.min((int)(entropy / 12.8), 10);
        System.out.print("║  [");
        for (int i = 0; i < 10; i++) {
            if (i < bars) {
                System.out.print("█");
            } else {
                System.out.print("░");
            }
        }
        System.out.println("]");
        
        // Entropy interpretation
        System.out.println("║");
        if (entropy < 28) {
            System.out.println("║  ⚠ Very weak - easily cracked");
        } else if (entropy < 36) {
            System.out.println("║  ⚠ Weak - vulnerable to attacks");
        } else if (entropy < 60) {
            System.out.println("║  ✓ Medium - acceptable for most uses");
        } else if (entropy < 128) {
            System.out.println("║  ✓✓ Strong - secure password");
        } else {
            System.out.println("║  ✓✓✓ Very strong - excellent security");
        }
        System.out.println("╚════════════════════════════════════╝");
    }
    
    /**
     * Saves password to file
     */
    public static void savePasswordToFile(String password, double entropy, String strength) {
        try {
            FileWriter writer = new FileWriter("passwords.txt", true);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = LocalDateTime.now().format(formatter);
            
            writer.write("========================================\n");
            writer.write("Generated: " + timestamp + "\n");
            writer.write("Password: " + password + "\n");
            writer.write("Entropy: " + String.format("%.2f", entropy) + " bits\n");
            writer.write("Strength: " + strength + "\n");
            writer.write("========================================\n\n");
            writer.close();
            
            System.out.println("\n✓ Password saved to passwords.txt");
        } catch (IOException e) {
            System.out.println("\n✗ Error saving password: " + e.getMessage());
        }
    }
    
    /**
     * Adds password to history
     */
    public static void addToHistory(String password, double entropy, String strength) {
        passwordHistory.add(new PasswordHistoryEntry(password, entropy, strength));
    }
    
    /**
     * Displays password history
     */
    public static void displayHistory() {
        if (passwordHistory.isEmpty()) {
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║   No passwords in history yet      ║");
            System.out.println("╚════════════════════════════════════╝");
            return;
        }
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                         PASSWORD HISTORY                           ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");
        
        for (int i = 0; i < passwordHistory.size(); i++) {
            System.out.println((i + 1) + ". " + passwordHistory.get(i));
        }
    }
    
    public static void displayMenu() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║    PASSWORD GENERATOR v2.0         ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println("1. Generate Password");
        System.out.println("2. Generate Multiple Passwords");
        System.out.println("3. View Password History");
        System.out.println("4. Clear History");
        System.out.println("5. Exit");
        System.out.print("\nSelect an option: ");
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    generateSinglePassword(scanner);
                    break;
                case 2:
                    generateMultiplePasswords(scanner);
                    break;
                case 3:
                    displayHistory();
                    break;
                case 4:
                    passwordHistory.clear();
                    System.out.println("\n✓ History cleared!");
                    break;
                case 5:
                    System.out.println("\nThank you for using Password Generator!");
                    running = false;
                    break;
                default:
                    System.out.println("\n✗ Invalid option. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private static void generateSinglePassword(Scanner scanner) {
        System.out.println("\n--- Generate Password ---");
        System.out.print("Enter password length (minimum 4): ");
        int length = scanner.nextInt();
        
        System.out.print("Include uppercase letters? (y/n): ");
        boolean useUppercase = scanner.next().equalsIgnoreCase("y");
        
        System.out.print("Include lowercase letters? (y/n): ");
        boolean useLowercase = scanner.next().equalsIgnoreCase("y");
        
        System.out.print("Include digits? (y/n): ");
        boolean useDigits = scanner.next().equalsIgnoreCase("y");
        
        System.out.print("Include special characters? (y/n): ");
        boolean useSpecial = scanner.next().equalsIgnoreCase("y");
        
        try {
            String password = generatePassword(length, useUppercase, useLowercase, 
                                              useDigits, useSpecial);
            double entropy = calculateEntropy(length, useUppercase, useLowercase, 
                                             useDigits, useSpecial);
            String strength = calculateStrength(entropy);
            
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("  Generated Password: " + password);
            System.out.println("╚════════════════════════════════════╝");
            
            displayEntropyMeter(entropy);
            
            // Add to history
            addToHistory(password, entropy, strength);
            
            // Ask to save to file
            System.out.print("\nSave this password to file? (y/n): ");
            if (scanner.next().equalsIgnoreCase("y")) {
                savePasswordToFile(password, entropy, strength);
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println("\n✗ Error: " + e.getMessage());
        }
    }
    
    private static void generateMultiplePasswords(Scanner scanner) {
        System.out.println("\n--- Generate Multiple Passwords ---");
        System.out.print("How many passwords to generate? ");
        int count = scanner.nextInt();
        
        System.out.print("Enter password length (minimum 4): ");
        int length = scanner.nextInt();
        
        System.out.print("Include uppercase letters? (y/n): ");
        boolean useUppercase = scanner.next().equalsIgnoreCase("y");
        
        System.out.print("Include lowercase letters? (y/n): ");
        boolean useLowercase = scanner.next().equalsIgnoreCase("y");
        
        System.out.print("Include digits? (y/n): ");
        boolean useDigits = scanner.next().equalsIgnoreCase("y");
        
        System.out.print("Include special characters? (y/n): ");
        boolean useSpecial = scanner.next().equalsIgnoreCase("y");
        
        try {
            double entropy = calculateEntropy(length, useUppercase, useLowercase, 
                                             useDigits, useSpecial);
            String strength = calculateStrength(entropy);
            
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("  Generated Passwords:");
            System.out.println("╚════════════════════════════════════╝");
            
            List<String> passwords = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                String password = generatePassword(length, useUppercase, useLowercase, 
                                                  useDigits, useSpecial);
                System.out.println(i + ". " + password);
                passwords.add(password);
                addToHistory(password, entropy, strength);
            }
            
            displayEntropyMeter(entropy);
            
            // Ask to save all to file
            System.out.print("\nSave all passwords to file? (y/n): ");
            if (scanner.next().equalsIgnoreCase("y")) {
                for (String pwd : passwords) {
                    savePasswordToFile(pwd, entropy, strength);
                }
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println("\n✗ Error: " + e.getMessage());
        }
    }
}
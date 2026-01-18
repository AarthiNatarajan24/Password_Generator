# 🔐 Password Generator v2.0

A secure, feature-rich password generator built in Java with entropy calculation, password history tracking, and file export capabilities.

## 📋 Table of Contents
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Password Strength](#password-strength)
- [File Structure](#file-structure)
- [Technical Details](#technical-details)
- [Contributing](#contributing)
- [License](#license)

## ✨ Features

### Core Functionality
- **Customizable Password Generation**: Choose length and character types
- **Multiple Password Generation**: Generate multiple passwords at once
- **Secure Randomization**: Uses `SecureRandom` for cryptographically strong random generation
- **Guaranteed Character Types**: Ensures at least one character from each selected type

### Advanced Features
- **🎯 Entropy Calculator**: Calculates password strength using entropy measurement in bits
- **📊 Visual Strength Meter**: Displays password strength with visual progress bar
- **📝 Password History**: Tracks all generated passwords with timestamps and strength ratings
- **💾 File Export**: Save passwords to `passwords.txt` with complete metadata
- **🎨 User-Friendly Interface**: Clean menu-driven interface with formatted output

## 🔧 Requirements

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code) or command line

## 📥 Installation

### Option 1: Using Command Line

1. **Download the source code**
   ```bash
   # Create project directory
   mkdir PasswordGenerator
   cd PasswordGenerator
   ```

2. **Save the code**
   - Copy `PasswordGenerator.java` into this directory

3. **Compile**
   ```bash
   javac PasswordGenerator.java
   ```

4. **Run**
   ```bash
   java PasswordGenerator
   ```

### Option 2: Using IntelliJ IDEA

1. Open IntelliJ IDEA
2. File → New → Project
3. Select Java and click Next
4. Name your project "PasswordGenerator"
5. Right-click `src` → New → Java Class → Name it "PasswordGenerator"
6. Paste the code
7. Click Run (▶) or press `Shift + F10`

### Option 3: Using Eclipse

1. Open Eclipse
2. File → New → Java Project
3. Name it "PasswordGenerator"
4. Right-click `src` → New → Class → Name it "PasswordGenerator"
5. Paste the code
6. Right-click file → Run As → Java Application

### Option 4: Using VS Code

1. Install Java Extension Pack
2. Create `PasswordGenerator.java` file
3. Paste the code
4. Click "Run" button above the `main` method

## 🚀 Usage

### Main Menu
When you run the program, you'll see:
```
╔════════════════════════════════════╗
║    PASSWORD GENERATOR v2.0         ║
╚════════════════════════════════════╝
1. Generate Password
2. Generate Multiple Passwords
3. View Password History
4. Clear History
5. Exit
```

### Generate a Single Password

1. Select option `1` from the main menu
2. Enter desired password length (minimum 4)
3. Choose character types:
   - Uppercase letters (A-Z)
   - Lowercase letters (a-z)
   - Digits (0-9)
   - Special characters (!@#$%^&*...)
4. View your generated password with strength meter
5. Choose to save to file (optional)

**Example:**
```
Enter password length (minimum 4): 16
Include uppercase letters? (y/n): y
Include lowercase letters? (y/n): y
Include digits? (y/n): y
Include special characters? (y/n): y

Generated Password: 7k@Xm9$pL2nQ#rT5

╔════════════════════════════════════╗
║     PASSWORD STRENGTH METER        ║
╠════════════════════════════════════╣
║  Entropy: 95.27 bits
║  Strength: Strong
║  [████████░░]
║
║  ✓✓ Strong - secure password
╚════════════════════════════════════╝
```

### Generate Multiple Passwords

1. Select option `2` from the main menu
2. Enter how many passwords you want
3. Configure character types (same as single generation)
4. All passwords will be displayed
5. Option to save all to file

### View Password History

Select option `3` to see all previously generated passwords with:
- Timestamp
- Password
- Entropy value
- Strength rating

### Clear History

Select option `4` to clear all passwords from memory (does not affect saved files)

## 🔒 Password Strength

### Entropy Scale

The password strength is calculated using entropy measurement:

| Entropy (bits) | Strength | Security Level |
|---------------|----------|----------------|
| < 28 | Very Weak | ⚠ Easily cracked |
| 28 - 36 | Weak | ⚠ Vulnerable to attacks |
| 36 - 60 | Medium | ✓ Acceptable for most uses |
| 60 - 128 | Strong | ✓✓ Secure password |
| 128+ | Very Strong | ✓✓✓ Excellent security |

### Formula
```
Entropy = log₂(pool_size^length)
```

Where:
- `pool_size` = total number of possible characters
- `length` = password length

### Recommendations

**For Maximum Security:**
- Length: 16+ characters
- Use all character types (uppercase, lowercase, digits, special)
- Target entropy: 80+ bits

**For Standard Security:**
- Length: 12-16 characters
- Use at least 3 character types
- Target entropy: 60+ bits

## 📁 File Structure

```
PasswordGenerator/
│
├── PasswordGenerator.java    # Main source file
├── PasswordGenerator.class   # Compiled bytecode
├── passwords.txt             # Generated passwords (created after first save)
└── README.md                 # This file
```

### passwords.txt Format

```
========================================
Generated: 2025-01-18 14:30:45
Password: 7k@Xm9$pL2nQ#rT5
Entropy: 95.27 bits
Strength: Strong
========================================
```

## 🔬 Technical Details

### Security Features
- **SecureRandom**: Uses Java's cryptographically secure random number generator
- **Character Guarantee**: Ensures password complexity by including at least one character from each selected type
- **Shuffling Algorithm**: Randomizes character positions to prevent patterns

### Key Classes and Methods

**Main Methods:**
- `generatePassword()`: Core password generation logic
- `calculateEntropy()`: Computes password entropy in bits
- `calculateStrength()`: Determines strength rating based on entropy
- `displayEntropyMeter()`: Visual representation of password strength
- `savePasswordToFile()`: Exports password with metadata to file
- `addToHistory()`: Tracks generated passwords in memory

**Data Structures:**
- `PasswordHistoryEntry`: Stores password with timestamp, entropy, and strength

### Character Sets
- **Uppercase**: 26 characters (A-Z)
- **Lowercase**: 26 characters (a-z)
- **Digits**: 10 characters (0-9)
- **Special**: 28 characters (!@#$%^&*()-_=+[]{}|;:,.<>?)

## 🤝 Contributing

Contributions are welcome! Here are some ways you can contribute:

1. **Report Bugs**: Open an issue describing the bug
2. **Suggest Features**: Share ideas for new features
3. **Submit Pull Requests**: Fix bugs or add features
4. **Improve Documentation**: Help make the README clearer

### Future Enhancement Ideas
- GUI version using JavaFX or Swing
- Password strength checker for existing passwords
- Pronounceable password generation
- Custom character set definition
- Password expiration reminders
- Batch generation with CSV export
- Password encryption for stored passwords

## 📄 License

This project is open source and available under the MIT License.

```
MIT License

Copyright (c) 2025

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## 📞 Support

If you encounter any issues or have questions:
1. Check the documentation above
2. Review existing issues
3. Create a new issue with detailed information

---

**Made with ☕ and Java**

*Last Updated: January 2025*

## Sasss
I will be using sass for styling so to work with that you have to have node installed
```shell
npm -v
node -v
```
if none of those worsks, you don't have node installed. Simply google and install that.
Then inside the project terminal, run the above commands again. If they now work and show a version. You can install the dependencies in "Package.json"
```shell
npm i
```

when you want to compile sass; go to search: File watchers: and <kbd>add</kbd>
```md
Program: $ProjectFileDir$\node_modules\.bin\sass
Arguments: --no-source-map $ProjectFileDir$/src/main/webapp/scss/$FileName$:$ProjectFileDir$/src/main/webapp/css/$FileNameWithoutExtension$.css
Output: $ProjectFileDir$/src/main/webapp/css/$FileNameWithoutExtension$.css
Working Directory: $ProjectFileDir$\src\main\webapp\scss
```

## Password Hasher

The `PasswordHasher` class has 2 methods.
1. `PasswordHasher.hashPassword(password)` Which takes in a string, the user entered password that needs to be hashed. This will return a hashed string.
2. `PasswordHashr.verifyPassword(enteredPassword, storedPassword)` This takes in two parameters, and returns a boolean of true or false.
    1. The user entered password as plain text,
   2. the stored password in the `user.json` and compare the two.

Example Usage
```java
// During user registration
String rawPassword = "user@123";
String hashedPassword = PasswordHasher.hashPassword(rawPassword);
// Store hashedPassword in users.json

// During login
String inputPassword = "user@123";
String storedHash = getUserHashFromDB(); // Retrieve from users.json
boolean isValid = PasswordHasher.verifyPassword(inputPassword, storedHash);
```
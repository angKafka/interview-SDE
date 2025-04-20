import database.UserDB;
import factory.ProxyFactory;
import model.User;
import service.UserService;
import util.DatabaseOperation;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create a scanner to get user input
        Scanner scanner = new Scanner(System.in);

        // Step 1: Create a UserDB with type reference
        UserDB<User> userDB = new UserDB<>(User.class);

        // Step 2: Inject real service with DB dependency
        UserService realService = new UserService(userDB, userDB.getAll());

        // Step 3: Create proxy that adds caching
        DatabaseOperation<User> userService = ProxyFactory.createProxy(realService, DatabaseOperation.class);

        while (true) {
            // Display menu
            System.out.println("\n--- User Service ---");
            System.out.println("1. Save User");
            System.out.println("2. Update User");
            System.out.println("3. Find User by ID");
            System.out.println("4. Exit");

            // Get user choice
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline left-over

            switch (choice) {
                case 1: // Save user
                    System.out.print("Enter user ID: ");
                    String userId = scanner.nextLine();
                    System.out.print("Enter user name: ");
                    String userName = scanner.nextLine();
                    System.out.print("Enter user score: ");
                    int userScore = scanner.nextInt();

                    userService.save(new User(userId, userName, userScore));
                    System.out.println("User saved successfully!");
                    break;

                case 2: // Update user
                    System.out.print("Enter user ID to update: ");
                    String updateUserId = scanner.nextLine();
                    System.out.print("Enter new name: ");
                    String updateUserName = scanner.nextLine();
                    System.out.print("Enter new score: ");
                    int updateUserScore = scanner.nextInt();

                    userService.update(new User(updateUserId, updateUserName, updateUserScore));
                    System.out.println("User updated successfully!");
                    break;

                case 3: // Find user by ID
                    System.out.print("Enter user ID to find: ");
                    int findUserId = scanner.nextInt();

                    User user = userService.findById(findUserId);
                    if (user != null) {
                        System.out.println("User found: " + user.getUserName());
                    } else {
                        System.out.println("User not found!");
                    }
                    break;

                case 4: // Exit
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
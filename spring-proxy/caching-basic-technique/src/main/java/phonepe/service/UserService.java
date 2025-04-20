package service;

import database.UserDB;
import model.User;
import util.DatabaseOperation;
import java.util.List;
import java.util.Optional;

public class UserService implements DatabaseOperation<User> {
    private List<User> users;
    private UserDB<User> userDB;

    // Constructor to initialize userDB and load users from DB
    public UserService(UserDB<User> userDB, List<User> users) {
        this.userDB = userDB;
        this.users = users; // Initialize users from the database on creation
    }

    @Override
    public void save(User user) {
        users.add(user); // Add user to the list (you may need additional logic to prevent duplicates)
        userDB.saveDataToLocalDB(); // Save to DB and invalidate cache
    }

    @Override
    public String update(User user) {
        Optional<User> userCheckInDb = users.stream().filter(user1 -> user1.getUserId().equals(user.getUserId())).findFirst();

        if (userCheckInDb.isPresent()) {
            users.remove(userCheckInDb.get()); // Remove old user and update with the new one
            users.add(user);
            userDB.saveDataToLocalDB(); // Save to DB and invalidate cache
            return String.format("User with userId=%s updated in the database.", user.getUserId());
        }

        return String.format("User with userId=%s not found.", user.getUserId());
    }

    @Override
    public List<User> findAll() {
        return users; // Return the in-memory list of users
    }

    @Override
    public User findById(int id) {
        String idAsString = String.valueOf(id);
        return userDB.getAll().stream()
                .filter(user -> user.getUserId().equals(idAsString))
                .findFirst()
                .orElse(null);
    }
}
package lt.mif.unit.exercise2;

import lt.mif.unit.dao.UserRepository;
import lt.mif.unit.exceptions.PasswordLengthException;
import lt.mif.unit.models.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserListController {

    private final UserRepository userRepository;


    public UserListController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Return a list of users that are not deleted.
     *
     * THIS METHOD IS CORRECT
     *
     * @return A map of (user id -> user name)
     */
    public Map<Long, String> getActiveUsers() {
        return userRepository.findActiveUsers().stream().collect(Collectors.toMap(User::getId, User::getName));
    }

    /**
     * Save a new user object.
     * Before saving the user's password will be hashed using {@link lt.mif.unit.exercise1.PasswordUtils#hashPassword(String)}.
     *
     * @param user user object to be saved
     * @return a user with id present and hashed password.
     * @throws PasswordLengthException if password is less than 8 characters long.
     */
    public User saveNewUser(User user) throws PasswordLengthException {
        Long id = userRepository.saveUser(user);
        return new User(id, user.getName(), user.getEmail(), user.getPassword(), false, false);
    }

    /**
     * Deleted selected and not locked user.
     *
     * @param selectedIds user ids that should be deleted
     * @return a collection of locked users that were not deleted.
     */
    public Collection<User> deleteSelected(List<Long> selectedIds) {
        // First, select all the selected users from the database.
        List<User> users = selectedIds.stream()
                .map(userRepository::findUserById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        List<User> failedToDelete = new ArrayList<>();

        // Next, delete not locked users and save the ones that were not deleted
        for (User u : users) {
            if (!(u.isLocked() && userRepository.deleteById(u.getId()))) {
                failedToDelete.add(u);
            }
        }
        return failedToDelete;
    }
}

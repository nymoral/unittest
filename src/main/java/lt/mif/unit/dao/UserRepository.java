package lt.mif.unit.dao;

import lt.mif.unit.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    /**
     * Get the users that are not deleted.
     *
     * @return a list of active users
     */
    List<User> findActiveUsers();

    /**
     * Find a user by it's id.
     *
     * @param id user id
     * @return {@link Optional} of a user if it is found (and not deleted) or empty optional.
     */
    Optional<User> findUserById(Long id);

    /**
     * Delete a user by it's id. This should set a deleted flag on the user's object
     *
     * @param id user id
     * @return true if user was deleted, false otherwise.
     */
    boolean deleteById(Long id);

    /**
     * Set the locked flag on the user.
     *
     * @param id user id
     * @return true if user was locked. False if it was already locked or the user doesn't exist.
     */
    boolean lockById(Long id);

    /**
     * Clear the unlocked flag from the user.
     *
     * @param id user id
     * @return true if user was unlocked. False if it was already unlocked or the user doesn't exist.
     */
    boolean unlockById(Long id);

    /**
     * Save a new or updated user. If id is set will use update operation, if not then insert.
     *
     * @param user
     * @return id of inserted / updated user
     */
    Long saveUser(User user);
}

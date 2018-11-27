package lt.mif.unit;

import lt.mif.unit.dao.SqliteUserRepository;
import lt.mif.unit.exercise2.UserListController;
import lt.mif.unit.models.User;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

public class Main {
    public static void main(String[] args) throws Exception {
        File dbFile = new File("test.db");
        dbFile.deleteOnExit();
        try (SqliteUserRepository repository = new SqliteUserRepository(dbFile)) {
            repository.createTables();
            UserListController controller = new UserListController(repository);


            User u1 = controller.saveNewUser(User.newUser("user1", "user1@email.com", "password1"));
            User u2 = controller.saveNewUser(User.newUser("user2", "user2@email.com", "password2"));
            User u3 = controller.saveNewUser(User.newUser("user3", "user3@email.com", "password3"));
            User u4 = controller.saveNewUser(User.newUser("user4", "user4@email.com", "password4"));

            System.out.println("Current users (we should see four users): " + controller.getActiveUsers());

            System.out.println("Locking user1 - we won't be able to delete it");
            repository.lockById(u1.getId());
            System.out.println("Deleting users 1, 2 and 3");
            Collection<User> notDeleted = controller.deleteSelected(Arrays.asList(1L, 2L, 3L));
            System.out.println("Failed to delete: " + notDeleted);
            System.out.println("Should have failed to delete the locked user (1)");
            System.out.println("Current users: " + controller.getActiveUsers());
            System.out.println("Should have two users left 1 and 4");
        }
    }
}

package lt.mif.unit.exercise2;

import lt.mif.unit.dao.UserRepository;
import lt.mif.unit.models.User;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


/**
 * Tests with mocking. (https://github.com/mockito/mockito/wiki)
 * <p>
 * To mock (provide a mock implementation) an object, use {@link Mockito#mock(Class)} method.
 * To define the behavior of the mocked instance, use {@link Mockito#when(Object)}:
 * <p>
 * <p>
 * {@code
 *
 * SomeInterface mockedObject = Mockito.mock(SomeInterface.class);
 * Mockito.when(mockedObject.methodCall(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(new ReturningValue());
 *
 * }
 * Please note that when mocking methods with arguments those arguments must be {@link ArgumentMatchers} objects.
 * <p>
 * <p>
 * For a test to fail if a required exception was not thrown use {@code assertThrows(MyException.class, () -> myObject.doThing()); }
 */
class UserListControllerTest {
    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    @Test
    void getActiveUsers() {
        UserListController controller = new UserListController(userRepository);

        Mockito.when(userRepository.findActiveUsers()).thenReturn(Collections.emptyList());
        assertEquals(0, controller.getActiveUsers().size(),
                "If database doesn't return any users the controller shouldn't return any either");

        Mockito.when(userRepository.findActiveUsers()).thenReturn(Collections.singletonList(new User(1L, "Bob", "email", "hunter2", false, false)));
        Map<Long, String> users = controller.getActiveUsers();
        assertEquals(1, users.size(), "Mocked data only one (active) user");
        assertEquals("Bob", users.get(1L), "The user has name 'Bob'");
        assertNull(users.get(2L), "Mocked data doesn't contain a user with id 2");


        Mockito.when(userRepository.findActiveUsers()).thenReturn(Arrays.asList(
                new User(1L, "Jane Doe", "email", "hunter2", false, false),
                new User(2L, "John Doe", "email", "hunter2", false, false)
        ));
        users = controller.getActiveUsers();
        assertEquals(2, users.size(), "When two users are active they both should be returned by the controller");
        assertEquals("Jane Doe", users.get(1L), "One user should have name 'Jane Doe'");
        assertEquals("John Doe", users.get(2L), "Other user should have name 'John Doe'");
    }
}
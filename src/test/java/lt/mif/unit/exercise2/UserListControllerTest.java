package lt.mif.unit.exercise2;

import lt.mif.unit.dao.UserRepository;
import lt.mif.unit.models.User;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Tests with mocking. (https://github.com/mockito/mockito/wiki)
 *
 * To mock (provide a mock implementation) an object, use {@link Mockito#mock(Class)} method.
 * To define the behavior of the mocked instance, use {@link Mockito#when(Object)}:
 * {@code
 *
 *  SomeInterface mockedObject = Mockito.mock(SomeInterface.class);
 *  Mockito.when(mockedObject.methodCall(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(new ReturningValue());
 *
 * }
 * Please note that when mocking methods with arguments those arguments must be {@link ArgumentMatchers} objects.
 *
 *
 * For a test to fail if exception was not thrown use @Test(expected = SomeException.class) annotation.
 */
public class UserListControllerTest {
    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    @Test
    public void getActiveUsers() {
        UserListController controller = new UserListController(userRepository);
        Mockito.when(userRepository.findActiveUsers()).thenReturn(Collections.emptyList());
        assertEquals(0, controller.getActiveUsers().size());

        Mockito.when(userRepository.findActiveUsers()).thenReturn(Collections.singletonList(new User(1L, "name", "email", "hunter2", false, false)));
        Map<Long, String> users = controller.getActiveUsers();
        assertEquals(1, users.size());
        assertEquals("name", users.get(1L));
        assertNull(users.get(2L));


        Mockito.when(userRepository.findActiveUsers()).thenReturn(Arrays.asList(
                new User(1L, "name1", "email", "hunter2", false, false),
                new User(2L, "name2", "email", "hunter2", false, false)
                ));
        users = controller.getActiveUsers();
        assertEquals(2, users.size());
        assertEquals("name1", users.get(1L));
        assertEquals("name2", users.get(2L));
    }
}
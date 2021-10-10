package com.education;

import com.education.model.CreateUserRequest;
import com.education.model.User;
import com.education.persistence.TaskDAO;
import com.education.persistence.TaskDaoInMemImpl;
import com.education.persistence.UserDAO;
import com.education.persistence.UserDaoInMemImpl;
import com.education.services.UserService;
import com.education.services.exceptions.UniqueEmailException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    public static final Long USER_ID = 2L;
    private final UserDAO userDAO = mock(UserDaoInMemImpl.class);
    private final TaskDAO taskDAO = mock(TaskDaoInMemImpl.class);
    private final CreateUserRequest createUserRequest = mock(CreateUserRequest.class);
    private final UserService service = new UserService(userDAO, taskDAO);
    private static final User USER = new User(2L, "Dan", "1@gmail.com", false);
    private static final CreateUserRequest USER_REQUEST = new CreateUserRequest(USER_ID, "Dan", "1@gmail.com", false);

    @Test
    public void testSaveUserUniqueEmailException() {
        User user = new User();
        user.setId(USER_ID);
        user.setName("Joshua");
        when(userDAO.getByEmail(user.getEmail()))
                .thenReturn(user);
        assertThrows(UniqueEmailException.class, () -> {
            service.saveUser(user);
        });
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setName("Joshua");
        user.setEmail("22@email.com");
        assertThat(user).isEqualTo(service.saveUser(user));
    }

//    @Test
//    public void testCreateFromRequest() {
//        ArgumentCaptor<CreateUserRequest> captor = ArgumentCaptor.forClass(CreateUserRequest.class);
//        when(userDAO.createUserFromRequest(any(CreateUserRequest.class))).thenReturn(new User(2L, "Dan", "1@gmail.com", false));
//        User result = service.createFromRequest(new CreateUserRequest(USER_ID, "Dan", "1@gmail.com", false));
//        assertThat(result).usingRecursiveComparison().isEqualTo(USER);
//        verify(userDAO).createUserFromRequest(captor.capture());
//        assertThat(captor.getValue()).usingRecursiveComparison().isEqualTo(USER_REQUEST);
//    }


}

package com.example.display;

import com.example.display.business.datasource.local.UsersLocalDataSource;
import com.example.display.business.datasource.remote.UsersRemoteDataSource;
import com.example.display.business.model.User;
import com.example.display.business.model.Users;
import com.example.display.business.repository.UsersRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subscribers.TestSubscriber;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UsersRepositoryTest {

    @Mock
    UsersRemoteDataSource usersRemoteDataSource;
    @Mock
    UsersLocalDataSource usersLocalDataSource;
    UsersRepository usersRepository;
    int page;

    @Before
    public void setUp() {
        usersRepository = new UsersRepository(usersRemoteDataSource, usersLocalDataSource);
        page = 1;
    }

    @Test
    public void testGetUsers_fromRemote_emptyLocal() {
        TestSubscriber<List<User>> testSubscriber = new TestSubscriber<>();

        // Fake local list
        List<User> emptyList = new ArrayList<>();
        // Fake remote list
        List<User> userList = new ArrayList<>();
        User user = new User(1,
                1,
                "my@email.com",
                "Pancho",
                "Villa",
                "https://reqres.in/img/faces/1-image.jpg"
        );
        userList.add(user);
        Users users = new Users(page, 6, 6, 2, userList);

        when(usersLocalDataSource.getUserList(page)).thenReturn(emptyList);
        when(usersRemoteDataSource.fetchUsers(page)).thenReturn(Single.just(users));

        usersRepository.getUsers(page).toFlowable().subscribe(testSubscriber);

        verify(usersRemoteDataSource).fetchUsers(page);
    }

    @Test
    public void testGetUsers_fromLocal() {
        TestSubscriber<List<User>> testSubscriber = new TestSubscriber<>();

        // Fake local list
        List<User> userList = new ArrayList<>();
        User user = new User(1,
                1,
                "my@email.com",
                "Pancho",
                "Villa",
                "https://reqres.in/img/faces/1-image.jpg"
        );
        userList.add(user);

        when(usersLocalDataSource.getUserList(page)).thenReturn(userList);
        usersRepository.getUsers(page).toFlowable().subscribe(testSubscriber);

        // Assert
        testSubscriber.assertValue(userList);
    }

}

package com.example.display;

import com.example.display.business.datasource.APIService;
import com.example.display.business.datasource.remote.UsersRemoteDataSource;
import com.example.display.business.model.User;
import com.example.display.business.model.Users;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subscribers.TestSubscriber;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UsersRemoteDataSourceTest {

    @Mock
    APIService apiService;
    UsersRemoteDataSource usersRemoteDataSource;

    @Before
    public void setUp() {
        usersRemoteDataSource = new UsersRemoteDataSource(apiService);
    }

    @Test
    public void testAPIResponse_success() {
        TestSubscriber<Users> testSubscriber = new TestSubscriber<>();

        // Fake response
        User user = new User(1,
                1,
                "my@email.com",
                "Pancho",
                "Villa",
                "https://reqres.in/img/faces/1-image.jpg"
        );
        List<User> userList = new ArrayList<>();
        userList.add(user);
        Users users = new Users(1, 6, 6, 2, userList);

        when(apiService.fetchUsers(1)).thenReturn(Single.just(users));
        usersRemoteDataSource.fetchUsers(1).toFlowable().subscribe(testSubscriber);

        // Assert
        testSubscriber.assertValue(users);
    }

    @Test
    public void testAPIResponse_fail_parameterNull() {
        // Fake response
        User user = new User(1,
                1,
                "my@email.com",
                "Pancho",
                "Villa",
                null
        );
        Assert.fail("Parameters can't be null");
    }

}

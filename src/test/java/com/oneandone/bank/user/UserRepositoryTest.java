package com.oneandone.bank.user;

import com.oneandone.bank.BankApplication;
import com.oneandone.bank.account.AccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

//TODO Testing - Run with sprig junit classRunner
//TODO Testing - Configure spring application context

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BankApplication.class)
public class UserRepositoryTest {

    //TODO Testing - field injection
    @Autowired
    UserRepository userRepository;

    //TODO Testing - field injection
    @Autowired
    AccountRepository accountRepo;

    @Before
    public void setup() {
        accountRepo.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    //TODO Testing - test save method
    public void save() {
        createTestUser();

    }

    private User createTestUser() {
        User user = new User();
        user.setName("test");
        user = userRepository.save(user);
        return user;
    }

    @Test
    //TODO Testing - test findOne method (by user id)
    public void findOne() {

        User user = createTestUser();
        User userRepo = userRepository.findOne(user.getId());
        Assert.assertEquals(user.getName(), userRepo.getName());

        //better if equals and hash ? is working
        Assert.assertEquals(user.getName(), userRepo.getName());
        Assert.assertEquals(user.getId(), userRepo.getId());
    }

    @Test
    //TODO Testing - test findByName method
    public void findByName() {

        User user = createTestUser();
        User userRepo = userRepository.findOneByName(user.getName());
        Assert.assertEquals(user.getName(), userRepo.getName());
        Assert.assertEquals(user.getId(), userRepo.getId());

    }

    @Test
    //TODO Testing - test findAll method
    public void findAll() {

        Collection<User> users = userRepository.findAll();
        Assert.assertEquals(0, users.size());

        createTestUser();
        users = userRepository.findAll();
        Assert.assertEquals(1, users.size());
        Assert.assertEquals("test", users.iterator().next().getName());
    }

    @Test
    //TODO Testing - test delete method
    public void delete() {

        User user = createTestUser();
        userRepository.delete(user.getId());
        Collection<User> users = userRepository.findAll();
        Assert.assertEquals(0, users.size());

    }

}

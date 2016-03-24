package com.oneandone.bank.user;

import com.oneandone.bank.BankApplication;
import com.oneandone.bank.account.Account;
import com.oneandone.bank.account.AccountRepository;
import com.oneandone.bank.account.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BankApplication.class)
public class UserServiceTest {

    private static final String TEST_USER_NAME = "test";

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    UserService userService;

    @Before
    public void setup() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void createUser() {
        createUser(TEST_USER_NAME);
    }

    private User createUser(String name) {
        User user = new User();
        user.setName(name);
        return userService.createUser(user);
    }

    private Account createAccount(User user) {
        Account account = new Account();
        account.setUser(user);
        return accountService.createAccount(account);
    }

    @Test
    public void getAllUsers() {
        createUser(TEST_USER_NAME);
        createUser("anotherUser");
        Collection<User> users = userService.getAllUsers();
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    public void getUser() {
        User testUser = createUser(TEST_USER_NAME);
        User user = userService.getUser(testUser.getId());
        assertNotNull(user);
        assertEquals(TEST_USER_NAME, user.getName());
    }

    @Test
    public void getAllUsersWithAccounts() {
        User user = createUser(TEST_USER_NAME);
        createUser("anotherUser");
        Collection<User> usersWithAccounts = userService.getAllUsersWithAccounts();
        assertNotNull(usersWithAccounts);
        assertEquals(2, usersWithAccounts.size());
        for (User userWithAccounts : usersWithAccounts) {
            assertTrue(userWithAccounts.getAccounts().isEmpty());
        }
        createAccount(user);
        usersWithAccounts = userService.getAllUsersWithAccounts();
        assertNotNull(usersWithAccounts);
        assertEquals(2, usersWithAccounts.size());
        User userWithAccounts = usersWithAccounts.iterator().next();
        Collection<Account> accounts = userWithAccounts.getAccounts();
        assertNotNull(accounts);
        assertEquals(1, accounts.size());
    }

    @Test
    public void updateUser() {
        User user = createUser(TEST_USER_NAME);
        user.setName("newTestUser");
        userService.updateUser(user);
        user = userRepository.findOne(user.getId());
        assertEquals("newTestUser", user.getName());
    }

    @Test
    public void closeAccount() {
        User user = createUser(TEST_USER_NAME);
        Account account1 = createAccount(user);
        Account account2 = createAccount(user);
        accountService.depositMoney(account1.getId(), 100d);
        accountService.redrawMoney(account2.getId(), 50d);
        Double totalFunds = userService.deleteUser(user.getId());
        assertEquals(50d, totalFunds, 0.01);
        user = userRepository.findOne(user.getId());
        assertNull(user);
    }
}

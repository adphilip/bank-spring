package com.oneandone.bank.account;

import com.oneandone.bank.BankApplication;
import com.oneandone.bank.user.User;
import com.oneandone.bank.user.UserRepository;
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
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Before
    public void setup() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void save() {
        User user = createUser();
        createTestAccount(user);
    }

    private Account createTestAccount(User user) {

        Account account = new Account();
        account.setUser(user);
        account.setFunds(100d);
        account = accountRepository.save(account);
        return account;
    }

    private User createUser() {
        User user = new User();
        user.setName("test");
        user = userRepository.save(user);
        return user;
    }

    @Test
    public void find() {
        User testUser = createUser();
        Account testAccount = createTestAccount(testUser);
        testAccount = accountRepository.findOne(testAccount.getId());
        assertNotNull(testAccount);
        assertEquals(100d, testAccount.getFunds(), 0.01);
        Account nullAccount = accountRepository.findOne(1);
        assertNull(nullAccount);
    }

    @Test
    public void findByUserId() {
        User testUser = createUser();
        createTestAccount(testUser);
        Collection<Account> testAccounts = accountRepository.findByUserId(testUser.getId());
        assertNotNull(testAccounts);
        assertEquals(1, testAccounts.size());
        Account testAccount = testAccounts.iterator().next();
        assertEquals(100d, testAccount.getFunds(), 0.01);
        Collection<Account> noAccounts = accountRepository.findByUserId(10000);
        assertTrue(noAccounts.isEmpty());
    }

    @Test
    public void findAll() {
        Collection<Account> accounts = accountRepository.findAll();
        assertNotNull(accounts);
        assertTrue(accounts.isEmpty());
        User testUser = createUser();
        createTestAccount(testUser);
        accounts = accountRepository.findAll();
        assertNotNull(accounts);
        assertEquals(1, accounts.size());
    }

    @Test
    public void delete() {
        User testUser = createUser();
        Account testAccount = createTestAccount(testUser);
        accountRepository.delete(testAccount.getId());
        Collection<Account> accounts = accountRepository.findAll();
        assertNotNull(accounts);
        assertTrue(accounts.isEmpty());
    }

}

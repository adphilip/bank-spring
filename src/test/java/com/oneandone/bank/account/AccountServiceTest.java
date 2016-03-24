package com.oneandone.bank.account;

import com.oneandone.bank.BankApplication;
import com.oneandone.bank.user.User;
import com.oneandone.bank.user.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BankApplication.class)
public class AccountServiceTest {

    private static final String TEST_USER_NAME = "test";

    //Testing - field injection
    // required for cleanup
    @Autowired
    AccountRepository accountRepository;

    //Testing - field injection
    // required for creating accounts and for cleanup
    @Autowired
    UserRepository userRepository;

    //Testing - field injection
    @Autowired
    AccountService accountService;

    @Before
    public void setup() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    //esting - test createAccount method
    public void createAccount() {
        // create an user
        // create an account for the previously created user

        User user = createUser(TEST_USER_NAME);
        Account account = createAccount(user);

    }

    private User createUser(String name) {
        User user = new User();
        user.setName(name);
        userRepository.save(user);
        return user;
    }

    private Account createAccount(User user) {
        Account account = new Account();
        account.setUser(user);
        return accountService.createAccount(account);
    }

    @Test
    public void getAllAccounts() {
        User user = createUser(TEST_USER_NAME);
        createAccount(user);
        createAccount(user);
        //TODO Testing - test getAllAccounts method
        // assert 2 users

       Collection<Account> accounts = accountService.getAllAccounts();
        Assert.assertEquals(2,accounts.size());
    }

    @Test
    public void getAccount() {
        User user = createUser(TEST_USER_NAME);
        Account account = createAccount(user);
        assertNotNull(account);
        //TODO Testing - test getAccount method
        //assert that the account has 0 funds
        assertNotNull(account);

        Assert.assertEquals(0,account.getFunds(),0.01);
        //assert that the account belong to the user
        Assert.assertEquals(user.getId(), account.getUser().getId());
    }

    @Test
    //TODO Testing - test getAccountsByUser method
    public void getAccountsByUser() {
        //assert no accounts are retrieved for an unknown user
        Collection<Account> accounts = accountService.getAccountsByUser(1);

        Assert.assertEquals(0,accounts.size());

        User user = createUser(TEST_USER_NAME);
        Account account = createAccount(user);
        // assert that there is 1 account for the user
        // assert that the account has 0 funds
        Collection<Account> accountsByUser = accountService.getAccountsByUser(user.getId());
        Assert.assertEquals(1, accountsByUser.size());

        Assert.assertEquals(0,accountsByUser.iterator().next().getFunds(),0.01);

    }

    @Test
    public void depositMoney() {
        User user = createUser(TEST_USER_NAME);
        Account account = createAccount(user);
        Collection<Account> accountsByUser = accountService.getAccountsByUser(user.getId());
        Double testFunds = accountsByUser.iterator().next().getFunds();
        assertEquals(0d, testFunds, 0.01);
        //TODO Testing - test depositMoney method
        // deposit an amount to the account
        accountService.depositMoney(account.getId(), 100d);

       // assert that the account has the deposited amount of money
        account = accountService.getAccount(account.getId());
        Assert.assertEquals(100,account.getFunds(),0.01);
    }

    @Test
    public void redrawMoney() {
        User user = createUser(TEST_USER_NAME);
        Account account = createAccount(user);
        Collection<Account> accountsByUser = accountService.getAccountsByUser(user.getId());
        Double testFunds = accountsByUser.iterator().next().getFunds();
        assertEquals(0d, testFunds, 0.01);
        //TODO Testing - test redrawMoney method
        // redraw an amount to the account
        accountService.redrawMoney(account.getId(), 100d);

        // assert that the account has the (-1)*amount of money
        account = accountService.getAccount(account.getId());
        Assert.assertEquals(-100,account.getFunds(),0.01);
    }

    @Test
    public void transferMoney() {
        User user = createUser(TEST_USER_NAME);
        Account srcAccount = createAccount(user);
        Account destAccount = createAccount(user);
        Collection<Account> accountsByUser = accountService.getAccountsByUser(user.getId());
        for (Account account : accountsByUser) {
            assertEquals(0d, account.getFunds(), 0.01);
        }
        //TODO Testing - test transferMoney method
        // transfer an amount of money from the srcAccount to the destAccount
        accountService.transferMoney(srcAccount.getId(), destAccount.getId(), 55d);
        // assert that the srcAccount has the  (-1)*amount of money
        srcAccount = accountService.getAccount(srcAccount.getId());
        Assert.assertEquals(-55d,srcAccount.getFunds(),0.01);
        // assert that the destAccount has the amount of money
        destAccount = accountService.getAccount(destAccount.getId());
        Assert.assertEquals(55d,destAccount.getFunds(),0.01);
    }

    @Test
    public void closeAccount() {
        User user = createUser(TEST_USER_NAME);
        Account account1 = createAccount(user);
        Account account2 = createAccount(user);
        accountService.depositMoney(account1.getId(), 100d);
        accountService.redrawMoney(account2.getId(), 50d);
        //TODO Testing - test closeAccounts method
        // assert that the closeAccount returns +100-50  money
        Double totalFunds = accountService.closeAccounts(user.getId());
        Assert.assertEquals(50d,totalFunds,0.01);
    }
}

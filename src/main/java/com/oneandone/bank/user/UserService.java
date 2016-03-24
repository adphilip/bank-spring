package com.oneandone.bank.user;

import com.oneandone.bank.account.Account;
import com.oneandone.bank.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


@Service
public class UserService {

    //Spring Core - use field dependency injection
    @Autowired
    private UserRepository userRepository;

    //Spring Core - use field dependency injection
    @Autowired
    private AccountService accountService;

    //Spring Core - Create user usecase. The returned user must have an allocated id
    public User createUser(User user) {

        return userRepository.save(user);
    }

    //TODO Spring Core - Get all the users usecase
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    //TODO Spring Core - Get all the users usecase. Populate the the accounts of all the returned users
    public Collection<User> getAllUsersWithAccounts() {

        Collection<User> users = getAllUsers();

        for(User user: users) {
            Collection<Account> accountByUser = accountService.getAccountsByUser(user.getId());
            user.setAccounts(accountByUser);

        }

        return users;
    }

    //TODO Spring Core - Get the user by it's id
    public User getUser(Integer id) {

        return userRepository.findOne(id);
    }

    //TODO Spring Core - Update the user
    public void updateUser(User user) {

        userRepository.save(user);
    }

    //Spring Core - Delete the user specified by it's id. Also delete all the user's accounts. Return the total amount of the user's accounts
    //Spring Data JTA - Run this method in a transaction
    @Transactional
    public Double deleteUser(Integer id) {

        Double totalFounds = accountService.closeAccounts(id);
        userRepository.delete(id);
        return totalFounds;
    }
}

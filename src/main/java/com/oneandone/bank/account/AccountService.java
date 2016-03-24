package com.oneandone.bank.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


@Service
public class AccountService {

    //Spring Core - use field dependency injection
    @Autowired
    private AccountRepository accountRepository;

    //Spring Core - Create account usecase. If the input account has no funds, initialize them with 0d. The returned account must have an allocated id
    public Account createAccount(Account account) {

        account.setId(null);

        if(account.getFunds() == null) {
            account.setFunds(0d);
        }
        account = accountRepository.save(account);
        return account;
    }

    //Spring Core - Get all the accounts usecase
    public Collection<Account> getAllAccounts() {

        return accountRepository.findAll();

    }

    //Spring Core - Get the account by it's id
    public Account getAccount(Integer id) {

        return accountRepository.findOne(id);

    }

    //Spring Core - Get all the accounts that belong to the specified user
    public Collection<Account> getAccountsByUser(Integer userId) {

        return accountRepository.findByUserId(userId);
    }

    //Spring Core - Deposit the 'amount' of money into the account specified by it's id
    public void depositMoney(Integer id, Double amount) {

        Account account = accountRepository.findOne(id);
        account.setFunds(account.getFunds() + amount);

        //save - why not ?
        accountRepository.save(account);

    }

    //Spring Core - Redraw the 'amount' of money into the account specified by it's id
    public void redrawMoney(Integer id, Double amount) {

        Account account = accountRepository.findOne(id);
        account.setFunds(account.getFunds() - amount);

        //save - why not ?
        accountRepository.save(account);

    }

    //Spring Core - Transfer the 'amount' of money from the 'sourceId' account to the 'destinationId' account
    //TSpring Data JTA - Run this method in a transaction
    @Transactional
    public void transferMoney(Integer sourceId, Integer destinationId, Double amount) {

        Account accountSource = accountRepository.findOne(sourceId);
        Account accountDestination = accountRepository.findOne(destinationId);

        //transfer
        accountSource.setFunds(accountSource.getFunds() - amount);
        accountDestination.setFunds(accountDestination.getFunds() + amount);

        //save
        accountRepository.save(accountSource);
        accountRepository.save(accountDestination);

       // redrawMoney(sourceId, amount);
        // depositMoney(destinationId, amount);


    }

    //Spring Core - Delete all the accounts of the specified user, returning the total amount
    //Spring Data JTA - Run this method in a transaction
    @Transactional
    public Double closeAccounts(Integer userId) {
        Double totalFunds = 0d;

        Collection<Account> accounts = accountRepository.findByUserId(userId);

        for(Account account : accounts) {
            totalFunds += account.getFunds();
            accountRepository.delete(account.getId());
        }

        return totalFunds;
    }
}

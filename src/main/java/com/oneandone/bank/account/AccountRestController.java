package com.oneandone.bank.account;

import com.oneandone.bank.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

//REST - Make this a rest controller bean
@RestController
public class AccountRestController {

    @Autowired
    private AccountService accountService;

    // REST - Map this method to the endpoint POST "/users/{userId}/accounts". The Account is in "application/json" format
    @RequestMapping(value="/users/{userId}/accounts", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createAccount(@PathVariable Integer userId, @RequestBody Account account) {
        //set the proper user to the account

        User user = new User();
        user.setId(userId);
        account.setUser(user);
        account =  accountService.createAccount(account);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(account.getId()).toUri());
        return new ResponseEntity<>(account, httpHeaders, HttpStatus.CREATED);
    }

    //REST - Map this method to the endpoint GET "/accounts".
    @RequestMapping("/accounts")
    public Collection<Account> getAllAccounts() {

      return   accountService.getAllAccounts();
    }

    //REST - Map this method to the endpoint GET "/accounts/{id}".
    @RequestMapping("/accounts/{id}")
    public Account getAccount(@PathVariable Integer id) {
       return accountService.getAccount(id);

    }

    //REST - Map this method to the endpoint GET "/users/{userId}/accounts".
    @RequestMapping("/users/{id}/accounts")
    public Collection<Account> getAccountsByUser(@PathVariable Integer userId) {
      return   accountService.getAccountsByUser(userId);
    }

    // REST - Map this method to the endpoint PUT "/accounts/{id}". The updateAccountRequest is in "application/json" format
    @RequestMapping(value="/accounts/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public void updateAccount(@PathVariable Integer id, @RequestBody UpdateAccountRequest updateAccountRequest) {
        // use updateAccountRequest.getOperation() to see if it is a deposit or a redrawal
        if(updateAccountRequest.getOperation() == UpdateAccountRequest.Operation.DEPOSIT) {
            accountService.depositMoney(id, updateAccountRequest.getAmount());

        } else if (updateAccountRequest.getOperation() == UpdateAccountRequest.Operation.REDRAW){
            accountService.redrawMoney(id, updateAccountRequest.getAmount());
        }


    }

    // REST - Map this method to the endpoint PUT "/accounts". The transferMoneyRequest is in "application/json" format
    @RequestMapping(value="/accounts", method = RequestMethod.PUT, consumes = "application/json")
    public void transferMoney(@RequestBody TransferMoneyRequest transferMoneyRequest) {
      accountService.transferMoney(transferMoneyRequest.getSource(),
              transferMoneyRequest.getDestination(),
              transferMoneyRequest.getAmount());
    }




}

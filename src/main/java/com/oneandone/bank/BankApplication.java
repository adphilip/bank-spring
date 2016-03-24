package com.oneandone.bank;

import com.oneandone.bank.account.Account;
import com.oneandone.bank.account.AccountService;
import com.oneandone.bank.user.User;
import com.oneandone.bank.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//TODO JMS - Enable Jms
public class BankApplication {

    public static void main(String[] args) {
        //TODO JMS - uncomment this to
        // Clean out any ActiveMQ data from a previous run
        //FileSystemUtils.deleteRecursively(new File("activemq-data"));

        SpringApplication.run(BankApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(final UserService userService, final AccountService accountService) {
		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {
                // REST - uncomment this to add 2 users and 3 accounts
                User user = new User();
                user.setName("Ion");
				user = userService.createUser(user);
                Account account = new Account();
                account.setUser(user);
                account = accountService.createAccount(account);
                accountService.depositMoney(account.getId(), 100d);

                account.setFunds(0d);
                account = accountService.createAccount(account);
                accountService.depositMoney(account.getId(), 10d);

                user = new User();
                user.setName("Gheorghe");
                user = userService.createUser(user);
                account.setUser(user);
                account.setFunds(0d);
                account = accountService.createAccount(account);
                accountService.depositMoney(account.getId(), 50d);
			}
		};
	}
}

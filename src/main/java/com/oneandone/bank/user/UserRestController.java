package com.oneandone.bank.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.ws.rs.QueryParam;
import java.util.Collection;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        user = userService.createUser(user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping("/users")
    public Collection<User> getAllUsers(@QueryParam("withAccounts") boolean withAccounts) {
        return withAccounts ? userService.getAllUsersWithAccounts() : userService.getAllUsers();
    }

    @RequestMapping("/users/{id}")
    public User getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public void updateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        userService.updateUser(user);

    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public Double deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }
}

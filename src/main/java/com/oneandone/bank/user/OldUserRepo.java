import com.oneandone.bank.user.User;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class OldUserRepo{
    private Map<Integer, User> repo = new ConcurrentHashMap<>();
    private AtomicInteger index = new AtomicInteger(0);

    //TODO Spring Core - Add the user to the repository (and increment the index) if it does not exists. Update the user if it's in the repo
    public User save(User user) {

        Integer id = user.getId();

        if(id == null) {
            id = index.incrementAndGet();
            user.setId(id);
        }

        repo.put(id, user);
        return user;

    }

    /* TODO Spring Core - Find the user by it's id */
    public User findOne(Integer id) {

        return repo.get(id);
    }

    //TODO Spring Core - Find the user by it's name
    public User findOneByName(String name) {


        for (User user : repo.values()) {
            if (user.getName().equals(name)){
                return user;
            }
        }

        return null;
    }

    //TODO Spring Core - Find all the users in the repository
    public Collection<User> findAll() {

        return repo.values();
    }

    //TODO Spring Core - Delete a user by it's id
    public void delete(Integer id) {

        repo.remove(id);

    }

    //TODO Spring Core - Delete all the users in the repository
    public void deleteAll() {

        repo.clear();

    }

    //TODO Spring Core - Call this method when the bean is created
    @PostConstruct
    void init() {
        System.out.println("UserRepository created");
    }

    //TODO Spring Core - Call this method when the bean is Destroyed
    @PreDestroy
    void destroy() {
        System.out.println("UserRepository destroyed");
    }
}

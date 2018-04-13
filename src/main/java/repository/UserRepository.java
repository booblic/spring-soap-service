package repository;

import country.Country;
import country.Currency;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import user.Role;
import user.User;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class UserRepository {

    private static final Set<User> users = new HashSet<>();

    @PostConstruct
    public void initData() {
        User userAdmin = new User();
        userAdmin.setUsername("admin");
        userAdmin.setPassword("admin");

        Set<Role> roles = new HashSet<>();
        Role roleAdmin = new Role();
        roleAdmin.setAuthority("ADMIN_ROLE");
        roleAdmin.setRoleName("Administrator");
        Role roleUser = new Role();
        roleUser.setAuthority("USER_ROLE");
        roleAdmin.setRoleName("User");
        roles.add(roleAdmin);
        roles.add(roleUser);

        userAdmin.setRoles(roles);

        users.add(userAdmin);
    }

    public User findUser(String username) {


        Assert.notNull(username, "The country's name must not be null");
        User currentUser = null;
        for (User user: users) {
            if (user.getUsername().compareTo(username) == 0) {

                currentUser = user;
            }
        }

        return currentUser;
    }
}

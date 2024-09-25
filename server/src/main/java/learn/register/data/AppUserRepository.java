package learn.register.data;

import learn.register.models.AppUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AppUserRepository {
    @Transactional
    AppUser findByUsername(String username);

    @Transactional
    AppUser create(AppUser user);

    @Transactional
    void update(AppUser user);

    void updateRoles(AppUser user);

    public List<String> getRolesByUsername(String username);
}

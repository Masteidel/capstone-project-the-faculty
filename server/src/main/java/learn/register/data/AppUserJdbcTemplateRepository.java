package learn.register.data;

import learn.register.models.AppUser;

public class AppUserJdbcTemplateRepository implements AppUserRepository {
    @Override
    public AppUser findByUsername(String username) {
        return null;
    }

    @Override
    public AppUser create(AppUser appUser) {
        return null;
    }

    @Override
    public void update(AppUser appUser) {

    }
}

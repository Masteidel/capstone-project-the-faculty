package learn.register.data;

import learn.register.models.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;

public class AppUserJdbcTemplateRepository implements AppUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public AppUserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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

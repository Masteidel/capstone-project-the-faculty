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

    private List<String> getRolesByUsername(String username) {
        final String sql = "select r.name "
                + "from app_user_role ur "
                + "inner join app_role r on ur.app_role_id = r.app_role_id "
                + "inner join app_user au on ur.app_user_id = au.app_user_id "
                + "where au.username = ?;";

        return jdbcTemplate.queryForList(sql, String.class, username);
    }
}

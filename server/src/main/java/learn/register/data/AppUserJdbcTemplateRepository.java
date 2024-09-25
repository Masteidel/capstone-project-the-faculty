package learn.register.data;

import learn.register.data.mappers.AppUserMapper;
import learn.register.models.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

@Repository
public class AppUserJdbcTemplateRepository implements AppUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public AppUserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public AppUser findByUsername(String username) {
        List<String> roles = getRolesByUsername(username);

        final String sql = "select app_user_id, username, password_hash, disabled "
                + "from app_user "
                + "where username = ?;";

        return jdbcTemplate.query(sql, new AppUserMapper(roles), username)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    @Transactional
    public AppUser create(AppUser user) {

        final String sql = "INSERT INTO app_user (username, password_hash, disabled) VALUES (?, ?, ?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setBoolean(3, user.isDisabled()); // Explicitly set the disabled field
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setAppUserId(keyHolder.getKey().intValue());

        updateRoles(user);

        return user;
    }

    @Override
    @Transactional
    public void update(AppUser user) {

        final String sql = "update app_user set "
                + "username = ?, "
                + "disabled = ? "
                + "where app_user_id = ?";

        jdbcTemplate.update(sql,
                user.getUsername(), !user.isEnabled(), user.getAppUserId());

        updateRoles(user);
    }

    @Override
    public void updateRoles(AppUser user) {

        // Delete all roles for the user
        jdbcTemplate.update("delete from app_user_role where app_user_id = ?;", user.getAppUserId());

        Collection<GrantedAuthority> authorities = user.getAuthorities();

        if (authorities == null || authorities.isEmpty()) {
            return;
        }

        // Convert authorities back to roles with the "ROLE_" prefix
        List<String> roles = AppUser.convertAuthoritiesToRoles(authorities);

        // Log roles before updating the database
        System.out.println("Roles to be inserted: " + roles);

        // Re-add roles by looking up role IDs in the app_role table
        for (String role : roles) {
            String sql = "insert into app_user_role (app_user_id, app_role_id) "
                    + "select ?, app_role_id from app_role where name = ?;";

            // Now we are using the full role name including the "ROLE_" prefix
            jdbcTemplate.update(sql, user.getAppUserId(), role);
        }
    }


    @Override
    public List<String> getRolesByUsername(String username) {
        final String sql = "select r.name "
                + "from app_user_role ur "
                + "inner join app_role r on ur.app_role_id = r.app_role_id "
                + "inner join app_user au on ur.app_user_id = au.app_user_id "
                + "where au.username = ?";

        // Query the roles and return a list of role names
        return jdbcTemplate.query(sql, (rs, rowId) -> rs.getString("name"), username);
    }
}

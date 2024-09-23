package learn.register.data;

import learn.register.data.mappers.AppUserMapper;
import learn.register.models.AppUser;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class AppUserJdbcTemplateRepository implements AppUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public AppUserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AppUser> findAll() {
        final String sql = "SELECT app_user_id, username, password_hash, disabled FROM app_user";
        return jdbcTemplate.query(sql, new AppUserMapper());
    }

    @Override
    @Transactional
    public AppUser findById(int appUserId) {
        final String sql = "SELECT app_user_id, username, password_hash, disabled FROM app_user WHERE app_user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new AppUserMapper(), appUserId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public AppUser add(AppUser appUser) {
        final String sql = "INSERT INTO app_user (username, password_hash, disabled) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, appUser.getUsername());
            ps.setString(2, appUser.getPasswordHash());
            ps.setBoolean(3, appUser.isDisabled());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        appUser.setAppUserId(keyHolder.getKey().intValue());
        return appUser;
    }

    @Override
    public boolean update(AppUser appUser) {
        final String sql = "UPDATE app_user SET username = ?, password_hash = ?, disabled = ? WHERE app_user_id = ?";
        return jdbcTemplate.update(sql,
                appUser.getUsername(),
                appUser.getPasswordHash(),
                appUser.isDisabled(),
                appUser.getAppUserId()) > 0;
    }

    @Override
    public AppUser findByUsername(String username) {
        final String sql = "SELECT app_user_id, username, password_hash, disabled FROM app_user WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new AppUserMapper(), username);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public boolean deleteById(int appUserId) {
        final String sql = "DELETE FROM app_user WHERE app_user_id = ?";
        return jdbcTemplate.update(sql, appUserId) > 0;
    }
}

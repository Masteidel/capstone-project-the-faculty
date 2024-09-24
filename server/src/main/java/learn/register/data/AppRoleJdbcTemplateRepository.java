/*
package learn.register.data;

import learn.register.data.mappers.AppRoleMapper;
import learn.register.models.AppRole;
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
public class AppRoleJdbcTemplateRepository implements AppRoleRepository {

    private final JdbcTemplate jdbcTemplate;

    public AppRoleJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AppRole> findAll() {
        final String sql = "SELECT app_role_id, name FROM app_role";
        return jdbcTemplate.query(sql, new AppRoleMapper());
    }

    @Override
    @Transactional
    public AppRole findById(int appRoleId) {
        final String sql = "SELECT app_role_id, name FROM app_role WHERE app_role_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new AppRoleMapper(), appRoleId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public AppRole add(AppRole appRole) {
        final String sql = "INSERT INTO app_role (name) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, appRole.getName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        appRole.setAppRoleId(keyHolder.getKey().intValue());
        return appRole;
    }

    @Override
    public boolean update(AppRole appRole) {
        final String sql = "UPDATE app_role SET name = ? WHERE app_role_id = ?";
        return jdbcTemplate.update(sql,
                appRole.getName(),
                appRole.getAppRoleId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int appRoleId) {
        final String sql = "DELETE FROM app_role WHERE app_role_id = ?";
        return jdbcTemplate.update(sql, appRoleId) > 0;
    }
}
*/
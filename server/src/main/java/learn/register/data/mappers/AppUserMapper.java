package learn.register.data.mappers;

import learn.register.models.AppUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppUserMapper implements RowMapper<AppUser> {

    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        AppUser appUser = new AppUser();
        appUser.setAppUserId(rs.getInt("app_user_id"));
        appUser.setUsername(rs.getString("username"));
        appUser.setPasswordHash(rs.getString("password_hash"));
        appUser.setDisabled(rs.getBoolean("disabled"));
        return appUser;
    }
}

package learn.register.data.mappers;

import learn.register.models.Agency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AgencyMapper implements RowMapper<Agency> {

    @Override
    public Agency mapRow(ResultSet resultSet, int i) throws SQLException {
        Agency agency = new Agency();
        agency.setAgencyId(resultSet.getInt("agency_id"));
        agency.setShortName(resultSet.getString("short_name"));
        agency.setLongName(resultSet.getString("long_name"));
        return agency;
    }
}

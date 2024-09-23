package learn.register.data;

import learn.register.models.SecurityClearance;

import java.util.List;

public interface SecurityClearanceRepository {
    List<SecurityClearance> findAll();

    SecurityClearance findById(int securityClearanceId);

    SecurityClearance findByName(String name);

    SecurityClearance add(SecurityClearance securityClearance);

    boolean update(SecurityClearance securityClearance);

    boolean deleteById(int securityClearanceId);

    int getUsageCount(int securityClearanceId);
}

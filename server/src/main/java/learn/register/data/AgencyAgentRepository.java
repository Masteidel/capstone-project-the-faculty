package learn.register.data;

import learn.register.models.AgencyAgent;

public interface AgencyAgentRepository {
    boolean add(AgencyAgent agencyAgent);

    boolean update(AgencyAgent agencyAgent);

    boolean deleteByKey(int agencyId, int agentId);
}

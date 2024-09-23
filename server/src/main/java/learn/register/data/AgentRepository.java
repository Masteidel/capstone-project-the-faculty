package learn.register.data;

import learn.register.models.Agent;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AgentRepository {
    List<Agent> findAll();

    Agent findById(int agentId);

    Agent add(Agent agent);

    boolean update(Agent agent);

    @Transactional
    boolean deleteById(int agentId);
}

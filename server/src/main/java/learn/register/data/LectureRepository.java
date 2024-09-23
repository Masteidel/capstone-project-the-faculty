package learn.register.data;

import learn.register.models.Alias;

import java.util.List;

public interface AliasRepository {
    List<Alias> findByName(String name);

    Alias add(Alias alias);

    boolean update(Alias alias);

    boolean deleteById(int aliasId);
}

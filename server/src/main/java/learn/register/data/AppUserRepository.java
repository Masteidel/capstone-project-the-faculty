package learn.register.data;

import learn.register.models.AppUser;

import java.util.List;

public interface AppUserRepository {
    List<AppUser> findAll();
    AppUser findById(int appUserId);
    AppUser findByUsername(String username);  // For authentication
    AppUser add(AppUser appUser);
    boolean update(AppUser appUser);
    boolean deleteById(int appUserId);
}
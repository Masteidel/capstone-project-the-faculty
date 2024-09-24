//package learn.register.models;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.util.Assert;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//public class AppUser extends User {
//
//    private static final String AUTHORITY_PREFIX = "ROLE_";
//
//    private UUID appUserId;
//    private List<String> roles = new ArrayList<>();
//
//    public AppUser(UUID appUserId, String username, String password, boolean disabled, List<String> roles) {
//        super(username, password, !disabled,
//                true, true, true,
//                convertRolesToAuthorities(roles));
//
//        this.appUserId = appUserId;
//    }
//
//    public UUID getAppUserId() {
//        return appUserId;
//    }
//
//    public void setAppUserId(UUID appUserId) {
//        this.appUserId = appUserId;
//    }
//
//    public static List<GrantedAuthority> convertRolesToAuthorities(List<String> roles) {
//        List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
//        for (String role : roles) {
//            Assert.isTrue(!role.startsWith(AUTHORITY_PREFIX),
//                    () -> String.format(
//                            "%s cannot start with %s (it is automatically added)",
//                            role,
//                            AUTHORITY_PREFIX
//                    )
//            );
//        }
//
//        return authorities;
//    }
//
//    public List<String> convertAuthoritiesToRoles(Collection<GrantedAuthority> authorities) {
//        return authorities.stream()
//                .map(a -> a.getAuthority().substring(AUTHORITY_PREFIX.length()))
//                .collect(Collectors.toList());
//    }
//}

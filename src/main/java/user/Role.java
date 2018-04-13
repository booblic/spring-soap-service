package user;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    private String roleName;

    private String authority;

    public Role() {}

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return null;
    }
}

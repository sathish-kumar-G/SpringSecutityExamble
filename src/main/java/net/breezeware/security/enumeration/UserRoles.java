package net.breezeware.security.enumeration;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static net.breezeware.security.enumeration.UserPermission.*;

public enum UserRoles {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(STUDENT_WRITE, STUDENT_READ, COURSE_READ, COURSE_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(STUDENT_READ, COURSE_READ));

    private final Set<UserPermission> userPermissions;

    UserRoles(Set<UserPermission> userPermissions) {
        this.userPermissions = userPermissions;
    }

    public Set<UserPermission> getUserPermissions() {
        return userPermissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority() {
        Set<SimpleGrantedAuthority> authority = getUserPermissions().stream()
                .map((permission -> new SimpleGrantedAuthority(permission.getPermissions())))
                .collect(Collectors.toSet());
        authority.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authority;
    }
}

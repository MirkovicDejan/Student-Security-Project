package com.securityproject.securityproject.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),

    ADMIN(Sets.newHashSet(ApplicationUserPremission.COURSE_READ,
            ApplicationUserPremission.COURSE_WRITE,
            ApplicationUserPremission.STUDENT_READ,
            ApplicationUserPremission.STUDENT_WRITE)),

    ADMINPART(Sets.newHashSet(ApplicationUserPremission.COURSE_READ,
            ApplicationUserPremission.STUDENT_READ));

    private final Set<ApplicationUserPremission> premission;

    ApplicationUserRole(Set<ApplicationUserPremission> premission) {
        this.premission = premission;
    }

    public Set<ApplicationUserPremission> getPremission() {
        return premission;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> premissions = getPremission()
                .stream()
                .map(premission -> new SimpleGrantedAuthority(premission.getPremission()))
                .collect(Collectors.toSet());
        premissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return premissions;
    }
}

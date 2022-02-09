package com.securityproject.securityproject.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import static com.securityproject.securityproject.security.ApplicationUserRole.*;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDAO {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser>getApplicationUsers(){
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(ADMIN.getGrantedAuthorities(),
                                     passwordEncoder.encode("password123"),
                                     "Dejan",
                                     true,
                                     true,
                                     true,
                                     true
                                  ),
                new ApplicationUser(STUDENT.getGrantedAuthorities(),
                passwordEncoder.encode("password"),
                "ana",
                true,
                true,
                true,
                true
                                   ),
                new ApplicationUser(ADMINPART.getGrantedAuthorities(),
                        passwordEncoder.encode("password1234"),
                        "aleksandar",
                        true,
                        true,
                        true,
                        true
                                  )
        );
        return applicationUsers;
    }
}

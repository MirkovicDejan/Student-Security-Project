package com.securityproject.securityproject.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*")
                .permitAll()
                .antMatchers("/get-student").hasRole(ApplicationUserRole.STUDENT.name())
                .antMatchers("/delete-student").hasAuthority(ApplicationUserPremission.COURSE_WRITE.getPremission())
                .antMatchers("/register-new-student").hasAuthority(ApplicationUserPremission.COURSE_WRITE.getPremission())
                .antMatchers("/update-student").hasAuthority(ApplicationUserPremission.COURSE_WRITE.getPremission())
                .antMatchers("/get-all-students").hasAnyRole(ApplicationUserRole.ADMIN.name(),ApplicationUserRole.ADMINPART.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails ana = User.builder()
                .username("ana")
                .password(passwordEncoder.encode("password"))
            //    .roles(ApplicationUserRole.STUDENT.name())
                .authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
                .build();
        UserDetails dejan = User.builder()
                .username("dejan")
                .password(passwordEncoder.encode("password123"))
         //       .roles(ApplicationUserRole.ADMIN.name())
                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
                .build();
        UserDetails aleksandar = User.builder()
                .username("aleksandar")
                .password(passwordEncoder.encode("password1234"))
          //      .roles(ApplicationUserRole.ADMINPART.name())
                .authorities(ApplicationUserRole.ADMINPART.getGrantedAuthorities())
                .build();
        return new InMemoryUserDetailsManager(ana,dejan,aleksandar);
    }
}

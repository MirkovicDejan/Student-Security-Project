package com.securityproject.securityproject.security;
import com.securityproject.securityproject.auth.ApplicationUserService;
import com.securityproject.securityproject.jwt.JwtTokenVerifier;
import com.securityproject.securityproject.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // this is for first and another way
    /*    http
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
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/courses",true)
                .and()
                .rememberMe().tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
                .key("somethingverysecured")
                .rememberMeParameter("remember-me")
                .and()
                .logout()
                .logoutUrl("/logout");*/

        //This is for JWT !
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
                .addFilterAfter(new JwtTokenVerifier(),JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*")
                .permitAll()
                .antMatchers("/get-student").hasRole(ApplicationUserRole.STUDENT.name())
                .antMatchers("/delete-student").hasAuthority(ApplicationUserPremission.COURSE_WRITE.getPremission())
                .antMatchers("/register-new-student").hasAuthority(ApplicationUserPremission.COURSE_WRITE.getPremission())
                .antMatchers("/update-student").hasAuthority(ApplicationUserPremission.COURSE_WRITE.getPremission())
                .antMatchers("/get-all-students").hasAnyRole(ApplicationUserRole.ADMIN.name(),ApplicationUserRole.ADMINPART.name())
                .anyRequest()
                .authenticated();

    }
    // another way
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }
    // another way
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(applicationUserService);
        return daoAuthenticationProvider;
    }
// First Way
 /*   @Override
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
    } */


}

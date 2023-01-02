package net.breezeware.security.configuration;

import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static net.breezeware.security.enumeration.UserPermission.COURSE_WRITE;
import static net.breezeware.security.enumeration.UserRoles.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

/*    //Current Scenario
SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity.csrf().disable().authorizeRequests()
            .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
            .antMatchers("/api/**").hasRole(STUDENT.name())
            *//* .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermissions())
             .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermissions())
             .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermissions())
             .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())*//*
            .anyRequest().authenticated()
            .and()
            // .httpBasic(); //Basic Authentication
            .formLogin()  //Form Based Authentication
            .loginPage("/login").permitAll() //Permit All Users in Login Page
            .defaultSuccessUrl("/courses",true)//After SuccessFul Login go to this URL
            .usernameParameter("username")
            .passwordParameter("password")
            .and()
            .rememberMe()//It is Remember the Session Time
            .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21)) //21 Hours
            .key("SomeStringValue")
            .rememberMeParameter("remember-me")
            .and()
            .logout().logoutUrl("/logout") //Set the Logout URL
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
            .clearAuthentication(true)  //Clear Authentication
            .invalidateHttpSession(true)  //Clear Session
            .deleteCookies("JSESSIONID","remember-me") //Delete the Cookies
            .logoutSuccessUrl("/login").and().build(); //After Logout go to this URL
}*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
               /* .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermissions())
                .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermissions())
                .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermissions())
                .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())*/
                .anyRequest().authenticated()
                .and()
               // .httpBasic(); //Basic Authentication
                .formLogin()  //Form Based Authentication
                   .loginPage("/login").permitAll() //Permit All Users in Login Page
                   .defaultSuccessUrl("/courses",true)//After SuccessFul Login go to this URL
                   .usernameParameter("username")
                   .passwordParameter("password")
                   .and()
                   .rememberMe()//It is Remember the Session Time
                      .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21)) //21 Hours
                      .key("SomeStringValue")
                      .rememberMeParameter("remember-me")
                .and()
                .logout().logoutUrl("/logout") //Set the Logout URL
                  .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
                  .clearAuthentication(true)  //Clear Authentication
                  .invalidateHttpSession(true)  //Clear Session
                  .deleteCookies("JSESSIONID","remember-me") //Delete the Cookies
                  .logoutSuccessUrl("/login"); //After Logout go to this URL
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        //Create User
        UserDetails user =
                User.builder().username("sathish")
                        .password(passwordEncoder().encode("password"))
                       // .roles(STUDENT.name())
                        .authorities(STUDENT.getGrantedAuthority())
                        .build();
        //Create ADMIN
        UserDetails admin =
                User.builder().username("kumar")
                        .password(passwordEncoder().encode("password123"))
                        //.roles(ADMIN.name())
                        .authorities(ADMIN.getGrantedAuthority())
                        .build();
        //Create admin Trainee
        UserDetails adminTrainee =
                User.builder().username("Thiyash")
                        .password(passwordEncoder().encode("password@123"))
                        //.roles(ADMINTRAINEE.name())
                        .authorities(ADMINTRAINEE.getGrantedAuthority())
                        .build();
        System.out.println(ADMIN.name());
        // Match method for Check Encode and Raw Password aer same
        System.out.println(passwordEncoder().matches("password", passwordEncoder().encode("password")));
        return new InMemoryUserDetailsManager(user, admin,adminTrainee);
    }

    //It is used to Encode the Password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}

package edigest.enotes.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@ComponentScan
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;



    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF protection if needed

                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/user/**").hasRole("USER")  // Restrict access to "/user/**" to users with "USER" role
                        .requestMatchers("/**").permitAll()  // Allow public access to all other URLs
                ).formLogin(form -> form
                .loginPage("/login")  // Custom login page
                .loginProcessingUrl("/UserLogin")  // URL to handle login form submission
                // .failureUrl("/login?error=true")  // Redirect URL on login failure
                .defaultSuccessUrl("/user/index")  // Redirect URL on successful login
                .permitAll()  // Allow all users to access the login page
        );


        return http.build();
    }




}

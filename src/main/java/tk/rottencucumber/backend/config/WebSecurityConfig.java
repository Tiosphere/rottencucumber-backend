package tk.rottencucumber.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import tk.rottencucumber.backend.authentication.MyUserDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeRequests(auth -> {
                    auth.antMatchers("/api/**", "api/user/login/", "api/user/logout/", "api/user/signup/").permitAll();
                    auth.antMatchers("/api/user/**").authenticated();
                    auth.antMatchers("/api/admin/**").hasRole("ADMIN");
                })
//                .formLogin(form -> form.loginPage("/user/login/").permitAll())
//                .logout(logout -> logout.logoutUrl("/user/logout/").permitAll())
//                .exceptionHandling().authenticationEntryPoint(new Json403EntryPoint())
//                .and()
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(32,128,4,16*1024,16);
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(new MyUserDetailService())
                .and().build();
    }
}

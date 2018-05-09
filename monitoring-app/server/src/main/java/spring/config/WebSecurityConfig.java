package spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Enable authentication with three in-memory users: UserA, UserB and UserC.
     *
     * Spring Security will provide a default login form where insert username
     * and password.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth
                // Defines three users with their passwords and roles
//                todo : add ssl support
                .inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser("UserA").password("UserA").roles("USER")
                .and()
                .withUser("UserB").password("UserB").roles("USER")
                .and()
                .withUser("UserC").password("UserC").roles("USER")
                .and()
                .withUser("1").password("1").roles("USER");
        return;
    }

    /**
     * Disable CSRF protection (to simplify this demo) and enable the default
     * login form.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection
//                todo: need form for logout
//                .csrf().disable()
                // Set default configurations from Spring Security
                .authorizeRequests()
//                .antMatchers("/", "/webjars/**", "/js/**", "/css/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
        return;
    }

} // class WebSecurityConfig

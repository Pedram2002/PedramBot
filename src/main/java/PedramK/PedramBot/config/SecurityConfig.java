package PedramK.PedramBot.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Configures in-memory authentication with two users: ADMIN and user.
     *
     * @param auth AuthenticationManagerBuilder instance.
     * @throws Exception If an error occurs during configuration.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth .inMemoryAuthentication().withUser("ADMIN")
               .password("123")
               .roles("ADMIN")
               .and() .withUser("user")
               .password("123")
               .roles("USER");

    }

    /**
     * Configures HTTP security, specifying URL access rules.
     *
     * @param http HttpSecurity instance.
     * @throws Exception If an error occurs during configuration.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
              .antMatchers("/userList").hasRole("ADMIN")
              .antMatchers("/**").permitAll().and().formLogin();
    }


    /**
     * Provides a NoOpPasswordEncoder bean for password encoding.
     *
     * @return A PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder encoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
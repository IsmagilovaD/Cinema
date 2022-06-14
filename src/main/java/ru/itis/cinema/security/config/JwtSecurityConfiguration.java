package ru.itis.cinema.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import ru.itis.cinema.repositories.BlackListRepository;
import ru.itis.cinema.security.filters.JwtTokenAuthenticationFilter;
import ru.itis.cinema.security.filters.JwtTokenAuthorizationFilter;
import ru.itis.cinema.security.filters.JwtTokenLogoutFilter;

@EnableWebSecurity
public class JwtSecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String LOGIN_FILTER_PROCESSES_URL = "/login";

    public static final String LOGOUT_FILTER_PROCESSES_URL = "/logout";

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService accountUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BlackListRepository blackListRepository;


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtTokenAuthenticationFilter authenticationFilter =
                new JwtTokenAuthenticationFilter(authenticationManagerBean(),
                        objectMapper, secretKey);

        JwtTokenAuthorizationFilter authorizationFilter =
                new JwtTokenAuthorizationFilter(objectMapper, secretKey, blackListRepository);

        authenticationFilter.setFilterProcessesUrl(LOGIN_FILTER_PROCESSES_URL);

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilter(authenticationFilter);
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtTokenLogoutFilter(blackListRepository), LogoutFilter.class);

        http.authorizeRequests()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers(LOGIN_FILTER_PROCESSES_URL + "/**").permitAll()
                .antMatchers(LOGOUT_FILTER_PROCESSES_URL+"/**").authenticated()
                .antMatchers("films/**").permitAll()
                .antMatchers("/film").hasAuthority("ADMIN")
                .antMatchers("/ticket").authenticated()
                .antMatchers("/review").authenticated();
    }

}


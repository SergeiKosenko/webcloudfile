package ru.kosenko.webcloudfile.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kosenko.webcloudfile.services.UserService;



@EnableWebSecurity // Настраиваем доступ в зависимости прав доступа
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/authenticated/**").authenticated()
                .antMatchers("/adminpanel/**").hasRole("ADMIN")
                .antMatchers("/profile/**").hasAuthority("READ_PROFILE")
                .antMatchers("/myprofile/**").authenticated()
                .and()
                .formLogin()// Спринговая страница авторизации
                //       .loginProcessingUrl("/helologin") // Своя страница авторизации
                .and()
                .logout().logoutSuccessUrl("/")
        .and()
        .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

//    @Bean
//    public JdbcUserDetailsManager users(DataSource dataSource) {
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$12$InVT3RGmR/CXw4fw3DEre.ZxuelSspR.7OnDH7jo0U/L5V8XKXJJa")
//                .roles("ADMIN","USER")
//                .build();
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2a$12$InVT3RGmR/CXw4fw3DEre.ZxuelSspR.7OnDH7jo0U/L5V8XKXJJa")
//                .roles("USER")
//                .build();
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//        if (jdbcUserDetailsManager.userExists(user.getUsername())) {
//            jdbcUserDetailsManager.deleteUser(user.getUsername()); // Если пользователь user есть, удаляем
//        }
//        if (jdbcUserDetailsManager.userExists(admin.getUsername())) {
//            jdbcUserDetailsManager.deleteUser(admin.getUsername()); // Если пользователь admin есть, удаляем
//        }
//        jdbcUserDetailsManager.createUser(user); // Создаем нового user
//        jdbcUserDetailsManager.createUser(admin); // Создаем нового admin
//        return jdbcUserDetailsManager;
//    }

}

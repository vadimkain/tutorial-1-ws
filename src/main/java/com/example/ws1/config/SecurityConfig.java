package com.example.ws1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
// Настраиваем безопасность таким образом чтобы она фактически позволяла всем подключением переходить к / и /ws/** которые являются конечными точками вс
        http
                .authorizeHttpRequests()
                .requestMatchers("/", "/ws/**")
                .permitAll()
//                Любой другой запрос должен быть авторизирован
                .and()
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
//                устанавливаем любой стандартный логин
                .formLogin()
                .and()
//                устаналиваем url-обработчик выхода из системы
                .logout(logout -> logout.logoutSuccessUrl("/"));

        return http.build();
    }

    //    устанавливаем встроенного пользователя
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("test")
                .password("test")
                .roles("USER")
                .build();

        UserDetails user2 = User.withDefaultPasswordEncoder()
                .username("test2")
                .password("test2")
                .roles("USER")
                .build();

        UserDetails user3 = User.withDefaultPasswordEncoder()
                .username("test3")
                .password("test3")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user, user2, user3);
    }
}

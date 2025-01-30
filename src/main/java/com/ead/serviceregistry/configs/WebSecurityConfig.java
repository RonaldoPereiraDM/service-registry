package com.ead.serviceregistry.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Value("${ead.serviceRegistry.username}")
    private String username;

    @Value("${ead.serviceRegistry.password}")
    private String password;


    // Define um bean Spring que configura a cadeia de filtros de segurança
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configura regras de autorização para requisições HTTP
                .authorizeHttpRequests((authorize) -> authorize
                        // Requer autenticação para qualquer requisição
                        .anyRequest().authenticated()
                )
                // Habilita autenticação HTTP Basic com configuração padrão
                .httpBasic(Customizer.withDefaults())
                // Habilita login por formulário com configuração padrão
                .formLogin(Customizer.withDefaults())
                // Desabilita proteção CSRF (Cross-Site Request Forgery)
                .csrf(AbstractHttpConfigurer::disable);
        // Constrói e retorna a cadeia de filtros configurada
        return http.build();
    }

    // Configura um serviço de detalhes do usuário em memória
    @Bean
    public UserDetailsService userDetailsService() {
        // Cria um usuário com função ADMIN e senha codificada
        UserDetails userDetails = User.withUsername(username)
                .password(passwordEncoder().encode(password))         // Codifica a senha
                .roles("ADMIN")                                      // Atribui papel ADMIN
                .build();

        System.out.println(userDetails.getPassword() + " USER NAME: " + userDetails.getUsername());
        // Retorna gerenciador em memória com o usuário criado
        return new InMemoryUserDetailsManager(userDetails);
    }

    //Cria um bean que fornece codificação de senhas usando BCrypt
    //BCrypt é um algoritmo seguro que adiciona salt e hash às senhas
    //Recomendado para uso em produção
    //Integra-se com Spring Security para criptografar senhas automaticamente
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
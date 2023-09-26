package ma.enset.hospitalapp.security;

import lombok.AllArgsConstructor;
import ma.enset.hospitalapp.security.service.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration//pour dire que c'est une classe de configuration cad une classe qui va etre chargé au demarrage de l'application
@EnableWebSecurity//pour activer la securité web
@EnableMethodSecurity(prePostEnabled = true)//pour activer la securité au niveau des methodes
@AllArgsConstructor
public class SecurityConfig {
    private PasswordEncoder passwordEncoder;

    private UserDetailServiceImp userDetailServiceImpl;
    //@Bean
 public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){//pour dire a spring security que l'authentification se fait en memoire cad en utilisant une liste d'utilisateur en memoire
        return new JdbcUserDetailsManager(dataSource);//cette methode permet de creer une liste d'utilisateur en memoire et de specifier les roles de chaque utilisateur
    }
   // @Bean//pour dire que c'est un bean cad un objet qui va etre chargé au demarrage de l'application
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){//pour dire a spring security que l'authentification se fait en memoire cad en utilisant une liste d'utilisateur en memoire
        return new InMemoryUserDetailsManager(//cette methode permet de creer une liste d'utilisateur en memoire et de specifier les roles de chaque utilisateur
                User.withUsername("user1").password(passwordEncoder.encode("1234")).authorities("USER").build(),
                User.withUsername("user2").password(passwordEncoder.encode("1234")).authorities("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("1234")).authorities("USER","ADMIN").build()
        );
    }
    @Bean//pour dire que c'est un bean cad un objet qui va etre chargé au demarrage de l'application
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll();//pour dire a spring security que l'authentification se fait par formulaire
        httpSecurity.authorizeHttpRequests().requestMatchers("/webjars/**").permitAll();//pour dire a spring security que les requetes http qui commencent par /webjars/ neccesite pas d'authentification
//        httpSecurity.rememberMe();
        httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasAuthority("USER");//pour dire a spring security que les requetes http qui commencent par /user/ neccesite un role USER
        httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasAuthority("ADMIN");//pour dire a spring security que les requetes http qui commencent par /user/ neccesite un role USER
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();//pour dire a spring security que toutes les requetes http doivent etre authentifié et neccesite une authentification
        httpSecurity.exceptionHandling().accessDeniedPage("/notAuthorized");//pour dire a spring security que si une requete http n'est pas autorisé alors il faut rediriger vers la page /notAuthorized
        httpSecurity.userDetailsService(userDetailServiceImpl);
        return httpSecurity.build();


    }

}

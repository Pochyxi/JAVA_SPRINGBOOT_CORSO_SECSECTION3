package com.developez.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


/* @Configuration, indica che è una classe di configurazione per l'applicazione Spring.
 */
@Configuration
public class ProjectSecurityConfig {

    /*
    * Il metodo defaultSecurityFilterChain è annotato con @Bean e restituisce un oggetto SecurityFilterChain.
    * Questo metodo configura le regole di autorizzazione per le richieste HTTP.
    * In particolare, specifica che le richieste agli endpoint ("/myAccount", "myLoans", "myBalance", "myCards") devono
    * essere autenticate, mentre le richieste agli endpoint ("notices", "contact") devono essere accessibili a tutti
    * senza autenticazione. Viene utilizzato il metodo http.authorizeHttpRequests() per definire queste regole.
    *
    * Il metodo defaultSecurityFilterChain configura anche la gestione del login tramite form (http.formLogin()) e
    * la gestione delle richieste HTTP di base (http.httpBasic()).
    * Infine, viene restituito l'oggetto http costruito tramite il metodo build().
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain( HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/myAccount", "myLoans", "myBalance",
                "myCards").authenticated()
                .requestMatchers( "notices", "contact" ).permitAll());

        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }


    /*
    * Il metodo userDetailsManager è annotato con @Bean e restituisce un oggetto InMemoryUserDetailsManager. Questo
    * metodo definisce gli utenti che saranno gestiti in memoria. Vengono creati due oggetti UserDetails:
    * uno per l'utente "admin" e uno per l'utente "user". Per il metodo 2, viene utilizzato User.withUsername()
    * per specificare il nome utente e la password degli utenti e roles() per specificare i ruoli degli utenti.
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        // METODO 1
        // NON RACCOMANDATO PER PRODUZIONE
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("12345")
//                .roles("admin")
//                .build();
//
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("12345")
//                .roles("read")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);

        // METODO 2 - NON RACCOMANDATO PER PRODUZIONE
        UserDetails admin = User.withUsername("admin")
                .password("12345")
                .roles("admin")
                .build();

        UserDetails user = User.withUsername("user")
                .password("12345")
                .roles("read")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    /*
    * passwordEncoder restituisce un oggetto PasswordEncoder. In questo caso, viene utilizzato
    * NoOpPasswordEncoder.getInstance() che è un'implementazione di PasswordEncoder che non codifica la password.
    * Si noti che questa implementazione non è raccomandata per la produzione e dovrebbe essere sostituita con un
    * PasswordEncoder sicuro.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}

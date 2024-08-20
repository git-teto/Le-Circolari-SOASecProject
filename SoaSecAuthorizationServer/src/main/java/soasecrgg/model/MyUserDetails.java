/*
 * La classe MyUserDetails implementa l’interfaccia UserDetailsService di Spring Security,
 * che è un meccanismo centrale nell’infrastruttura di sicurezza di Spring. 
 */
package soasecrgg.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import soasecrgg.entity.User;
import soasecrgg.repository.UserRepository;

@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Metodo fondamentale nel processo di autenticazione con Spring Security. Questo metodo ha la responsabilità di recuperare
     * le informazioni di un utente dal database, dato il suo nome utente (in questo caso, l'email).
     * Spring Security invoca questo metodo durante il processo di autenticazione per recuperare i dati utente e verificarne
     * le credenziali.
     * @param email parametro email da cercare
     * @return oggetto UserDetails (una classe di Spring Security che rappresenta un utente autenticato) e lo restituisce.
     * L'oggetto contiene i dati necessari per l'autenticazione: l'email dell'utente', la password e
     * le autorithies (ruoli) dell'utente.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Utente non trovato con email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(List.of(user.getTipo())));
    }

    /**
     * Questo metodo converte una lista di stringhe che rappresentano ruoli in una
     * collezione di oggetti GrantedAuthority utilizzabili da Spring Security per la gestione delle autorizzazioni.
     * @param roles Lista di stringhe come argomento. Ogni stringa rappresenta un ruolo (autorità) dell'utente.
     * @return La lista authorities che contiene tutti gli oggetti GrantedAuthority per i ruoli dell'utente
     */
    private static Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
/*
 * Questa classe è utilizzata per caricare i dettagli dell’utente dal database e convertire i ruoli dell’utente in autorità di Spring Security.
 * Queste informazioni vengono poi utilizzate da Spring Security per l’autenticazione e l’autorizzazione.
 */
package soasecrgg.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import soasecrgg.entity.User;
import soasecrgg.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    
    /*
     * Questo è un metodo sovrascritto dall’interfaccia UserDetailsService. Viene utilizzato per caricare i dettagli dell’utente in base al nome utente,
     * che in questo caso è l’email. Se l’utente non viene trovato, viene lanciata un’eccezione UsernameNotFoundException.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user==null) {
            throw new UsernameNotFoundException("Utente non trovato con email: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(List.of(user.getTipo())));
    }

    /*
     * Questo è un metodo privato che converte una lista di ruoli in una collezione di GrantedAuthority, che rappresenta le autorità concesse all’utente.
     */
    private static Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}


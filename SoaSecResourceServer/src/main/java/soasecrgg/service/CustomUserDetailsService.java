package soasecrgg.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import soasecrgg.entity.User;
import soasecrgg.repository.IUtenteRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUtenteRepository userRepository;

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
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user));
    }

    /**
     * Questo metodo recupera le autorità dell'utente leggendo il campo tipo e aggiungendo il prefisso "ROLE_".
     * Successivamente, converte il ruolo in un oggetto GrantedAuthority e lo inserisce in una collezione che viene restituita.
     * @param user Oggetto User
     * @return Collezione authorities che contiene le autorità dell'utente.
     */
    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String userRole = "ROLE_" + user.getTipo().toUpperCase();
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRole);
        return authorities;
    }
    
    
    

}

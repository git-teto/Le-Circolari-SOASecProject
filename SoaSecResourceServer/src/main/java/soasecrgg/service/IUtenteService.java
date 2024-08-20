package soasecrgg.service;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import soasecrgg.entity.User;

/**
 * Interfaccia per fornire le funzionalità di gestione degli utenti.
 * L'utilizzo di un'interfaccia garantisce la separazione
 * tra la definizione delle operazioni e la loro implementazione concreta.
 */
public interface IUtenteService {

    public Iterable<User> getAll();

    public Optional<User> getById(int id);
    
    public String getNomeUtente(Authentication authentication);

    public User create(User utente);

    public Optional<User> update(int id, @RequestBody User utente);

    public Boolean delete(int id);
    
    

}

package soasecrgg.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import soasecrgg.entity.User;
import soasecrgg.repository.IUtenteRepository;

@Service("mainUtenteService")
public class DbUtenteService implements IUtenteService {

    @Autowired
    private IUtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     *Questo metodo fornisce un modo semplice per recuperare tutti gli utenti presenti nel database.
     * Si appoggia al repository degli utenti per effettuare la ricerca vera e propria, evitando la scrittura manuale di codice SQL.
     * @return Iterable</User> Collezione di utenti recuperata dal repository.
     */
    @Override
    public Iterable<User> getAll(){

        return utenteRepository.findAll();
    }

    /**
     *Questo metodo permette di recuperare uno specifico utente dal database tramite il suo ID.
     * Optional<User> consente di gestire in modo sicuro il caso in cui l'utente cercato non sia presente
     * nel database, evitando potenziali errori null pointer.
     * @param id identificativo utente
     * @return metodo restituisce l'oggetto Optional<User> ottenuto dal repository.
     * Se l'utente con l'ID specificato viene trovata, l'oggetto Optional conterrà l'utente'.
     * Se l'utente non viene trovata, l'oggetto Optional sarà vuoto.
     */
    @Override
    public Optional<User> getById(int id) {

        return utenteRepository.findById(id);
    }

    /**
     *Questo metodo permette di creare un nuovo utente nel database. Il metodo delega la responsabilità al repository
     * e restituisce l'oggetto User salvato.
     * @param utente Oggetto User da creare
     * @return Oggetto User salvato nel db
     */
    @Override
    public User create(User utente) {
        String encryptedPassword = passwordEncoder.encode(utente.getPassword());
        utente.setPassword(encryptedPassword);
        return utenteRepository.save(utente);
    }

    /**
     *Questo metodo permette di aggiornare i dati di un utente esistente nel database.
     * Optional<User> consente di gestire in modo sicuro il caso in cui l'utente cercato non sia presente
     * nel database, evitando potenziali errori null pointer.
     * Il metodo controlla prima se l'utente con l'ID specificato esiste, aggiorna i suoi dati e salva le modifiche.
     * Infine, restituisce l'oggetto User aggiornato.
     * @param id identificativo utente
     * @param utente Oggetto User con i dati aggiornati nel body della richiesta
     * @return Optional<User> che contiene l'utente aggiornato o nulla se l'utente non esiste.
     */
    @Override
    public Optional<User> update(int id, @RequestBody User utente) {
        Optional<User> trovaUtente = utenteRepository.findById(id);
        if(trovaUtente.isEmpty()) {
            return Optional.empty();
        }
        trovaUtente.get().setNome(utente.getNome());
        trovaUtente.get().setCognome(utente.getCognome());
        trovaUtente.get().setEmail(utente.getEmail());
        String encryptedPassword = passwordEncoder.encode(utente.getPassword());
        trovaUtente.get().setPassword(encryptedPassword);
        trovaUtente.get().setTipo(utente.getTipo());
        utenteRepository.save(trovaUtente.get());
        return trovaUtente;
    }

    /**
     *Questo metodo permette di eliminare un utente esistente nel database.
     * Il metodo controlla prima se l'utente con l'ID specificato esiste, se esiste lo elimina
     * e infine restituisce un valore booleano che indica se l'eliminazione è avvenuta con successo.
     * @param id identificativo utente da eliminare
     * @return valore booleano (true o false): true se l'utente è stato eliminato, false altrimenti.
     */
    @Override
    public Boolean delete(int id) {

        Optional<User> trovaUtente = utenteRepository.findById(id);

        if(trovaUtente.isEmpty()) {
            return false;
        }

        utenteRepository.delete(trovaUtente.get());

        return true;

    }

	@Override
	public String getNomeUtente(Authentication authentication) {
		return authentication.getName();
	}


}

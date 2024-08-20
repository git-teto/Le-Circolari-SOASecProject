package soasecrgg.controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import soasecrgg.entity.User;
import soasecrgg.repository.IUtenteRepository;
import soasecrgg.service.IUtenteService;

@RestController
public class AmministratoreUtenteController {

    @Autowired
    @Qualifier("mainUtenteService")
    private IUtenteService utenteService;
    @Autowired
    private IUtenteRepository utenteRepository;

    public AmministratoreUtenteController() {}

    /**
     * Metodo che fornisce un endpoint API agli amministratori per accedere ai dati utente.
     * Quando un amministratore invia una richiesta GET a /amministratore/api/utente, questo metodo viene invocato,
     * recupera tutti gli utenti tramite utenteService e li restituisce come collezione.
     * @return Iterable</User> collezione di utenti
     */
    @RequestMapping("/amministratore/api/utente")
    public Iterable<User> getAll(){

        return utenteService.getAll();
    }

    /**
     *Questo metodo permette agli amministratori di recuperare un singolo utente in base al suo ID tramite un'API.
     * L'URL deve seguire il formato /amministratore/api/utente/{id} dove {id} è l'ID numerico dell'utente.
     * Se l'utente viene trovato, viene restituito come oggetto User.
     * Se l'utente non viene trovato, viene lanciata un'eccezione con lo stato HTTP 404.
     * @param id identificativo utente
     * @return Oggetto User trovato in base all'id
     */
    @RequestMapping("/amministratore/api/utente/{id}")
    public User getById(@PathVariable int id) {

        Optional<User> utente = utenteService.getById(id);

        if (utente.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
        }

        return utente.get();
    }

    /**
     *Questo metodo permette agli amministratori di creare un nuovo utente inviando una richiesta POST
     * all'endpoint /amministratore/api/utente con i dati dell'utente nel corpo della richiesta.
     * Il controller riceve i dati, li converte in un oggetto User, e delega la creazione vera e propria al servizio
     * utenteService. Se la creazione ha successo, il metodo restituisce l'oggetto User appena creato.
     * @param utente Oggetto utente da creare
     * @return User Utente creato
     */
    @RequestMapping(value = "/amministratore/api/utente", method = RequestMethod.POST)
    public User create(@RequestBody User utente) {

        return utenteService.create(utente);
    }

    /**
     *Questo metodo permette agli amministratori di aggiornare un utente esistente inviando una richiesta PUT
     * all'endpoint /amministratore/api/utente/{id} con i dati aggiornati dell'utente nel corpo della richiesta.
     * Il controller riceve l'ID dell'utente da aggiornare e i dati aggiornati, li inoltra al servizio utenteService
     * per effettuare l'aggiornamento, e restituisce l'oggetto utente aggiornato se l'operazione ha avuto successo.
     * In caso di utente non trovato, viene lanciata un'eccezione con lo stato HTTP 404.
     * @param id id utente da aggiornare
     * @param utente Oggetto utente con dati aggiornati
     * @return User Utente aggiornato
     */
    @RequestMapping(value = "/amministratore/api/utente/{id}", method = RequestMethod.PUT)
    public User update(@PathVariable int id, @RequestBody User utente) {
        Optional<User> utenteAggiornata = utenteService.update(id, utente);

        if (utenteAggiornata.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
        }

        return utenteAggiornata.get();
    }

    /**
     *Questo metodo permette agli amministratori di eliminare un utente esistente inviando una richiesta
     * DELETE all'endpoint /amministratore/api/utente/{id}. Il controller riceve l'ID dell'utente da eliminare,
     * lo inoltra al servizio utenteService per effettuare l'eliminazione, e controlla l'esito dell'operazione.
     * In caso di eliminazione riuscita, il metodo non restituisce nulla. Se l'utente non viene trovato,
     * viene lanciata un'eccezione con lo stato HTTP 404.
     * @param id id utente da eliminare
     */
    @RequestMapping(value = "/amministratore/api/utente/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        Boolean eliminata = utenteService.delete(id);

        if (eliminata == false) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
        }
    }

    /**
     * Questo metodo recupera l'email dall'utente autenticato, utilizza l'email per trovare
     * l'oggetto utente completo nel database e infine restituisce il nome completo dell'utente concatenando nome e cognome.
     * @param authentication contiene informazioni sull'utente attualmente connesso.
     * @return Costruisce e restituisce il nome completo concatenando nome e cognome con uno spazio.
     */
    @RequestMapping("/amministratore/api/nomeUtente")
    public String getNomeUtente(Authentication authentication) {
        String email = authentication.getName();
        User user = utenteRepository.findByEmail(email);
        String nome = user.getNome();
        String cognome = user.getCognome();
        return nome + ' ' + cognome;
    }

    /**
     * Questo metodo recupera l'email dall'utente autenticato, utilizza l'email per trovare
     * l'oggetto utente completo nel database e infine restituisce un oggetto user che sarà poi impiegato per effettuare la
     * modifica della password.
     * @param authentication contiene informazioni sull'utente attualmente connesso.
     * @return Oggetto user con tutte le informazioni relative all'utente autenticato.
     */
    @RequestMapping("/amministratore/api/utente/cambiaPassword")
    public User getAllUtente(Authentication authentication){
    	String email = authentication.getName();
        User user = utenteRepository.findByEmail(email);		
        return user;
    }


}

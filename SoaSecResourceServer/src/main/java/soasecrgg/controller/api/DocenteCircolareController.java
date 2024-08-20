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

import soasecrgg.entity.Circolare;
import soasecrgg.entity.User;
import soasecrgg.repository.IUtenteRepository;
import soasecrgg.service.ICircolareService;
import soasecrgg.service.IUtenteService;

@RestController
public class DocenteCircolareController {
    @Autowired
    @Qualifier("mainCircolareService")
    private ICircolareService circolareService;
    @Autowired
    private IUtenteRepository utenteRepository;
    
    @Autowired
    @Qualifier("mainUtenteService")
    private IUtenteService utenteService;

    public DocenteCircolareController() {}

    /**
     *Il metodo getAll permette ai docenti di recuperare tutte le circolari che li riguardano tramite un'API.
     * La richiesta viene inviata all'endpoint /docente/api/circolare e il metodo restituisce una collezione di oggetti
     * Circolare.
     * @return Iterable</Circolare> collezione di oggetti Circolare rivolte ai docenti
     */
    @RequestMapping("/docente/api/circolare")
    public Iterable<Circolare> getAll(){

        return circolareService.getAll();
    }

    /**
     *Questo metodo permette ai docenti di recuperare una circolare specifica tramite il suo ID.
     * La richiesta viene inviata all'endpoint /docente/api/circolare/{id}, dove {id} è l'ID numerico della circolare.
     * Se la circolare viene trovata, viene restituita come oggetto Circolare.
     * Se la circolare non viene trovata, viene lanciata un'eccezione con lo stato HTTP 404.
     * @param id identificativo circolare
     * @return Oggetto circolare trovato in base all'id
     */
    @RequestMapping("/docente/api/circolare/{id}")
    public Circolare getById(@PathVariable int id) {

        Optional<Circolare> circolare = circolareService.getById(id);

        if (circolare.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
        }

        return circolare.get();
    }

    /**
     * Questo metodo recupera l'email dall'utente autenticato, utilizza l'email per trovare
     * l'oggetto utente completo nel database e infine restituisce il nome completo dell'utente concatenando nome e cognome.
     * @param authentication contiene informazioni sull'utente attualmente connesso.
     * @return Costruisce e restituisce il nome completo concatenando nome e cognome con uno spazio.
     */
    @RequestMapping("/docente/api/nomeUtente")
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
    @RequestMapping("/docente/api/utente")
    public User getAllUtente(Authentication authentication){
    	String email = authentication.getName();
        User user = utenteRepository.findByEmail(email);		
        return user;
    }

    /**
     *Questo metodo permette ai docenti di modificare la propria password inviando una richiesta PUT
     * all'endpoint /docente/api/utente/{id} con la password aggiornata e gli altri campi invariati nel corpo della richiesta.
     * Il controller riceve l'ID dell'utente da aggiornare e i dati, li inoltra al servizio utenteService
     * per effettuare l'aggiornamento, e restituisce l'oggetto utente aggiornato se l'operazione di cambio password ha avuto successo.
     * In caso di utente non trovato, viene lanciata un'eccezione con lo stato HTTP 404.
     * @param id id utente da aggiornare
     * @param utente Oggetto utente con password aggiornata e altri dati invariati
     * @return User Utente con password aggiornata
     */
    @RequestMapping(value = "/docente/api/utente/{id}", method = RequestMethod.PUT)
    public User update(@PathVariable int id, @RequestBody User utente) {
        Optional<User> utenteAggiornata = utenteService.update(id, utente);

        if (utenteAggiornata.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
        }

        return utenteAggiornata.get();
    }


}

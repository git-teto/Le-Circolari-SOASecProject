/*
package soasecrgg.controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import soasecrgg.entity.User;
import soasecrgg.service.IUtenteService;

@RestController
public class UtenteController {

    @Autowired
    @Qualifier("mainUtenteService")
    private IUtenteService utenteService;

    public UtenteController() {}

    /**
     * Metodo che fornisce un endpoint API per accedere ai dati utente.
     * Invia una richiesta GET a /api/utente, questo metodo viene invocato,
     * recupera tutti gli utenti tramite utenteService e li restituisce come collezione.
     * @return Iterable</User> collezione di utenti
     */
/*
    @RequestMapping("/api/utente")
    public Iterable<User> getAll(){ //Stampa le circolari

        return utenteService.getAll();
    }

    /**
     *Questo metodo permette adi recuperare un singolo utente in base al suo ID tramite un'API.
     * L'URL deve seguire il formato /api/utente/{id} dove {id} è l'ID numerico dell'utente.
     * Se l'utente viene trovato, viene restituito come oggetto User.
     * Se l'utente non viene trovato, viene lanciata un'eccezione con lo stato HTTP 404.
     * @param id identificativo utente
     * @return Oggetto User trovato in base all'id
     */
/*
    @RequestMapping("/api/utente/{id}")
    public User getById(@PathVariable int id) {

        Optional<User> utente = utenteService.getById(id);

        if (utente.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
        }

        return utente.get();
    }

}
*/
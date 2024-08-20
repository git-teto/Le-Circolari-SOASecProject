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

import soasecrgg.entity.Circolare;
import soasecrgg.service.ICircolareService;

@RestController
public class CircolareController {

    @Autowired
    @Qualifier("mainCircolareService")
    private ICircolareService circolareService;

    public CircolareController() {}

    /**
     *Il metodo getAll permette di recuperare tutte le circolari disponibili tramite un'API.
     * La richiesta viene inviata all'endpoint /api/circolare e il metodo restituisce una collezione di oggetti Circolare.
     * @return Iterable</Circolare> collezione di circolari
     */
/*
    @RequestMapping("/api/circolare")
    public Iterable<Circolare> getAll(){
        return circolareService.getAll();
    }

    /*

    /**
     *Questo metodo permette di recuperare una circolare specifica tramite il suo ID.
     * La richiesta viene inviata all'endpoint /api/circolare/{id}, dove {id} è l'ID numerico della circolare.
     * Se la circolare viene trovata, viene restituita come oggetto Circolare.
     * Se la circolare non viene trovata, viene lanciata un'eccezione con lo stato HTTP 404.
     * @param id identificativo circolare
     * @return Oggetto circolare trovato in base all'id
     */
/*
    @RequestMapping("/api/circolare/{id}")
    public Circolare getById(@PathVariable int id) {

        Optional<Circolare> circolare = circolareService.getById(id);

        if (circolare.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
        }

        return circolare.get();
    }

}
*/

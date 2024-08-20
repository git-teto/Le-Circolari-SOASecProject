package soasecrgg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Questo controller definisce una semplice mappatura per l'URL /docente.
 * Quando un utente accede a questo URL, viene chiamato il metodo docente e
 * restituisce il nome di un modello di vista associato al ruolo di "docente", cioè docente.html
 * che si trova nella cartella resources/templates che sarà responsabile per la visualizzazione del contenuto specifico
 * dell'interfaccia utente del docente.
 */
@Controller
public class DocenteController {

    @RequestMapping("/docente")
    public String docente() {
        return "docente";
    }

}

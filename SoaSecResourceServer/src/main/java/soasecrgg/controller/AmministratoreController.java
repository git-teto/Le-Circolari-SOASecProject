package soasecrgg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Questo controller definisce una semplice mappatura per l'URL /amministratore.
 * Quando un utente accede a questo URL, viene chiamato il metodo amministratore e
 * restituisce il nome di un modello di vista associato al ruolo di "amministratore", cioè amministratore.html
 * che si trova nella cartella resources/templates che sarà responsabile per la visualizzazione del contenuto specifico
 * dell'interfaccia utente dell'amministratore.
 */
@Controller
public class AmministratoreController {

    @RequestMapping("/amministratore")
    public String amministratore() {
        return "amministratore";
    }

}

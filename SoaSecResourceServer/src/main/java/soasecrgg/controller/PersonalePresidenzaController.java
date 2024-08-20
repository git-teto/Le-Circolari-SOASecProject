package soasecrgg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Questo controller definisce una semplice mappatura per l'URL /personale_presidenza.
 * Quando un utente accede a questo URL, viene chiamato il metodo personalePresidenza e
 * restituisce il nome di un modello di vista associato al ruolo di "presidenza", cioè personale_presidenza.html
 * che si trova nella cartella resources/templates che sarà responsabile per la visualizzazione del contenuto specifico
 * dell'interfaccia utente del personale di presidenza.
 */
@Controller
public class PersonalePresidenzaController {

    @RequestMapping("/personale_presidenza")
    public String personalePresidenza() {
        return "personale_presidenza";
    }

}

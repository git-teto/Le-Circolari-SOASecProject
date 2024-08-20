package soasecrgg.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Questo controller definisce una semplice mappatura per l'URL /studente.
 * Quando un utente accede a questo URL, viene chiamato il metodo studente e
 * restituisce il nome di un modello di vista associato al ruolo di "studente", cioè studente.html
 * che si trova nella cartella resources/templates che sarà responsabile per la visualizzazione del contenuto specifico
 * dell'interfaccia utente dello studente.
 */
@Controller
public class StudenteController {
	
	
    @RequestMapping("/studente")
    public String studente(Authentication authentication) {
    	return "studente";
    }

}

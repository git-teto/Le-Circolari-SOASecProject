package soasecrgg.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class MySimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     *Questo metodo gestisce l'operazione di redirect dopo un login riuscito con Spring Security.
     * Determina l'URL di destinazione in base all'utente autenticato e reindirizza l'utente a tale URL,
     * a meno che la risposta HTTP non sia già stata inviata.
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP in arrivo.
     * @param response Oggetto HttpServletResponse che permette di inviare la risposta HTTP.
     * @param authentication Oggetto Authentication che contiene le informazioni sull'utente.
     * @throws IOException Indica che il metodo potrebbe lanciare un'eccezione IOException in caso di problemi durante la ridirezione.
     */
    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     *Questo metodo implementa una logica di redirect basata sulle autorità dell'utente.
     * A seconda del ruolo dell'utente autenticato (studente, amministratore, presidenza o docente),
     * il metodo stabilisce la pagina di destinazione appropriata ("/studente", "/amministratore", "/personale_presidenza",
     * o "/docente").
     * @param authentication Oggetto Authentication che contiene le informazioni sull'utente.
     * @return il metodo restituisce la stringa url che contiene l'URL di destinazione determinata in base all'autorità dell'utente.
     */
    protected String determineTargetUrl(Authentication authentication) {
        String url = "";

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (authorityName.equals("studente")) {
                url = "/studente";
                break;
            } else if (authorityName.equals("amministratore")) {
                url = "/amministratore";
                break;
            } else if (authorityName.equals("presidenza")) {
                url = "/personale_presidenza";
                break;
            } else if (authorityName.equals("docente")) {
                url = "/docente";
                break;
            }
        }

        return url;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
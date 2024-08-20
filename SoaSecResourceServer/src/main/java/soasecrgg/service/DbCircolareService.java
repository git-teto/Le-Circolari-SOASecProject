package soasecrgg.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import soasecrgg.entity.Circolare;
import soasecrgg.repository.ICircolareRepository;

@Service("mainCircolareService")
public class DbCircolareService implements ICircolareService {

    @Autowired
    private ICircolareRepository circolareRepository;

    /**
     *Questo metodo fornisce un modo semplice per recuperare tutte le circolari presenti nel database.
     * Si appoggia al repository delle circolari per effettuare la ricerca vera e propria, evitando la scrittura manuale di codice SQL.
     * @return Iterable</Circolare> Collezione di circolari recuperata dal repository.
     */
    @Override
    public Iterable<Circolare> getAll(){

        return circolareRepository.findAll();
    }

    /**
     *Questo metodo permette di recuperare una specifica circolare dal database tramite il suo ID.
     * Optional<Circolare> consente di gestire in modo sicuro il caso in cui la circolare cercata non sia presente
     * nel database, evitando potenziali errori null pointer.
     * @param id identificativo circolare
     * @return metodo restituisce l'oggetto Optional<Circolare> ottenuto dal repository.
     * Se la circolare con l'ID specificato viene trovata, l'oggetto Optional conterrà la circolare.
     * Se la circolare non viene trovata, l'oggetto Optional sarà vuoto.
     */
    @Override
    public Optional<Circolare> getById(int id) {

        return circolareRepository.findById(id);
    }

    /**
     *Questo metodo permette di creare una nuova circolare nel database. Il metodo delega la responsabilità al repository
     * e restituisce l'oggetto Circolare salvato.
     * @param circolare Oggetto Circolare da creare
     * @return Oggetto Circolare salvato nel db
     */
    @Override
    public Circolare create(Circolare circolare) {

        return circolareRepository.save(circolare);
    }

    /**
     *Questo metodo permette di aggiornare i dati di una circolare esistente nel database.
     * Optional<Circolare> consente di gestire in modo sicuro il caso in cui la circolare cercata non sia presente
     * nel database, evitando potenziali errori null pointer.
     * Il metodo controlla prima se la circolare con l'ID specificato esiste, aggiorna i suoi dati e salva le modifiche.
     * Infine, restituisce l'oggetto Circolare aggiornato.
     * @param id identificativo circolare
     * @param circolare Oggetto Circolare con i dati aggiornati nel body della richiesta
     * @return Optional<Circolare> che contiene la circolare aggiornata o nulla se la circolare non esiste.
     */
    @Override
    public Optional<Circolare> update(int id, @RequestBody Circolare circolare) {

        Optional<Circolare> trovaCircolare = circolareRepository.findById(id);

        if(trovaCircolare.isEmpty()) {
            return Optional.empty();
        }

        trovaCircolare.get().setTitolo(circolare.getTitolo());
        trovaCircolare.get().setDescrizione(circolare.getDescrizione());
        trovaCircolare.get().setData(circolare.getData());
        trovaCircolare.get().setTipo(circolare.getTipo());

        circolareRepository.save(trovaCircolare.get());

        return trovaCircolare;
    }

    /**
     *Questo metodo permette di eliminare una circolare esistente nel database.
     * Il metodo controlla prima se la circolare con l'ID specificato esiste, se esiste la elimina
     * e infine restituisce un valore booleano che indica se l'eliminazione è avvenuta con successo.
     * @param id identificativo circolare da eliminare
     * @return valore booleano (true o false): true se la circolare è stata eliminata, false altrimenti.
     */
    @Override
    public Boolean delete(int id) {

        Optional<Circolare> trovaCircolare = circolareRepository.findById(id);

        if(trovaCircolare.isEmpty()) {
            return false;
        }

        circolareRepository.delete(trovaCircolare.get());

        return true;

    }

}

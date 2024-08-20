package soasecrgg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import soasecrgg.entity.User;

/**
 * Interfaccia che fa da punto di accesso per interagire con l'entità User nel database.
 * Tramite l'estensione di CrudRepository, fornisce metodi generici per le operazioni CRUD(Create, Read, Update e Delete) sugli utenti.
 * Inoltre, il metodo findByEmail(String email) permette di effettuare ricerche per email in modo più semplice.
 */
@Repository
public interface IUtenteRepository extends CrudRepository<User, Integer>{
    User findByEmail(String email);

}

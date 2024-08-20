/*
 * UserRepository fornisce un modo per interagire con la tabella
 * degli utenti nel database. Puoi utilizzare UserRepository per trovare
 * un utente per email, oltre a tutte le operazioni CRUD fornite da JpaRepository.
 */
package soasecrgg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.CrudRepository;
import soasecrgg.entity.User;

/**
 * Interfaccia che fa da punto di accesso per interagire con l'entità User nel database.
 * Tramite l'estensione di CrudRepository, fornisce metodi generici per le operazioni CRUD(Create, Read, Update e Delete) sugli utenti.
 * Inoltre, il metodo findByEmail(String email) permette di effettuare ricerche per email in modo più semplice.
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);
}

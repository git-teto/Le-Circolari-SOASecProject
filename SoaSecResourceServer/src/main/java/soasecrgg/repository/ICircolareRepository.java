package soasecrgg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import soasecrgg.entity.Circolare;

/**
 * Interfaccia che fa da punto di accesso per interagire con l'entità Circolare nel database. Tramite l'estensione di CrudRepository,
 * fornisce metodi generici per le operazioni CRUD(Create, Read, Update e Delete) sulle circolari, semplificando la gestione dei dati.
 */
@Repository
public interface ICircolareRepository extends CrudRepository<Circolare, Integer>{

}

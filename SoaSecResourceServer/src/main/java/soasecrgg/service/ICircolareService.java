package soasecrgg.service;

import java.util.Optional;

import org.springframework.web.bind.annotation.RequestBody;

import soasecrgg.entity.Circolare;

/**
 * Interfaccia per fornire le funzionalità di gestione delle circolari.
 * L'utilizzo di un'interfaccia garantisce la separazione
 * tra la definizione delle operazioni e la loro implementazione concreta.
 */
public interface ICircolareService {

    public Iterable<Circolare> getAll();

    public Optional<Circolare> getById(int id);

    public Circolare create(Circolare circolare);

    public Optional<Circolare> update(int id, @RequestBody Circolare circolare);

    public Boolean delete(int id);

}

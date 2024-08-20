package soasecrgg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * La classe Circolare modella un oggetto circolare con i suoi attributi principali.
 * L'annotazione @Entity indica che si tratta di un'entità che viene mappata alla tabella del database denominata "Circolare"
 * per la persistenza dei dati.
 * Le altre annotazioni semplificano la gestione della classe e garantiscono l'integrità dei dati.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CIRCOLARE")
public class Circolare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(unique = true)
    private String titolo;
    @NonNull
    private String descrizione;
    private String data;
    private String tipo;
}

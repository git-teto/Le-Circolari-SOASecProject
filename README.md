# Progetto SOASec - Le Circolari

## Autori
- **Simone Galimberti**
- **Stefano Giardina**
- **Mattia Ruo**

## Indice
1. [Introduzione](#introduzione)
2. [Metodologie di sviluppo e strumenti utilizzati](#metodologie-di-sviluppo-e-strumenti-utilizzati)
3. [Authorization Server](#authorization-server)
   - [Configurazione della Sicurezza](#configurazione-della-sicurezza)
   - [Gestione dei Token JWT](#gestione-dei-token-jwt)
4. [Resource Server](#resource-server)
   - [Configurazione della Sicurezza](#configurazione-della-sicurezza-resource-server)
   - [API Implementate](#api-implementate)
5. [Client](#client)
   - [Templates HTML](#templates-html)
6. [Conclusione](#conclusione)

## Introduzione

Il progetto SOASec mira a implementare un'applicazione che utilizzi il protocollo OAuth2.0 per gestire l'autenticazione e l'autorizzazione all'accesso delle risorse protette, basandosi sul ruolo dell'utente. L'applicazione è progettata per consentire la gestione delle circolari scolastiche, con funzionalità diverse a seconda del ruolo assegnato agli utenti.

Gli utenti del sistema sono suddivisi in quattro ruoli principali:
- **Amministratore**: può registrare nuovi utenti, visualizzare l'elenco completo degli utenti registrati, modificarli o eliminarli.
- **Personale Presidenza**: può caricare nuove circolari e visualizzare, modificare o eliminare quelle esistenti.
- **Studente e Docente**: possono visualizzare le circolari pertinenti al loro ruolo.

## Metodologie di sviluppo e strumenti utilizzati

Per lo sviluppo dell'applicazione sono stati utilizzati i seguenti strumenti e metodologie:

- **Spring Framework**: un framework open-source utilizzato per semplificare lo sviluppo di applicazioni Java, in particolare quelle web. Le componenti principali utilizzate sono:
  - **Spring Boot**: per avviare rapidamente l'applicazione con configurazione minima.
  - **Spring Data**: per l'interazione semplificata con il database.
  - **Spring Security**: per la gestione della sicurezza dell'applicazione.
  
- **MySQL**: un DBMS basato su SQL. Abbiamo utilizzato `phpMyAdmin` per amministrare e progettare i database in maniera semplice.

- **REST API**: le API sono esposte tramite endpoint HTTP, utilizzando metodi standard come GET, POST, PUT e DELETE per interagire con le risorse.

## Authorization Server

Nel contesto di OAuth 2.0, l'Authorization Server svolge un ruolo cruciale, fungendo da intermediario sicuro tra l'utente, l'applicazione client e il Resource Server. Esso gestisce l'emissione dei token di accesso che autorizzano i client a interagire con le risorse protette.

### Configurazione della Sicurezza

Il server di autorizzazione è configurato per ascoltare sulla porta `9000`. Le principali configurazioni di sicurezza includono:

- **Autenticazione OAuth2**: Protezione delle risorse tramite l'autenticazione OAuth 2.0 e l'uso di token JWT. Questo assicura che solo gli utenti autenticati possano accedere alle risorse.
  
- **Form Login**: È abilitata l'autenticazione tramite form login. Tutte le richieste devono provenire da utenti autenticati, aggiungendo un livello di sicurezza extra rispetto alla semplice autenticazione tramite token.

- **Registrazione Client OAuth2.0**: È stato implementato un `RegisteredClientRepository` che memorizza i dettagli dei client OAuth2, inclusi ID, segreto del client, URI di reindirizzamento e altre impostazioni di sicurezza.

### Gestione dei Token JWT

I token JWT (JSON Web Token) sono fondamentali per il sistema di autenticazione. I token sono composti da tre parti:
1. **Header**: Contiene il tipo di token e l'algoritmo di firma.
2. **Payload**: Contiene le informazioni dell'utente, come ID, ruoli e altre autorizzazioni.
3. **Signature**: È il risultato di una funzione di hash (SHA-256) applicata all'header e al payload, utilizzando una chiave privata RSA.

Il token è utilizzato dal client per autenticarsi al Resource Server. Ad esempio, il metodo `tokenCustomizer` aggiunge rivendicazioni personalizzate come l'indirizzo email e il ruolo dell'utente nel token JWT.

## Resource Server

Il Resource Server è responsabile della protezione delle risorse dell'utente. Esso garantisce che solo gli utenti autorizzati possano accedere ai dati richiesti.

Il Resource Server è configurato per essere in ascolto sulla porta `8080`.

### Configurazione della Sicurezza (Resource Server)

- **Classe `securityConfig`**: Configura la sicurezza del Resource Server, inclusa la gestione del login e del logout degli utenti. Il gestore di successo (`successHandler`) redirige gli utenti autenticati alla pagina corretta, in base al loro ruolo.

### API Implementate

Sono state implementate diverse API per gestire le risorse, a seconda del ruolo dell'utente:
- **Amministratore**: API per gestire gli utenti registrati.
- **Presidenza**: API per l'upload e la gestione delle circolari.
- **Docente/Studente**: API per la visualizzazione delle circolari di competenza.

## Client

Il Client è l'applicazione che richiede l'accesso alle risorse protette attraverso le API esposte dal Resource Server.

### Templates HTML

Sono stati sviluppati vari template HTML per le interfacce utente:
- **`amministratore.html`**: Interfaccia per la gestione degli utenti da parte dell'amministratore.
- **`docente.html`**: Interfaccia per la visualizzazione delle circolari da parte dei docenti.
- **`presidenza.html`**: Interfaccia per la gestione delle circolari da parte del personale di presidenza.

## Conclusione

Il progetto SOASec ha implementato con successo un sistema di gestione delle circolari basato su OAuth2.0, garantendo un accesso sicuro e autorizzato alle risorse protette.

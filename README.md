# Progetto di Programmazione ad Oggetti
### Autori: *Asya Pesaresi e Lorenzo Vagnini*

## Descrizione del Progetto
L'applicazione da noi sviluppata presente nella repository è un progetto Java che permette di decodificare un file JSON e scaricare i dati contenuti in esso in un file di tipo TSV (Tab Separated Value). In seguito è possibile applicare dei filtri ai dati e ottenere delle statistiche sui vari campi.
Il file contiene tutti i dati riguardanti la produzione di energia elettrica, suddivisi per mese e anno, dei paesi dell'Unione Europea.
Il dataset è contenuto al seguente indirizzo URL : https://ec.europa.eu/eurostat/estat-navtree-portlet-prod/BulkDownloadListing?file=data/nrg_ind_342m.tsv.gz&unzip=true.

-----
## Funzionamento del Progetto
L'aaplicazione crea un server locale tramite SpringBoot all'indirizzo http://localhost:8080.
Per ottenere tutte le informazioni si utilizza una API REST GET che permette di:
* Restituire i metadati (elenco attributi e tipo).
* Restituire i dati (integralmente o filtrati).
* Restituire delle statistiche sui dati specificando l'attributo da prendere in considerazione.

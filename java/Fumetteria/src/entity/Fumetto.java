package entity;

import database.FumettoDAO;
import exception.DAOException;
import exception.DBConnectionException;

public class Fumetto {
    private Integer idFumetto;
    private String nomeSerie;
    private String annoSerie;
    private String titolo;
    private String genere;
    private String casaEditrice;
    private byte[] immagineCopertina;
    private String descrizione;
    private Double prezzo;
    private Integer quantitaDisponibile;

    public Fumetto(Integer idFumetto, String nomeSerie, String annoSerie, String titolo, String genere, String casaEditrice, byte[] immagineCopertina, String descrizione, Double prezzo, Integer quantitaDisponibile) {
        this.idFumetto = idFumetto;
        this.nomeSerie = nomeSerie;
        this.annoSerie = annoSerie;
        this.titolo = titolo;
        this.genere = genere;
        this.casaEditrice = casaEditrice;
        this.immagineCopertina = immagineCopertina;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.quantitaDisponibile = quantitaDisponibile;
    }

    public Integer getIdFumetto() {
        return idFumetto;
    }

    public void setIdFumetto(Integer idFumetto) {
        this.idFumetto = idFumetto;
    }

    public String getNomeSerie() {
        return nomeSerie;
    }

    public void setNomeSerie(String nomeSerie) {
        this.nomeSerie = nomeSerie;
    }

    public String getAnnoSerie() {
        return annoSerie;
    }

    public void setAnnoSerie(String annoSerie) {
        this.annoSerie = annoSerie;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getCasaEditrice() {
        return casaEditrice;
    }

    public void setCasaEditrice(String casaEditrice) {
        this.casaEditrice = casaEditrice;
    }

    public byte[] getImmagineCopertina() {
        return immagineCopertina;
    }

    public void setImmagineCopertina(byte[] immagineCopertina) {
        this.immagineCopertina = immagineCopertina;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    public Integer getQuantitaDisponibile() {
        return quantitaDisponibile;
    }

    public void setQuantitaDisponibile(Integer quantitaDisponibile) {
        this.quantitaDisponibile = quantitaDisponibile;
    }

    public void saveFumetto() throws DAOException, DBConnectionException {
		FumettoDAO.createFumetto(this);
	}
    
}

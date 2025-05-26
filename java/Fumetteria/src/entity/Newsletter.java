package entity;


import database.NewsletterDAO;
import exception.DAOException;
import exception.DBConnectionException;
import java.time.LocalDate;

public class Newsletter {
    private Integer idNewsletter;
    private String titolo;
    private String descrizione;
    private LocalDate data;

    public Newsletter(Integer idNewsletter, String titolo, String descrizione, LocalDate data) {
        this.idNewsletter = idNewsletter;
        this.titolo = titolo;
        this.descrizione=descrizione;
        this.data=data;
}

    public Integer getIdNewsletter() {
        return idNewsletter;
    }

    public void setIdNewsletter(Integer idNewsletter) {
        this.idNewsletter = idNewsletter;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescizione(String descizione) {
        this.descrizione = descizione;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

     public void saveNewsletter() throws DAOException, DBConnectionException {
		NewsletterDAO.createNewsletter(this);
	}
}

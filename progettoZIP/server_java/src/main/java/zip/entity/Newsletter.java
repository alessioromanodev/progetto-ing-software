package zip.entity;

import java.time.LocalDateTime;

public class Newsletter {
    private Integer id;
    private String titolo;
    private String descrizione;
    private LocalDateTime dataCreazione;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public LocalDateTime getDataCreazione() { return dataCreazione; }
    public void setDataCreazione(LocalDateTime dataCreazione) { this.dataCreazione = dataCreazione; }
} 
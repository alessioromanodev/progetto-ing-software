package zip.entity;

import java.time.LocalDateTime;

public class Consegna {
    private int idConsegna;
    private LocalDateTime dataRichiesta;
    private LocalDateTime dataConsegna;
    private String stato;
    private int idOrdine;
    public int getIdConsegna() { return idConsegna; }
    public void setIdConsegna(int idConsegna) { this.idConsegna = idConsegna; }

    public LocalDateTime getDataRichiesta() { return dataRichiesta; }
    public void setDataRichiesta(LocalDateTime dataRichiesta) { this.dataRichiesta = dataRichiesta; }

    public LocalDateTime getDataConsegna() { return dataConsegna; }
    public void setDataConsegna(LocalDateTime dataConsegna) { this.dataConsegna = dataConsegna; }

    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }

    public int getIdOrdine() { return idOrdine; }
    public void setIdOrdine(int idOrdine) { this.idOrdine = idOrdine; }
}

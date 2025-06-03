package zip.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Ordine {
    private int id;
    private LocalDateTime dataOrdine;
    private double importoTotale;
    private String metodoConsegna;
    private String statoOrdine;
    private String qrCode;

    private int idUtente;
    private int idPagamento;
    private int idConsegna;

    private List<RigaOrdine> righeOrdine;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDateTime getDataOrdine() { return dataOrdine; }
    public void setDataOrdine(LocalDateTime dataOrdine) { this.dataOrdine = dataOrdine; }

    public double getImportoTotale() { return importoTotale; }
    public void setImportoTotale(double importoTotale) { this.importoTotale = importoTotale; }

    public String getMetodoConsegna() { return metodoConsegna; }
    public void setMetodoConsegna(String metodoConsegna) { this.metodoConsegna = metodoConsegna; }

    public String getStatoOrdine() { return statoOrdine; }
    public void setStatoOrdine(String statoOrdine) { this.statoOrdine = statoOrdine; }

    public String getQrCode() { return qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }

    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idUtente) { this.idUtente = idUtente; }

    public int getIdPagamento() { return idPagamento; }
    public void setIdPagamento(int idPagamento) { this.idPagamento = idPagamento; }

    public int getIdConsegna() { return idConsegna; }
    public void setIdConsegna(int idConsegna) { this.idConsegna = idConsegna; }

    public List<RigaOrdine> getRigheOrdine() { return righeOrdine; }
    public void setRigheOrdine(List<RigaOrdine> righeOrdine) { this.righeOrdine = righeOrdine; }
}

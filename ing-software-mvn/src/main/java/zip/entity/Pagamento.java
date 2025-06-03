package zip.entity;

import java.time.LocalDateTime;

public class Pagamento {
    private int idPagamento;
    private LocalDateTime dataPagamento;
    private double importo;
    private String metodo;
    private boolean scontoApplicato;
    private int idOrdine;

    public int getIdPagamento() { return idPagamento; }
    public void setIdPagamento(int idPagamento) { this.idPagamento = idPagamento; }

    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDateTime dataPagamento) { this.dataPagamento = dataPagamento; }

    public double getImporto() { return importo; }
    public void setImporto(double importo) { this.importo = importo; }

    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo; }

    public boolean isScontoApplicato() { return scontoApplicato; }
    public void setScontoApplicato(boolean scontoApplicato) { this.scontoApplicato = scontoApplicato; }

    public int getIdOrdine() { return idOrdine; }
    public void setIdOrdine(int idOrdine) { this.idOrdine = idOrdine; }
}

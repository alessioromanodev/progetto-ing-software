package zip.entity;

public class RigaOrdine {
    private int id;              
    private int idOrdine;        
    private int idFumetto;       
    private int quantita;
    private double prezzoUnitario;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdOrdine() { return idOrdine; }
    public void setIdOrdine(int idOrdine) { this.idOrdine = idOrdine; }

    public int getIdFumetto() { return idFumetto; }
    public void setIdFumetto(int idFumetto) { this.idFumetto = idFumetto; }

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    public double getPrezzoUnitario() { return prezzoUnitario; }
    public void setPrezzoUnitario(double prezzoUnitario) { this.prezzoUnitario = prezzoUnitario; }
}

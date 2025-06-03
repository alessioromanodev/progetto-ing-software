package zip.entity;

public class Fumetto {
    private int id;
    private String nomeSerie;
    private String annoSerie;
    private String autore;
    private int numeroVolume;
    private String titolo;
    private String genere;
    private String casaEditrice;
    private byte[] immagineCopertina;
    private String descrizione;
    private double prezzo;
    private int quantitaDisponibile;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomeSerie() { return nomeSerie; }
    public void setNomeSerie(String nomeSerie) { this.nomeSerie = nomeSerie; }

    public String getAnnoSerie() { return annoSerie; }
    public void setAnnoSerie(String annoSerie) { this.annoSerie = annoSerie; }

    public String getAutore() { return autore; }
    public void setAutore(String autore) { this.autore = autore; }

    public int getNumeroVolume() { return numeroVolume; }
    public void setNumeroVolume(int numeroVolume) { this.numeroVolume = numeroVolume; }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public String getGenere() { return genere; }
    public void setGenere(String genere) { this.genere = genere; }

    public String getCasaEditrice() { return casaEditrice; }
    public void setCasaEditrice(String casaEditrice) { this.casaEditrice = casaEditrice; }

    public byte[] getImmagineCopertina() { return immagineCopertina; }
    public void setImmagineCopertina(byte[] immagineCopertina) { this.immagineCopertina = immagineCopertina; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public double getPrezzo() { return prezzo; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }

    public int getQuantitaDisponibile() { return quantitaDisponibile; }
    public void setQuantitaDisponibile(int quantitaDisponibile) { this.quantitaDisponibile = quantitaDisponibile; }
}


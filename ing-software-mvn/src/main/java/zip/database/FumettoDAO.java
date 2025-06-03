package zip.database;

import zip.entity.Fumetto;
import java.sql.*;
import java.util.*;

public class FumettoDAO {

    public List<Fumetto> findAll() throws SQLException {
        String sql = "SELECT * FROM fumetto";
        List<Fumetto> list = new ArrayList<>();
        try (Connection c = DBManager.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                Fumetto f = new Fumetto();
                f.setId(rs.getInt("id"));
                f.setNomeSerie(rs.getString("nome_serie"));
                f.setAnnoSerie(rs.getString("anno_serie"));
                f.setAutore(rs.getString("autore"));
                f.setNumeroVolume(rs.getInt("numero_volume"));
                f.setTitolo(rs.getString("titolo"));
                f.setGenere(rs.getString("genere"));
                f.setCasaEditrice(rs.getString("casa_editrice"));
                f.setImmagineCopertina(rs.getBytes("immagine_copertina"));
                f.setDescrizione(rs.getString("descrizione"));
                f.setPrezzo(rs.getDouble("prezzo"));
                f.setQuantitaDisponibile(rs.getInt("quantita_disponibile"));
                list.add(f);
            }
        }
        return list;
    }

    public void createFumetto(Fumetto f) throws SQLException {
        String sql = "INSERT INTO fumetto (nome_serie, anno_serie, autore, numero_volume, titolo, genere, casa_editrice, immagine_copertina, descrizione, prezzo, quantita_disponibile) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, f.getNomeSerie());
            ps.setString(2, f.getAnnoSerie());
            ps.setString(3, f.getAutore());
            ps.setInt(4, f.getNumeroVolume());
            ps.setString(5, f.getTitolo());
            ps.setString(6, f.getGenere());
            ps.setString(7, f.getCasaEditrice());
            ps.setBytes(8, f.getImmagineCopertina());
            ps.setString(9, f.getDescrizione());
            ps.setDouble(10, f.getPrezzo());
            ps.setInt(11, f.getQuantitaDisponibile());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    f.setId(keys.getInt(1));
                }
            }
        }
    }

    public Fumetto findFumettoById(int id) throws SQLException {
        String sql = "SELECT * FROM fumetto WHERE id = ?";
        try (Connection c = DBManager.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Fumetto f = new Fumetto();
                    f.setId(rs.getInt("id"));
                    f.setNomeSerie(rs.getString("nome_serie"));
                    f.setAnnoSerie(rs.getString("anno_serie"));
                    f.setAutore(rs.getString("autore"));
                    f.setNumeroVolume(rs.getInt("numero_volume"));
                    f.setTitolo(rs.getString("titolo"));
                    f.setGenere(rs.getString("genere"));
                    f.setCasaEditrice(rs.getString("casa_editrice"));
                    f.setImmagineCopertina(rs.getBytes("immagine_copertina"));
                    f.setDescrizione(rs.getString("descrizione"));
                    f.setPrezzo(rs.getDouble("prezzo"));
                    f.setQuantitaDisponibile(rs.getInt("quantita_disponibile"));
                    return f;
                }
            }
        }
        return null;
    }

    public boolean updateFumetto(Fumetto f) throws SQLException {
        String sql = "UPDATE fumetto SET nome_serie = ?, anno_serie = ?, autore = ?, numero_volume = ?, titolo = ?, genere = ?, casa_editrice = ?, immagine_copertina = ?, descrizione = ?, prezzo = ?, quantita_disponibile = ? WHERE id = ?";
        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, f.getNomeSerie());
            ps.setString(2, f.getAnnoSerie());
            ps.setString(3, f.getAutore());
            ps.setInt(4, f.getNumeroVolume());
            ps.setString(5, f.getTitolo());
            ps.setString(6, f.getGenere());
            ps.setString(7, f.getCasaEditrice());
            ps.setBytes(8, f.getImmagineCopertina());
            ps.setString(9, f.getDescrizione());
            ps.setDouble(10, f.getPrezzo());
            ps.setInt(11, f.getQuantitaDisponibile());
            ps.setInt(12, f.getId());

            return ps.executeUpdate() == 1;
        }
    }

    public boolean deleteFumettoById(int id) throws SQLException {
        String sql = "DELETE FROM fumetto WHERE id = ?";
        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }
}

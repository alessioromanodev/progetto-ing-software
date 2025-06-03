package zip.database;

import zip.entity.Consegna;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsegnaDAO {

    /** Recupera tutte le consegne */
    public List<Consegna> findAll() throws SQLException {
        List<Consegna> consegne = new ArrayList<>();
        String sql = "SELECT id_consegna, data_richiesta, data_consegna, stato, id_ordine FROM consegna";

        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Consegna con = extractConsegna(rs);
                consegne.add(con);
            }
        }
        return consegne;
    }

    /** Recupera una singola consegna per ID */
    public Consegna findById(int id) throws SQLException {
        String sql = "SELECT id_consegna, data_richiesta, data_consegna, stato, id_ordine " +
                     "  FROM consegna WHERE id_consegna = ?";

        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractConsegna(rs);
                }
            }
        }
        return null;
    }

    /** Inserisce una nuova consegna */
    public boolean create(Consegna c) throws SQLException {
        String sql = "INSERT INTO consegna (data_richiesta, data_consegna, stato, id_ordine) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setTimestamp(1, Timestamp.valueOf(c.getDataRichiesta()));
            ps.setTimestamp(2, Timestamp.valueOf(c.getDataConsegna()));
            ps.setString(3, c.getStato());
            ps.setInt(4, c.getIdOrdine());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        c.setIdConsegna(keys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        }
    }

    /** Aggiorna una consegna esistente */
    public boolean update(Consegna c) throws SQLException {
        String sql = "UPDATE consegna SET data_richiesta = ?, data_consegna = ?, stato = ?, id_ordine = ? " +
                     "WHERE id_consegna = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(c.getDataRichiesta()));
            ps.setTimestamp(2, Timestamp.valueOf(c.getDataConsegna()));
            ps.setString(3, c.getStato());
            ps.setInt(4, c.getIdOrdine());
            ps.setInt(5, c.getIdConsegna());

            return ps.executeUpdate() == 1;
        }
    }

    /** Elimina una consegna per ID */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM consegna WHERE id_consegna = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    /** Estrae un oggetto Consegna da un ResultSet */
    private Consegna extractConsegna(ResultSet rs) throws SQLException {
        Consegna c = new Consegna();
        c.setIdConsegna(rs.getInt("id_consegna"));
        Timestamp trichiesta = rs.getTimestamp("data_richiesta");
        if (trichiesta != null) {
            c.setDataRichiesta(trichiesta.toLocalDateTime());
        }
        Timestamp tconsegna = rs.getTimestamp("data_consegna");
        if (tconsegna != null) {
            c.setDataConsegna(tconsegna.toLocalDateTime());
        }
        c.setStato(rs.getString("stato"));
        c.setIdOrdine(rs.getInt("id_ordine"));
        return c;
    }
}

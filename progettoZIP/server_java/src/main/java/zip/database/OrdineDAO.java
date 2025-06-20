package zip.database;

import zip.entity.Ordine;
import zip.entity.RigaOrdine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO {

    public List<Ordine> findAll() throws SQLException {
        List<Ordine> ordini = new ArrayList<>();
        String sqlOrdini =
            "SELECT id, data_ordine, importo_totale, metodo_consegna, stato_ordine, qr_code, " +
            "       id_utente " +
            "  FROM ordine";

        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sqlOrdini);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ordine o = extractOrdine(rs);
                o.setRigheOrdine(findRigheByOrdineId(o.getId()));
                ordini.add(o);
            }
        }
        return ordini;
    }

    public Ordine findById(int id) throws SQLException {
        String sql =
            "SELECT id, data_ordine, importo_totale, metodo_consegna, stato_ordine, qr_code, " +
            "       id_utente " +
            "  FROM ordine " +
            " WHERE id = ?";

        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ordine o = extractOrdine(rs);
                    o.setRigheOrdine(findRigheByOrdineId(o.getId()));
                    return o;
                }
            }
        }
        return null;
    }

    public boolean create(Ordine ordine) throws SQLException {
        String sqlOrdine =
            "INSERT INTO ordine (" +
            "    data_ordine, importo_totale, metodo_consegna, stato_ordine, qr_code, " +
            "    id_utente" +
            ") VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection c = DBManager.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sqlOrdine, Statement.RETURN_GENERATED_KEYS)) {
                ps.setTimestamp(1, Timestamp.valueOf(ordine.getDataOrdine()));
                ps.setDouble(2, ordine.getImportoTotale());
                ps.setString(3, ordine.getMetodoConsegna());
                ps.setString(4, ordine.getStatoOrdine());
                ps.setString(5, ordine.getQrCode());
                ps.setInt(6, ordine.getIdUtente());

                int affected = ps.executeUpdate();
                if (affected == 0) {
                    c.rollback();
                    return false;
                }

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        int newId = keys.getInt(1);
                        ordine.setId(newId);
                    } else {
                        c.rollback();
                        return false;
                    }
                }

                if (!insertRighe(c, ordine)) {
                    c.rollback();
                    return false;
                }

                c.commit();
                return true;
            } catch (SQLException ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }

    public boolean update(Ordine ordine) throws SQLException {
        String sqlOrdine =
            "UPDATE ordine SET " +
            "    data_ordine = ?, importo_totale = ?, metodo_consegna = ?, stato_ordine = ?, " +
            "    qr_code = ?, id_utente = ? " +
            " WHERE id = ?";

        try (Connection c = DBManager.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sqlOrdine)) {
                ps.setTimestamp(1, Timestamp.valueOf(ordine.getDataOrdine()));
                ps.setDouble(2, ordine.getImportoTotale());
                ps.setString(3, ordine.getMetodoConsegna());
                ps.setString(4, ordine.getStatoOrdine());
                ps.setString(5, ordine.getQrCode());
                ps.setInt(6, ordine.getIdUtente());
                ps.setInt(7, ordine.getId());

                if (ps.executeUpdate() != 1) {
                    c.rollback();
                    return false;
                }
            }

            deleteRigheByOrdine(c, ordine.getId());
            if (!insertRighe(c, ordine)) {
                c.rollback();
                return false;
            }

            c.commit();
            return true;
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public boolean delete(int ordineId) throws SQLException {
        try (Connection c = DBManager.getConnection()) {
            c.setAutoCommit(false);
            deleteRigheByOrdine(c, ordineId);

            String sql = "DELETE FROM ordine WHERE id = ?";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, ordineId);
                if (ps.executeUpdate() != 1) {
                    c.rollback();
                    return false;
                }
            }

            c.commit();
            return true;
        } catch (SQLException ex) {
            throw ex;
        }
    }

    private List<RigaOrdine> findRigheByOrdineId(int ordineId) throws SQLException {
        List<RigaOrdine> righe = new ArrayList<>();
        String sql =
            "SELECT id, id_ordine, id_fumetto, quantita, prezzo_unitario " +
            "  FROM riga_ordine " +
            " WHERE id_ordine = ?";

        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, ordineId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RigaOrdine r = new RigaOrdine();
                    r.setId(rs.getInt("id"));
                    r.setIdOrdine(rs.getInt("id_ordine"));
                    r.setIdFumetto(rs.getInt("id_fumetto"));
                    r.setQuantita(rs.getInt("quantita"));
                    r.setPrezzoUnitario(rs.getDouble("prezzo_unitario"));
                    righe.add(r);
                }
            }
        }
        return righe;
    }

    private boolean insertRighe(Connection c, Ordine ordine) throws SQLException {
        if (ordine.getRigheOrdine() == null || ordine.getRigheOrdine().isEmpty()) {
            return true;
        }

        String sql =
            "INSERT INTO riga_ordine (id_ordine, id_fumetto, quantita, prezzo_unitario) " +
            "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            for (RigaOrdine r : ordine.getRigheOrdine()) {
                ps.setInt(1, ordine.getId());
                ps.setInt(2, r.getIdFumetto());
                ps.setInt(3, r.getQuantita());
                ps.setDouble(4, r.getPrezzoUnitario());
                ps.addBatch();
            }
            ps.executeBatch();
        }
        return true;
    }

    private void deleteRigheByOrdine(Connection c, int ordineId) throws SQLException {
        String sql = "DELETE FROM riga_ordine WHERE id_ordine = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, ordineId);
            ps.executeUpdate();
        }
    }

    private Ordine extractOrdine(ResultSet rs) throws SQLException {
        Ordine o = new Ordine();
        o.setId(rs.getInt("id"));
        o.setDataOrdine(rs.getTimestamp("data_ordine").toLocalDateTime());
        o.setImportoTotale(rs.getDouble("importo_totale"));
        o.setMetodoConsegna(rs.getString("metodo_consegna"));
        o.setStatoOrdine(rs.getString("stato_ordine"));
        o.setQrCode(rs.getString("qr_code"));
        o.setIdUtente(rs.getInt("id_utente"));
        return o;
    }
}
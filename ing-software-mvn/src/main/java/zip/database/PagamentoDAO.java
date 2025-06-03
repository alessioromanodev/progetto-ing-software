package zip.database;

import zip.entity.Pagamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {

    /** Recupera tutti i pagamenti */
    public List<Pagamento> findAll() throws SQLException {
        List<Pagamento> pagamenti = new ArrayList<>();
        String sql = "SELECT id_pagamento, data_pagamento, importo, metodo, sconto_applicato, id_ordine FROM pagamento";

        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                pagamenti.add(extractPagamento(rs));
            }
        }
        return pagamenti;
    }

    /** Recupera un pagamento per ID */
    public Pagamento findById(int id) throws SQLException {
        String sql = "SELECT id_pagamento, data_pagamento, importo, metodo, sconto_applicato, id_ordine " +
                     "FROM pagamento WHERE id_pagamento = ?";

        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractPagamento(rs);
                }
            }
        }
        return null;
    }

    /** Recupera il pagamento associato a un ordine specifico */
    public Pagamento findByOrdineId(int idOrdine) throws SQLException {
        String sql = "SELECT id_pagamento, data_pagamento, importo, metodo, sconto_applicato, id_ordine " +
                     "FROM pagamento WHERE id_ordine = ?";

        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idOrdine);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractPagamento(rs);
                }
            }
        }
        return null;
    }

    /** Crea un nuovo pagamento */
    public boolean create(Pagamento p) throws SQLException {
        String sql = "INSERT INTO pagamento (data_pagamento, importo, metodo, sconto_applicato, id_ordine) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setTimestamp(1, Timestamp.valueOf(p.getDataPagamento()));
            ps.setDouble(2, p.getImporto());
            ps.setString(3, p.getMetodo());
            ps.setBoolean(4, p.isScontoApplicato());
            ps.setInt(5, p.getIdOrdine());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        p.setIdPagamento(keys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        }
    }

    /** Aggiorna un pagamento esistente */
    public boolean update(Pagamento p) throws SQLException {
        String sql = "UPDATE pagamento SET data_pagamento = ?, importo = ?, metodo = ?, sconto_applicato = ?, id_ordine = ? " +
                     "WHERE id_pagamento = ?";

        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(p.getDataPagamento()));
            ps.setDouble(2, p.getImporto());
            ps.setString(3, p.getMetodo());
            ps.setBoolean(4, p.isScontoApplicato());
            ps.setInt(5, p.getIdOrdine());
            ps.setInt(6, p.getIdPagamento());

            return ps.executeUpdate() == 1;
        }
    }

    /** Elimina un pagamento per ID */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM pagamento WHERE id_pagamento = ?";

        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    /** Estrae un oggetto Pagamento da un ResultSet */
    private Pagamento extractPagamento(ResultSet rs) throws SQLException {
        Pagamento p = new Pagamento();
        p.setIdPagamento(rs.getInt("id_pagamento"));
        Timestamp ts = rs.getTimestamp("data_pagamento");
        if (ts != null) {
            p.setDataPagamento(ts.toLocalDateTime());
        }
        p.setImporto(rs.getDouble("importo"));
        p.setMetodo(rs.getString("metodo"));
        p.setScontoApplicato(rs.getBoolean("sconto_applicato"));
        p.setIdOrdine(rs.getInt("id_ordine"));
        return p;
    }
}

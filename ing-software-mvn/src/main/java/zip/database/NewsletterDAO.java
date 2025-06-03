package zip.database;

import zip.entity.Newsletter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsletterDAO {

    public List<Newsletter> findAll() throws SQLException {
        List<Newsletter> list = new ArrayList<>();
        String sql = "SELECT * FROM newsletter";

        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Newsletter n = new Newsletter();
                n.setId(rs.getInt("id_newsletter"));
                n.setTitolo(rs.getString("titolo"));
                n.setDescrizione(rs.getString("descrizione"));
                Timestamp ts = rs.getTimestamp("data_creazione");
                if (ts != null) n.setDataCreazione(ts.toLocalDateTime());
                list.add(n);
            }
        }

        return list;
    }

    public Newsletter findById(int id) throws SQLException {
        String sql = "SELECT * FROM newsletter WHERE id_newsletter = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Newsletter n = new Newsletter();
                    n.setId(rs.getInt("id_newsletter"));
                    n.setTitolo(rs.getString("titolo"));
                    n.setDescrizione(rs.getString("descrizione"));
                    Timestamp ts = rs.getTimestamp("data_creazione");
                    if (ts != null) n.setDataCreazione(ts.toLocalDateTime());
                    return n;
                }
            }
        }
        return null;
    }

    public boolean create(Newsletter n) throws SQLException {
        String sql = "INSERT INTO newsletter (titolo, descrizione) VALUES (?, ?)";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, n.getTitolo());
            ps.setString(2, n.getDescrizione());

            int rows = ps.executeUpdate();

            if (rows == 1) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        n.setId(keys.getInt(1));
                    }
                }
            }

            return rows == 1;
        }
    }

    public boolean update(Newsletter n) throws SQLException {
        String sql = "UPDATE newsletter SET titolo = ?, descrizione = ? WHERE id_newsletter = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, n.getTitolo());
            ps.setString(2, n.getDescrizione());
            ps.setInt(3, n.getId());

            return ps.executeUpdate() == 1;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM newsletter WHERE id_newsletter = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }
}

package zip.database;

import zip.entity.Ordine;
import zip.entity.Utente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {

    /** Recupera tutti gli utenti (e popola la lista ordini per ciascuno) */
    public List<Utente> findAll() throws SQLException {
        List<Utente> utenti = new ArrayList<>();
        String sql = "SELECT id, nome, cognome, username, role, password, email, indirizzo, registrato FROM utente";

        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Utente u = extractUtente(rs);
                u.setOrdini(findOrdiniByUtenteId(u.getId()));
                utenti.add(u);
            }
        }
        return utenti;
    }

    /** Recupera un singolo utente per ID (e popola la lista ordini) */
    public Utente findById(int id) throws SQLException {
        String sql = "SELECT id, nome, cognome, username, role, password, email, indirizzo, registrato " +
                     "  FROM utente WHERE id = ?";

        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Utente u = extractUtente(rs);
                    u.setOrdini(findOrdiniByUtenteId(id));
                    return u;
                }
            }
        }
        return null;
    }

    /** Inserisce un nuovo utente */
    public boolean create(Utente u) throws SQLException {
        String sql = "INSERT INTO utente " +
                     "(nome, cognome, username, role, password, email, indirizzo, registrato) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getNome());
            ps.setString(2, u.getCognome());
            ps.setString(3, u.getUsername());
            ps.setString(4, u.getRole());
            ps.setString(5, u.getPassword());
            ps.setString(6, u.getEmail());
            ps.setString(7, u.getIndirizzo());
            ps.setBoolean(8, u.getRegistrato());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        u.setId(keys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        }
    }

    /** Aggiorna un utente esistente */
    public boolean update(Utente u) throws SQLException {
        String sql = "UPDATE utente SET " +
                     "nome = ?, cognome = ?, username = ?, role = ?, password = ?, email = ?, indirizzo = ?, registrato = ? " +
                     "WHERE id = ?";
        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, u.getNome());
            ps.setString(2, u.getCognome());
            ps.setString(3, u.getUsername());
            ps.setString(4, u.getRole());
            ps.setString(5, u.getPassword());
            ps.setString(6, u.getEmail());
            ps.setString(7, u.getIndirizzo());
            ps.setBoolean(8, u.getRegistrato());
            ps.setInt(9, u.getId());

            return ps.executeUpdate() == 1;
        }
    }

    /** Elimina un utente per ID */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM utente WHERE id = ?";
        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    /** Restituisce la lista di Ordini associati a un utente, chiamando OrdineDAO.findByUtenteId(...) */
    private List<Ordine> findOrdiniByUtenteId(int idUtente) throws SQLException {
        List<Ordine> ordini = new ArrayList<>();
        String sql = "SELECT id FROM ordine WHERE id_utente = ?";

        try (Connection c = DBManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idUtente);
            try (ResultSet rs = ps.executeQuery()) {
                OrdineDAO ordineDAO = new OrdineDAO();
                while (rs.next()) {
                    int idOrdine = rs.getInt("id");
                    Ordine o = ordineDAO.findById(idOrdine);
                    if (o != null) {
                        ordini.add(o);
                    }
                }
            }
        }
        return ordini;
    }

    /** Estrae un Utente (senza ordini) da un ResultSet */
    private Utente extractUtente(ResultSet rs) throws SQLException {
        Utente u = new Utente();
        u.setId(rs.getInt("id"));
        u.setNome(rs.getString("nome"));
        u.setCognome(rs.getString("cognome"));
        u.setUsername(rs.getString("username"));
        u.setRole(rs.getString("role"));
        u.setPassword(rs.getString("password"));
        u.setEmail(rs.getString("email"));
        u.setIndirizzo(rs.getString("indirizzo"));
        u.setRegistrato(rs.getBoolean("registrato"));
        return u;
    }
}

package zip.control;

import zip.database.UtenteDAO;
import zip.entity.Utente;

import java.sql.SQLException;
import java.util.List;

public class UtenteController {
    private final UtenteDAO dao = new UtenteDAO();

    public List<Utente> findAll() {
        try {
            return dao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Utente findById(int id) {
        try {
            return dao.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean create(Utente u) {
        try {
            return dao.create(u);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Utente u) {
        try {
            return dao.update(u);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            return dao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

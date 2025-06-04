package zip.control;

import zip.database.OrdineDAO;
import zip.entity.Ordine;

import java.sql.SQLException;
import java.util.List;

public class OrdineController {
    private final OrdineDAO dao = new OrdineDAO();

    public List<Ordine> findAll() {
        try {
            return dao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Ordine findById(int id) {
        try {
            return dao.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean create(Ordine ordine) {
        try {
            return dao.create(ordine);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Ordine ordine) {
        try {
            return dao.update(ordine);
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

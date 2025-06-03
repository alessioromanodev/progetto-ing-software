package zip.control;

import zip.database.ConsegnaDAO;
import zip.entity.Consegna;

import java.sql.SQLException;
import java.util.List;

public class ConsegnaController {
    private final ConsegnaDAO dao = new ConsegnaDAO();

    public List<Consegna> findAll() {
        try {
            return dao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Consegna findById(int id) {
        try {
            return dao.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean create(Consegna c) {
        try {
            return dao.create(c);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Consegna c) {
        try {
            return dao.update(c);
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

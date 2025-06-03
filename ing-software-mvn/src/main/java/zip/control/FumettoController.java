package zip.control;

import zip.database.FumettoDAO;
import zip.entity.Fumetto;

import java.sql.SQLException;
import java.util.List;

public class FumettoController {

    private final FumettoDAO dao;

    public FumettoController() {
        this.dao = new FumettoDAO();
    }

    public List<Fumetto> findAll() {
        try {
            return dao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Fumetto findById(int id) {
        try {
            return dao.findFumettoById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean create(Fumetto f) {
        try {
            dao.createFumetto(f);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Fumetto f) {
        try {
            return dao.updateFumetto(f);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            return dao.deleteFumettoById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

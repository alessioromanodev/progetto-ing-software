package zip.control;

import zip.database.PagamentoDAO;
import zip.entity.Pagamento;

import java.sql.SQLException;
import java.util.List;

public class PagamentoController {
    private final PagamentoDAO dao = new PagamentoDAO();

    public List<Pagamento> findAll() {
        try {
            return dao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Pagamento findById(int id) {
        try {
            return dao.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Pagamento findByOrdineId(int idOrdine) {
        try {
            return dao.findByOrdineId(idOrdine);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean create(Pagamento p) {
        try {
            return dao.create(p);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Pagamento p) {
        try {
            return dao.update(p);
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

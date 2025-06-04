package zip.control;

import zip.database.NewsletterDAO;
import zip.entity.Newsletter;

import java.sql.SQLException;
import java.util.List;

public class NewsletterController {
    private final NewsletterDAO dao = new NewsletterDAO();

    public List<Newsletter> findAll() {
        try {
            return dao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Newsletter findById(int id) {
        try {
            return dao.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean create(Newsletter n) {
        try {
            return dao.create(n);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Newsletter n) {
        try {
            return dao.update(n);
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

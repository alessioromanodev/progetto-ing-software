package zip.control;

import zip.database.NewsletterDAO;
import zip.entity.Newsletter;
import zip.entity.Utente;

import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NewsletterController {
  private final NewsletterDAO dao = new NewsletterDAO();
  private final UtenteController utenteCtrl = new UtenteController();
  private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

  public NewsletterController() {
    scheduleMonthlyNewsletter();
  }

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

  public void sendMonthlyNewsletter() {
    try {
      Newsletter latest = dao.findLatest();
      if (latest == null) {
        System.out.println("Nessuna newsletter da inviare.");
        return;
      }

      List<Utente> users = utenteCtrl.findAll();
      for (Utente u : users) {
        System.out.printf("Invio newsletter \"%s\" a %s (%s)%n",
            latest.getTitolo(), u.getNome() + " " + u.getCognome(), u.getEmail());
      }
      System.out.println("Invio newsletter completato.");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void scheduleMonthlyNewsletter() {
    Runnable task = this::sendMonthlyNewsletter;
    long period = Duration.ofDays(30).toMillis();

    scheduler.scheduleAtFixedRate(task, 0, period, TimeUnit.MILLISECONDS);
  }
}

package zip.boundary.api;

import static spark.Spark.*;

import com.google.gson.Gson;
import zip.control.NewsletterController;
import zip.entity.Newsletter;

import java.util.List;

public class NewsletterApi {

    public static void registerRoutes(Gson gson) {
        NewsletterController controller = new NewsletterController();

        path("/newsletter", () -> {

            get("", (req, res) -> {
                res.type("application/json");
                List<Newsletter> lista = controller.findAll();
                return gson.toJson(lista);
            });

            get("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                Newsletter n = controller.findById(id);
                if (n != null) {
                    return gson.toJson(n);
                } else {
                    res.status(404);
                    return gson.toJson("Newsletter non trovata");
                }
            });

            post("", (req, res) -> {
                res.type("application/json");
                Newsletter nuova = gson.fromJson(req.body(), Newsletter.class);
                boolean successo = controller.create(nuova);
                res.status(successo ? 201 : 400);
                return gson.toJson(successo ? "Creazione avvenuta con successo" : "Errore nella creazione");
            });

            put("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                Newsletter aggiornata = gson.fromJson(req.body(), Newsletter.class);
                aggiornata.setId(id);
                boolean successo = controller.update(aggiornata);
                res.status(successo ? 200 : 400);
                return gson.toJson(successo ? "Aggiornamento avvenuto con successo" : "Errore nell'aggiornamento");
            });

            delete("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                boolean successo = controller.delete(id);
                res.status(successo ? 200 : 404);
                return gson.toJson(successo ? "Eliminata con successo" : "Newsletter non trovata");
            });

        });
    }
}

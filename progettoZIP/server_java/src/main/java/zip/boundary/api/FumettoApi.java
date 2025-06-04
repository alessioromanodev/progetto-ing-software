package zip.boundary.api;

import static spark.Spark.*;

import com.google.gson.Gson;
import zip.control.FumettoController;
import zip.entity.Fumetto;

public class FumettoApi {

    public static void registerRoutes(Gson gson) {
        FumettoController controller = new FumettoController();

        path("/fumetti", () -> {

            get("", (req, res) -> {
                res.type("application/json");
                return gson.toJson(controller.findAll());
            });

            get("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                Fumetto f = controller.findById(id);
                if (f != null) {
                    return gson.toJson(f);
                } else {
                    res.status(404);
                    return gson.toJson("Fumetto non trovato");
                }
            });

            post("", (req, res) -> {
                res.type("application/json");
                Fumetto nuovo = gson.fromJson(req.body(), Fumetto.class);
                boolean successo = controller.create(nuovo);
                res.status(successo ? 201 : 400);
                return gson.toJson(successo ? "Inserito con successo" : "Errore inserimento");
            });

            put("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                Fumetto f = gson.fromJson(req.body(), Fumetto.class);
                f.setId(id);
                boolean successo = controller.update(f);
                res.status(successo ? 200 : 400);
                return gson.toJson(successo ? "Aggiornato con successo" : "Errore aggiornamento");
            });

            delete("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                boolean successo = controller.delete(id);
                res.status(successo ? 200 : 400);
                return gson.toJson(successo ? "Cancellato con successo" : "Errore cancellazione");
            });

        });
    }
}

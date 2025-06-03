package zip.boundary.api;

import static spark.Spark.*;

import com.google.gson.Gson;
import zip.control.ConsegnaController;
import zip.entity.Consegna;

import java.util.List;

public class ConsegnaApi {

    public static void registerRoutes(Gson gson) {
        ConsegnaController controller = new ConsegnaController();

        path("/consegne", () -> {

            get("", (req, res) -> {
                res.type("application/json");
                List<Consegna> lista = controller.findAll();
                return gson.toJson(lista);
            });

            get("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                Consegna c = controller.findById(id);
                if (c != null) {
                    return gson.toJson(c);
                } else {
                    res.status(404);
                    return gson.toJson("Consegna non trovata");
                }
            });

            post("", (req, res) -> {
                res.type("application/json");
                Consegna nuova = gson.fromJson(req.body(), Consegna.class);
                boolean successo = controller.create(nuova);
                res.status(successo ? 201 : 400);
                return gson.toJson(successo ? "Consegna creata" : "Errore creazione");
            });

            put("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                Consegna aggiornata = gson.fromJson(req.body(), Consegna.class);
                aggiornata.setIdConsegna(id);
                boolean successo = controller.update(aggiornata);
                res.status(successo ? 200 : 400);
                return gson.toJson(successo ? "Consegna aggiornata" : "Errore aggiornamento");
            });

            delete("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                boolean successo = controller.delete(id);
                res.status(successo ? 204 : 404);
                return gson.toJson(successo ? "Consegna eliminata" : "Errore eliminazione");
            });

        });
    }
}

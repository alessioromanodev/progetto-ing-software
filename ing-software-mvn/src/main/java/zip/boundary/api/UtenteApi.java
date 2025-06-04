package zip.boundary.api;

import static spark.Spark.*;

import com.google.gson.Gson;
import zip.control.UtenteController;
import zip.entity.Utente;

import java.util.List;

public class UtenteApi {

    public static void registerRoutes(Gson gson) {
        UtenteController controller = new UtenteController();

        path("/utenti", () -> {

            get("", (req, res) -> {
                res.type("application/json");
                List<Utente> lista = controller.findAll();
                return gson.toJson(lista);
            });

            get("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                Utente u = controller.findById(id);
                if (u != null) {
                    return gson.toJson(u);
                } else {
                    res.status(404);
                    return gson.toJson("Utente non trovato");
                }
            });

            post("", (req, res) -> {
                res.type("application/json");
                Utente nuovo = gson.fromJson(req.body(), Utente.class);
                boolean successo = controller.create(nuovo);
                res.status(successo ? 201 : 400);
                return gson.toJson(successo ? "Utente creato" : "Errore creazione utente");
            });

            put("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                Utente aggiornato = gson.fromJson(req.body(), Utente.class);
                aggiornato.setId(id);
                boolean successo = controller.update(aggiornato);
                res.status(successo ? 200 : 400);
                return gson.toJson(successo ? "Utente aggiornato" : "Errore aggiornamento utente");
            });

            delete("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                boolean successo = controller.delete(id);
                res.status(successo ? 204 : 404);
                return gson.toJson(successo ? "Utente eliminato" : "Utente non trovato");
            });

        });
    }
}

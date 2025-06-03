package zip.boundary.api;

import static spark.Spark.*;

import com.google.gson.Gson;
import zip.control.OrdineController;
import zip.entity.Ordine;

public class OrdineApi {

    public static void registerRoutes(Gson gson) {
        OrdineController controller = new OrdineController();

        path("/ordini", () -> {

            // GET /ordini → restituisce tutti gli ordini (con righe)
            get("", (req, res) -> {
                res.type("application/json");
                return gson.toJson(controller.findAll());
            });

            // GET /ordini/:id → restituisce un ordine specifico
            get("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                Ordine o = controller.findById(id);
                if (o != null) {
                    return gson.toJson(o);
                } else {
                    res.status(404);
                    return gson.toJson("Ordine non trovato");
                }
            });

            // POST /ordini → crea un nuovo ordine (inserisce ordine + righe)
            post("", (req, res) -> {
                res.type("application/json");
                Ordine nuovo = gson.fromJson(req.body(), Ordine.class);
                boolean successo = controller.create(nuovo);
                res.status(successo ? 201 : 400);
                return gson.toJson(successo ? "Ordine creato" : "Errore creazione ordine");
            });

            // PUT /ordini/:id → aggiorna un ordine esistente (sostituisce righe)
            put("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                Ordine aggiornato = gson.fromJson(req.body(), Ordine.class);
                aggiornato.setId(id);
                boolean successo = controller.update(aggiornato);
                res.status(successo ? 200 : 400);
                return gson.toJson(successo ? "Ordine aggiornato" : "Errore aggiornamento ordine");
            });

            // DELETE /ordini/:id → elimina un ordine (e le righe collegate)
            delete("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                boolean successo = controller.delete(id);
                res.status(successo ? 204 : 404);
                return gson.toJson(successo ? "Ordine eliminato" : "Errore eliminazione ordine");
            });

        });
    }
}

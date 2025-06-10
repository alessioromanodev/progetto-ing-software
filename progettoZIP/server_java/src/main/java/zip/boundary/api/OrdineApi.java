package zip.boundary.api;

import static spark.Spark.*;
import com.google.gson.Gson;
import zip.control.OrdineController;
import zip.entity.Ordine;

public class OrdineApi {

    public static void registerRoutes(Gson gson) {
        OrdineController controller = new OrdineController();

        path("/ordini", () -> {
            // CORS preflight handler
            options("", (req, res) -> {
                String reqHeaders = req.headers("Access-Control-Request-Headers");
                if (reqHeaders != null) {
                    res.header("Access-Control-Allow-Headers", reqHeaders);
                }
                String reqMethods = req.headers("Access-Control-Request-Method");
                if (reqMethods != null) {
                    res.header("Access-Control-Allow-Methods", reqMethods);
                }
                // origin header set globally
                res.status(200);
                return "";
            });

            // GET /ordini - lista tutti gli ordini
            get("", (req, res) -> {
                res.type("application/json");
                return gson.toJson(controller.findAll());
            });

            // GET /ordini/:id - trova ordine per ID
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

            // POST /ordini - crea un nuovo ordine e lo restituisce
            post("", "application/json", (req, res) -> {
                res.type("application/json");
                Ordine nuovo = gson.fromJson(req.body(), Ordine.class);
                boolean successo = controller.create(nuovo);
                if (successo) {
                    res.status(201);
                    // controller.create() popola l'ID su "nuovo"
                    return gson.toJson(nuovo);
                } else {
                    res.status(400);
                    return gson.toJson("Errore creazione ordine");
                }
            });

            // PUT /ordini/:id - aggiorna ordine esistente
            put("/:id", "application/json", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                Ordine aggiornato = gson.fromJson(req.body(), Ordine.class);
                aggiornato.setId(id);
                boolean successo = controller.update(aggiornato);
                res.status(successo ? 200 : 400);
                return gson.toJson(successo ? "Ordine aggiornato" : "Errore aggiornamento ordine");
            });

            // DELETE /ordini/:id - elimina un ordine
            delete("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                boolean successo = controller.delete(id);
                if (successo) {
                    res.status(200);
                    return gson.toJson("Ordine eliminato");
                } else {
                    res.status(404);
                    return gson.toJson("Errore: ordine non trovato");
                }
            });
        });
    }
}

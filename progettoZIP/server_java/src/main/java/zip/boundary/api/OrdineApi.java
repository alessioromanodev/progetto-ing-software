package zip.boundary.api;

import static spark.Spark.*;
import com.google.gson.Gson;
import zip.control.OrdineController;
import zip.control.FumettoController;
import zip.entity.Ordine;
import zip.entity.RigaOrdine;
import zip.entity.Fumetto;

import java.util.List;

public class OrdineApi {

    public static void registerRoutes(Gson gson) {
        OrdineController controller = new OrdineController();
        FumettoController fumettoController = new FumettoController();

        path("/ordini", () -> {
            options("/*", (req, res) -> {
                String reqHeaders = req.headers("Access-Control-Request-Headers");
                if (reqHeaders != null) res.header("Access-Control-Allow-Headers", reqHeaders);
                String reqMethods = req.headers("Access-Control-Request-Method");
                if (reqMethods != null) res.header("Access-Control-Allow-Methods", reqMethods);
                res.header("Access-Control-Allow-Origin", "*");
                res.status(200);
                return "";
            });

            get("", (req, res) -> {
                res.type("application/json");
                return gson.toJson(controller.findAll());
            });

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

            post("", "application/json", (req, res) -> {
                res.type("application/json");
                Ordine nuovo = gson.fromJson(req.body(), Ordine.class);
                boolean successo = controller.create(nuovo);
                if (successo) {
                    List<RigaOrdine> righe = nuovo.getRigheOrdine();
                    for (RigaOrdine ro : righe) {
                        Fumetto f = fumettoController.findById(ro.getIdFumetto());
                        if (f != null) {
                            int newQty = f.getQuantitaDisponibile() - ro.getQuantita();
                            f.setQuantitaDisponibile(Math.max(newQty, 0));
                            fumettoController.update(f);
                        }
                    }
                    res.status(201);
                    return gson.toJson(nuovo);
                } else {
                    res.status(400);
                    return gson.toJson("Errore creazione ordine");
                }
            });

            put("/:id", "application/json", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                Ordine aggiornato = gson.fromJson(req.body(), Ordine.class);
                aggiornato.setId(id);
                boolean successo = controller.update(aggiornato);
                res.status(successo ? 200 : 400);
                return gson.toJson(successo ? "Ordine aggiornato" : "Errore aggiornamento ordine");
            });

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

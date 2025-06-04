package zip.boundary.api;

import static spark.Spark.*;

import com.google.gson.Gson;
import zip.control.PagamentoController;
import zip.entity.Pagamento;

import java.util.List;

public class PagamentoApi {

    public static void registerRoutes(Gson gson) {
        PagamentoController controller = new PagamentoController();

        path("/pagamenti", () -> {

            get("", (req, res) -> {
                res.type("application/json");
                List<Pagamento> lista = controller.findAll();
                return gson.toJson(lista);
            });

            get("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                Pagamento p = controller.findById(id);
                if (p != null) {
                    return gson.toJson(p);
                } else {
                    res.status(404);
                    return gson.toJson("Pagamento non trovato");
                }
            });

            get("/ordine/:ordineId", (req, res) -> {
                res.type("application/json");
                int ordineId = Integer.parseInt(req.params("ordineId"));
                Pagamento p = controller.findByOrdineId(ordineId);
                if (p != null) {
                    return gson.toJson(p);
                } else {
                    res.status(404);
                    return gson.toJson("Pagamento per ordine non trovato");
                }
            });

            post("", (req, res) -> {
                res.type("application/json");
                Pagamento nuovo = gson.fromJson(req.body(), Pagamento.class);
                boolean successo = controller.create(nuovo);
                res.status(successo ? 201 : 400);
                return gson.toJson(successo ? "Pagamento creato" : "Errore creazione pagamento");
            });

            put("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                Pagamento aggiornato = gson.fromJson(req.body(), Pagamento.class);
                aggiornato.setIdPagamento(id);
                boolean successo = controller.update(aggiornato);
                res.status(successo ? 200 : 400);
                return gson.toJson(successo ? "Pagamento aggiornato" : "Errore aggiornamento pagamento");
            });

            delete("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                boolean successo = controller.delete(id);
                res.status(successo ? 204 : 404);
                return gson.toJson(successo ? "Pagamento eliminato" : "Pagamento non trovato");
            });

        });
    }
}

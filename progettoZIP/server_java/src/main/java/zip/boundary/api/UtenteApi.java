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
            options("/*", (req, res) -> {
                String reqHeaders = req.headers("Access-Control-Request-Headers");
                if (reqHeaders != null) {
                    res.header("Access-Control-Allow-Headers", reqHeaders);
                }
                String reqMethods = req.headers("Access-Control-Request-Method");
                if (reqMethods != null) {
                    res.header("Access-Control-Allow-Methods", reqMethods);
                }
                res.header("Access-Control-Allow-Origin", "*");
                res.status(200);
                return "";
            });

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

            post("", "application/json", (req, res) -> {
                res.type("application/json");
                Utente nuovo = gson.fromJson(req.body(), Utente.class);
                boolean successo = controller.create(nuovo);
                res.status(successo ? 201 : 400);
                return gson.toJson(successo ? "Utente creato" : "Errore creazione utente");
            }, gson::toJson);

            put("/:id", "application/json", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                Utente aggiornato = gson.fromJson(req.body(), Utente.class);
                aggiornato.setId(id);
                boolean successo = controller.update(aggiornato);
                res.status(successo ? 200 : 400);
                return gson.toJson(successo ? "Utente aggiornato" : "Errore aggiornamento utente");
            }, gson::toJson);

            delete("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));
                boolean successo = controller.delete(id);
                res.status(successo ? 204 : 404);
                return gson.toJson(successo ? "Utente eliminato" : "Utente non trovato");
            }, gson::toJson);

            post("/login", "application/json", (req, res) -> {
                res.type("application/json");
                Utente creds = gson.fromJson(req.body(), Utente.class);
                Utente logged = controller.authenticate(creds.getEmail(), creds.getPassword());
                if (logged != null) {
                    res.status(200);
                    return gson.toJson(logged);
                } else {
                    res.status(401);
                    return gson.toJson("Email o password non validi");
                }
            }, gson::toJson);

        }); 
    }
}

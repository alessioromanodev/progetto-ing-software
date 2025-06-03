package zip.boundary;

import static spark.Spark.*;
import com.google.gson.Gson;
import java.sql.*;
import zip.database.DBManager;
import zip.boundary.api.ConsegnaApi;
import zip.boundary.api.OrdineApi;
import zip.boundary.api.PagamentoApi;
import zip.boundary.api.UtenteApi;
import zip.boundary.api.FumettoApi;
import zip.boundary.api.NewsletterApi;

public class ApiServer {
    public static void main(String[] args) {
        port(8080);


        try {
            Connection conn = DBManager.getStarterConnection();
            System.out.println(conn);
        } catch (SQLException e) {
            Throwable cause = e.getCause();
            if (cause instanceof ClassNotFoundException) {
                // Qui il Class.forName è fallito: driver H2 non presente nel classpath
                System.err.println("Errore: driver H2 non trovato. Controlla che h2.jar sia nel classpath.");
                e.printStackTrace();
            } else {
                // Qualsiasi altro errore di connessione (es. file DB mancante, credenziali sbagliate, ecc.)
                System.err.println("Errore generico di connessione a H2: " + e.getMessage());
                e.printStackTrace();
            }
        }


        Gson gson = new Gson();
    
        FumettoApi.registerRoutes(gson);
        OrdineApi.registerRoutes(gson);
        PagamentoApi.registerRoutes(gson);
        UtenteApi.registerRoutes(gson);
        ConsegnaApi.registerRoutes(gson);
        NewsletterApi.registerRoutes(gson);
    }
}
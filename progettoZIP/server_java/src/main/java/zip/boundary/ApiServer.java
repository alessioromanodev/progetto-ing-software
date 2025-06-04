package zip.boundary;

import static spark.Spark.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
                System.err.println("Errore: driver H2 non trovato. Controlla che h2.jar sia nel classpath.");
                e.printStackTrace();
            } else {
                System.err.println("Errore generico di connessione a H2: " + e.getMessage());
                e.printStackTrace();
            }
        }


        Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
            new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
        )
        .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, context) ->
            LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        )
        .create();
    
        FumettoApi.registerRoutes(gson);
        OrdineApi.registerRoutes(gson);
        PagamentoApi.registerRoutes(gson);
        UtenteApi.registerRoutes(gson);
        ConsegnaApi.registerRoutes(gson);
        NewsletterApi.registerRoutes(gson);
    }
}
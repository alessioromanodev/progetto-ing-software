package zip.database;


import java.sql.*;

public class DBManager {
    private static final String STARTER_URL =
        "jdbc:h2:./fumetteriaZIP;" +
        "INIT=RUNSCRIPT FROM 'classpath:init.sql';" +
        "DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE";
    private static final String URL =
        "jdbc:h2:./fumetteriaZIP;";
    private static final String USER = "sa";
    private static final String PASS = "";

    public static Connection getStarterConnection() throws SQLException {
        
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver H2 non trovato", e);
        }
        
        return DriverManager.getConnection(STARTER_URL, USER, PASS);
    }

    public static Connection getConnection() throws SQLException {
        
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver H2 non trovato", e);
        }
        
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void close(AutoCloseable... resources) {
        for (AutoCloseable r : resources) {
            if (r != null) try { r.close(); } catch (Exception ignored) {}
        }
    }
}

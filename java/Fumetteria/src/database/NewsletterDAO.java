package database;
import entity.Fumetto;
import entity.Newsletter;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;

import java.util.Base64;

import exception.DAOException;
import exception.DBConnectionException;

public class NewsletterDAO {

    public static void createNewsletter(Newsletter newsletter) throws DAOException, DBConnectionException {
		
		try {
			
			Connection conn = DBManager.getConnection();

			String query = "INSERT INTO newsletter VALUES (null,?,?,?);";

			try {
				PreparedStatement stmt = conn.prepareStatement(query);
				
                stmt.setString(1, newsletter.getTitolo());
				stmt.setString(2, newsletter.getDescrizione());
				stmt.setString(3,newsletter.getData());
				

				stmt.executeUpdate();

			}catch(SQLException e) {
				throw new DAOException("Errorenewsletter");
			} finally {
				DBManager.closeConnection();
			}

		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione database");
		}

	}
}

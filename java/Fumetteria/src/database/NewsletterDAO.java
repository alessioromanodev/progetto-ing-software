package database;

import entity.Newsletter;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;




import exception.DAOException;
import exception.DBConnectionException;

public class NewsletterDAO {

    public static void createNewsletter(Newsletter newsletter) throws DAOException, DBConnectionException {
		
		try {
			
			Connection conn = DBManager.getConnection();

			String query = "INSERT INTO Newsletter VALUES (null,?,?,?);";

			try {
				PreparedStatement stmt = conn.prepareStatement(query);
				
                stmt.setString(1, newsletter.getTitolo());
				stmt.setString(2, newsletter.getDescrizione());
				stmt.setDate(3, java.sql.Date.valueOf(newsletter.getData()));

				

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

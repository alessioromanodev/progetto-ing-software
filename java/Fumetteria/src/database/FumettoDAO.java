package database;
import entity.Fumetto;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;

import java.util.Base64;

import exception.DAOException;
import exception.DBConnectionException;

public class FumettoDAO {

    public static void createFumetto(Fumetto fumetto) throws DAOException, DBConnectionException {
		
		try {
			
			Connection conn = DBManager.getConnection();

			String query = "INSERT INTO fumetti VALUES (null,?,?,?,?,?,?,?,?,?);";

			try {
				PreparedStatement stmt = conn.prepareStatement(query);
				
                stmt.setString(1, fumetto.getNomeSerie());
				stmt.setString(2, fumetto.getAnnoSerie());
				stmt.setString(3,fumetto.getTitolo());
				stmt.setString(4, fumetto.getGenere());
				stmt.setString(5, fumetto.getCasaEditrice());
                stmt.setString(6, Base64.getEncoder().encodeToString(fumetto.getImmagineCopertina()));
                stmt.setString(7,fumetto.getDescrizione());
                stmt.setDouble(8,fumetto.getPrezzo());
                stmt.setInt(9,fumetto.getQuantitaDisponibile());

				stmt.executeUpdate();

			}catch(SQLException e) {
				throw new DAOException("Errore scrittura biglietto");
			} finally {
				DBManager.closeConnection();
			}

		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione database");
		}

	}
}

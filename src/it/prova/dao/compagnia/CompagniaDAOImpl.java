package it.prova.dao.compagnia;

import it.prova.dao.AbstractMySQLDAO;
import it.prova.model.Compagnia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompagniaDAOImpl extends AbstractMySQLDAO implements CompagniaDAO {

//	prima la connessione =====================================================

	public CompagniaDAOImpl(Connection connection) {
		super(connection);
	}

//	==========================================================================
	@Override
	public List<Compagnia> list() throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Compagnia> result = new ArrayList<Compagnia>();
		Compagnia compagniaTemp = null;

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from Compagnia")) {

			while (rs.next()) {
				compagniaTemp = new Compagnia();
				compagniaTemp.setRagioneSociale(rs.getString("RagioneSociale"));
				compagniaTemp.setFatturatoAnnuo(rs.getInt("FatturatoAnnuo"));
				compagniaTemp.setDataFondazione(rs.getDate("DataFondazione"));
				compagniaTemp.setId(rs.getLong("ID"));
				result.add(compagniaTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Compagnia get(Long idInput) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Compagnia result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from Compagnia where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Compagnia();
					result.setRagioneSociale(rs.getString("RagioneSociale"));
					result.setFatturatoAnnuo(rs.getInt("FatturatoAnnuo"));
					result.setDataFondazione(rs.getDate("DataFondazione"));
					result.setId(rs.getLong("ID"));

				} else {
					result = null;
				}
			} // niente catch qui

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Compagnia compagniaInput) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (compagniaInput == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO Compagnia (ragionesociale, fatturatoannuo, datafondazione) VALUES (?, ?, ?);")) {
			ps.setString(1, compagniaInput.getRagioneSociale());
			ps.setInt(2, compagniaInput.getFatturatoAnnuo());
			// quando si fa il setDate serve un tipo java.sql.Date
			ps.setDate(3, new java.sql.Date(compagniaInput.getDataFondazione().getTime()));
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int update(Compagnia compagniaInput) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (compagniaInput == null || compagniaInput.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE Compagnia SET ragionesociale=?, fatturatoannuo=?, datafondazione=? where id=?;")) {
			ps.setString(1, compagniaInput.getRagioneSociale());
			ps.setInt(2, compagniaInput.getFatturatoAnnuo());
			// quando si fa il setDate serve un tipo java.sql.Date
			ps.setDate(3, new java.sql.Date(compagniaInput.getDataFondazione().getTime()));
			ps.setLong(4, compagniaInput.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Compagnia compagniaInput) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (compagniaInput == null || compagniaInput.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM Compagnia WHERE ID=?")) {
			ps.setLong(1, compagniaInput.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

//	==================================================================================

	@Override
	public List<Compagnia> findByExample(Compagnia input) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Compagnia> findAllByDataAssunzioneMaggioreDi(Date dateCreatedInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (dateCreatedInput == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Compagnia> result = new ArrayList<Compagnia>();
		Compagnia compagniaTemp = null;

		try (PreparedStatement ps = connection.prepareStatement("select * from compagnia c inner join impiegato i on c.id = i.id where i.dataAssunzione < ?")) {
			// quando si fa il setDate serve un tipo java.sql.Date
			ps.setDate(1, new java.sql.Date(dateCreatedInput.getTime()));

			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					compagniaTemp = new Compagnia();
					compagniaTemp.setRagioneSociale(rs.getString("RAGIONESOCIALE"));
					compagniaTemp.setFatturatoAnnuo(rs.getInt("FATTURATOANNUO"));
					compagniaTemp.setDataFondazione(rs.getDate("DATAFONDAZIONE"));
					compagniaTemp.setId(rs.getLong("ID"));
					result.add(compagniaTemp);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Compagnia> findAllByRagioneSocialeContiene(String ragioneSocialeInput) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
				if (isNotActive())
					throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

				if (ragioneSocialeInput == null)
					throw new Exception("Valore di input non ammesso.");

				ArrayList<Compagnia> result = new ArrayList<Compagnia>();
				Compagnia compagniaTemp = null;

				try (PreparedStatement ps = connection.prepareStatement("select * from compagnia c where c.ragioneSociale like ?;")) {
					// quando si fa il setDate serve un tipo java.sql.Date
					ps.setString(1, ragioneSocialeInput + "%");

					try (ResultSet rs = ps.executeQuery();) {
						while (rs.next()) {
							compagniaTemp = new Compagnia();
							compagniaTemp.setRagioneSociale(rs.getString("RAGIONESOCIALE"));
							compagniaTemp.setFatturatoAnnuo(rs.getInt("FATTURATOANNUO"));
							compagniaTemp.setDataFondazione(rs.getDate("DATAFONDAZIONE"));
							compagniaTemp.setId(rs.getLong("ID"));
							result.add(compagniaTemp);
						}
					} 

				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
				return result;
	}

}

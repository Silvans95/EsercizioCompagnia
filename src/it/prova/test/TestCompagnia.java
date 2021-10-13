package it.prova.test;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.prova.connection.MyConnection;
import it.prova.dao.Constants;
import it.prova.dao.compagnia.CompagniaDAO;
import it.prova.dao.compagnia.CompagniaDAOImpl;
import it.prova.dao.impiegato.ImpiegatoDAO;
import it.prova.dao.impiegato.ImpiegatoDAOImpl;
import it.prova.model.Compagnia;
import it.prova.model.Impiegato;

public class TestCompagnia {

	public static void main(String[] args) {
		// test compagnia
		Compagnia newCompagniaInstance = new Compagnia("srl", 32);
		newCompagniaInstance.setDataFondazione(new Date());

		CompagniaDAO compagniarDAOInstance = null;

		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
		
			compagniarDAOInstance = new CompagniaDAOImpl(connection);

			System.out.println("In tabella ci sono " + compagniarDAOInstance.list().size() + " elementi.");
			compagniarDAOInstance.insert(newCompagniaInstance);
			Long idDaUsarePerRicerca = 1L;
			Compagnia founded = compagniarDAOInstance.get(idDaUsarePerRicerca);
			if (founded != null)
				System.out.println("User con id " + idDaUsarePerRicerca + ": " + founded);
			else
				System.out.println("User con id " + idDaUsarePerRicerca + " non trovato");
			
			
			
			System.out.println("-----prima del find data----");
			String dataDaCuiPartire = "2019-01-31";
			Date dateCreatedInput = new SimpleDateFormat("yyyy-MM-dd").parse(dataDaCuiPartire);
			for (Compagnia userItem : compagniarDAOInstance.findAllByDataAssunzioneMaggioreDi(dateCreatedInput)) {
				System.out.println(userItem);
			}
			System.out.println("-----Prima del find all contiene ragioneSociale----");
			String stringaInput = "srl";
			for (Compagnia userItem : compagniarDAOInstance.findAllByRagioneSocialeContiene(stringaInput)) {
				System.out.println(userItem);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// test Impiegato

		Impiegato newImpiegatoInstance = new Impiegato("Silvano", "Paloni", "PLNSVN95D08H501R");
		newImpiegatoInstance.setDataNascita(new Date());
		newImpiegatoInstance.setDataAssunzione(new Date());

		ImpiegatoDAO impiegatoDAOInstance = null;

		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			// ecco chi 'inietta' la connection: il chiamante
			impiegatoDAOInstance = new ImpiegatoDAOImpl(connection);

			System.out.println("In tabella ci sono " + impiegatoDAOInstance.list().size() + " elementi.");
			impiegatoDAOInstance.insert(newImpiegatoInstance);
			Long idDaUsarePerRicerca = 1L;
			Impiegato founded = impiegatoDAOInstance.get(idDaUsarePerRicerca);
			if (founded != null)
				System.out.println("User con id " + idDaUsarePerRicerca + ": " + founded);
			else
				System.out.println("User con id " + idDaUsarePerRicerca + " non trovato");
			impiegatoDAOInstance.delete(founded);
			System.out.println("In tabella ci sono " + compagniarDAOInstance.list().size() + " elementi.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
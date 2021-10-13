package it.prova.dao.compagnia;

import java.util.Date;
import java.util.List;

import it.prova.dao.IBaseDAO;
import it.prova.model.Compagnia;

public interface CompagniaDAO extends IBaseDAO<Compagnia> {

	public List<Compagnia> findAllByDataAssunzioneMaggioreDi(Date dataInput) throws Exception;
	public List<Compagnia> findAllByRagioneSocialeContiene(String stringInput) throws Exception;

}

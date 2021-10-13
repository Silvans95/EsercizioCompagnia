package it.prova.dao.impiegato;

import java.util.Date;
import java.util.List;

import it.prova.dao.IBaseDAO;
import it.prova.model.Compagnia;
import it.prova.model.Impiegato;

public interface ImpiegatoDAO extends IBaseDAO<Impiegato>{
	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput);
	public int countByDataFondazioneCompagniaMaggioreDi(Date dataInput);
	public List<Impiegato> findAllByComnpagniaConFatturatoMaggioreDi(int fatturatoInput);
	public List<Impiegato> findAllErroriAssunzione();
	
	
}

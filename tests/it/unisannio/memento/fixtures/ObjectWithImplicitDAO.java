package it.unisannio.memento.fixtures;

import javax.persistence.Id;

import it.unisannio.memento.CustomDAO;
import it.unisannio.memento.GenericDAO;

public class ObjectWithImplicitDAO implements CustomDAO<ObjectWithImplicitDAO.DAO> {
	public static interface DAO extends GenericDAO<Integer, ObjectWithImplicitDAO> {
		
	}
	
	@SuppressWarnings("unused")
	@Id
	private int id;
}

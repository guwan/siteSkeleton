package com.guwan.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.guwan.repository.UserRepository;

public abstract class JpaDaoSupport{

	@PersistenceContext protected EntityManager em;

	/**
	 * Configure the entity manager to be used.
	 * 
	 * @param em the {@link EntityManager} to set.
	 */
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}


	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
}

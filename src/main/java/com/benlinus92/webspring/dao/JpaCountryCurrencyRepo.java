package com.benlinus92.webspring.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class JpaCountryCurrencyRepo implements CountryCurrencyRepo {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CountryCurrency> getListByCountryId(String countryId) {
		Query q = em.createQuery("SELECT r from CountryCurrency r WHERE r.country = :countryId", CountryCurrency.class);
		q.setParameter("countryId", countryId);
		return (List<CountryCurrency>) q.getResultList();
	}	
	@Override
	public void insertCurrency(CountryCurrency entity) {
		try {
			em.persist(entity);
		} catch(PersistenceException e) {
			e.printStackTrace();
		}
	}
}

package com.benlinus92.webspring.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
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
	@SuppressWarnings("unchecked")
	@Override
	public CountryCurrency findById(int id) {
		Query q = em.createQuery("SELECT r from CountryCurrency r", CountryCurrency.class);
		List<CountryCurrency> list = (List<CountryCurrency>)q.getResultList();
		return list.get(0);
	}
	@Override
	public CountryCurrency findByName(String country) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void updateCountryCurrencyByName(String country) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteCountryCurrency() {
		// TODO Auto-generated method stub
		
	}
}

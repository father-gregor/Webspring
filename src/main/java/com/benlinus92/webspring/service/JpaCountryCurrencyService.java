package com.benlinus92.webspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benlinus92.webspring.dao.CountryCurrency;
import com.benlinus92.webspring.dao.CountryCurrencyRepo;

@Service("employeeService")
public class JpaCountryCurrencyService implements CountryCurrencyService {

	@Autowired
	private CountryCurrencyRepo dao;
	
	@Override
	public CountryCurrency findById(int id) {
		return dao.findById(id);
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

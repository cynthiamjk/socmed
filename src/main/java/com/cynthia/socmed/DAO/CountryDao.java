package com.cynthia.socmed.DAO;

import com.cynthia.socmed.models.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryDao extends CrudRepository<Country, Integer> {

        public Country findByName(String name);

}

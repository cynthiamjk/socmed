package com.cynthia.socmed.services;

import com.cynthia.socmed.DAO.CountryDao;
import com.cynthia.socmed.models.Country;
import com.cynthia.socmed.models.Event;
import com.cynthia.socmed.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    CountryDao countryDao;



    public String countryFlag(User user) {
        Country c = user.getCountry();
        String name = c.getName().toLowerCase();
        String imagePath = "flag/png/" + name + ".png";
        return imagePath;
    }

    public String countryFlagEvent(Event event) {
        Country c = event.getLocation();
        String name = c.getName().toLowerCase();
        String imagePath = "flag/png/" + name + ".png";
        return imagePath;
    }


    public List<Country> countries() {
        return (List<Country>) countryDao.findAll();
    }


    public Country findByName(String countryName) {
        return countryDao.findByName(countryName);
    }

    public List<Country> findAll() {
        return (List<Country>) countryDao.findAll();
    }

    public void save(Country c) { countryDao.save(c);}
}

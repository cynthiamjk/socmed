package com.cynthia.socmed.controllers;

import com.cynthia.socmed.models.Country;
import com.cynthia.socmed.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
    public class IndexController {



        @Autowired
        CountryService countryService;




        @RequestMapping(value ="/")
        public String index(ModelMap model) {

            List <Country> countries = (List<Country>) countryService.findAll();
            model.addAttribute("countries", countries);


            return "homePage";

        }
    }


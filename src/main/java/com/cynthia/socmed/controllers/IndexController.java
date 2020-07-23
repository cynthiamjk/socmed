package com.cynthia.socmed.controllers;

import com.cynthia.socmed.DAO.EventDao;
import com.cynthia.socmed.models.Country;
import com.cynthia.socmed.models.Event;
import com.cynthia.socmed.services.CountryService;
import com.cynthia.socmed.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    EventDao eventDao;

    @Autowired
    CountryService countryService;

    @Autowired
    EventService eventService;


    @RequestMapping(value ="/")
    public String index(ModelMap model) {

        List <Country> countries = (List<Country>) countryService.findAll();
        model.addAttribute("countries", countries);
       List<Event> events = eventService.homePageEvents();
        if(events.size() > 0) {
            model.addAttribute("events", events);
        } else {
            model.addAttribute("NoEventsMess", "No events to show yet");
        }
        return "homePage";

    }
}
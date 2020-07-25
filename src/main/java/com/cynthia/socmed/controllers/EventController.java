package com.cynthia.socmed.controllers;


import com.cynthia.socmed.models.Country;
import com.cynthia.socmed.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@SessionAttributes("user")
public class EventController {


    @Autowired
    EventService eventService;

    @RequestMapping(value = "/newEvent", method = RequestMethod.POST)
    public String eventForm(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "startTime") LocalTime startTime,
            @RequestParam(value = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "endTime") LocalTime endTime,
            @RequestParam(value = "country") Country c,
            @RequestParam(value = "editPicture", required = false) MultipartFile editPicture,
            @RequestParam(value = "url", required = false) String url,
            @RequestParam(value = "ticketUrl", required = false) String ticketUrl,
            RedirectAttributesModelMap redirectAttributesModelMap) throws IOException {

        eventService.addEvent(name, startDate, startTime, endDate, endTime, c,
                editPicture, url, ticketUrl, redirectAttributesModelMap);

        return "redirect:addEvent";



    }




}

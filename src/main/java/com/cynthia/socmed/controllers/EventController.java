package com.cynthia.socmed.controllers;


import com.cynthia.socmed.DAO.CountryDao;
import com.cynthia.socmed.DAO.EventDao;
import com.cynthia.socmed.models.Country;
import com.cynthia.socmed.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@SessionAttributes("user")
public class EventController {

    @Autowired
    EventDao eventDao;

    @Autowired
    CountryDao countryDao;

    @RequestMapping(value = "/newEvent", method = RequestMethod.POST)
public String eventForm (
        @RequestParam (value="name") String name,
                         @RequestParam (value="startDate")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate startDate,
                         @RequestParam (value="startTime") LocalTime startTime,
                         @RequestParam (value="endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                         @RequestParam (value="endTime") LocalTime endTime,
                         @RequestParam (value="country") Country c,
        @RequestParam(value="editPicture", required = false) MultipartFile editPicture,
        ModelMap model) throws IOException {
        Event event = new Event();


        List<Event> events = (List<Event>) eventDao.findAll();
        if(!events.isEmpty()) {
        List<Integer> eventsIds = new ArrayList<>();
        int num;
        String n;
        for(Event ev : events) {
            eventsIds.add(ev.getId());
        }
        if(!eventsIds.isEmpty()) {
            num = Collections.max(eventsIds) + 1;
        } else {
            num = 0;
        }
        n = Integer.toString(num);
        File convertFile = new File("C:\\Users\\CynthiaM\\Desktop\\socmed\\images\\"+n+".jpg");
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(String.valueOf(convertFile));
        fout.write(editPicture.getBytes());
        fout.close();
        event.setEventPicture(convertFile);
        event.setPicturePath("images/"+n+".jpg");

    }
    event.setName(name);
    event.setStartDate(startDate);
    event.setTimeStart(startTime.minusHours(1));
    event.setEndDate(endDate);
    event.setTimeEnd(endTime.minusHours(1));
    event.setLocation(c);
    eventDao.save(event);

    return "redirect:addEvent";



}




}

package com.cynthia.socmed.controllers;


import com.cynthia.socmed.comp.EventValidator;
import com.cynthia.socmed.models.Country;
import com.cynthia.socmed.models.Event;
import com.cynthia.socmed.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    EventValidator eventValidator;

    @RequestMapping(value = "/newEvent", method = RequestMethod.POST)
    public String eventForm (@ModelAttribute("eventForm") Event v, ModelMap modelMap,
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "startDate", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "startTime",  required = true) LocalTime startTime,
            @RequestParam(value = "endDate",  required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "endTime",  required = true) LocalTime endTime,
            @RequestParam(value = "country",  required = true) Country c,
            @RequestParam(value = "editPicture", required = false) MultipartFile editPicture,
            @RequestParam(value = "url", required = false) String url,
            @RequestParam(value = "ticketUrl", required = false) String ticketUrl,
            RedirectAttributesModelMap redirectAttributesModelMap, BindingResult bindingResult) throws IOException {

        eventValidator.validate(v, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributesModelMap.addFlashAttribute("err", bindingResult.getFieldError().getDefaultMessage());
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return "redirect:addEvent";
        } else if (((url.equals("")) || (url.startsWith("https://"))
                  && (((ticketUrl.equals("")) || (url.startsWith("https://www.festicket.com")))))) {
            eventService.addEvent(editPicture, v);
            return "redirect:addEvent";
        } else {
            redirectAttributesModelMap.addFlashAttribute("err", "Invalid Url");

        }

        return "redirect:addEvent";

    }




}

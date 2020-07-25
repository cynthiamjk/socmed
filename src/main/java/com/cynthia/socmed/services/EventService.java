package com.cynthia.socmed.services;

import com.cynthia.socmed.DAO.EventDao;
import com.cynthia.socmed.comp.EventTimeComparator;
import com.cynthia.socmed.models.Country;
import com.cynthia.socmed.models.Event;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
public class EventService {

    @Autowired
    EventDao eventDao;

    public List<Event> homePageEvents() {
        List<Event> events = (List<Event>) eventDao.findAll();
        List<Event> latestEvents = new ArrayList<>();
        if (events.size() >= 3) {
            Event[] evenArr = new Event[events.size()];
            Event[] sortedEvents = events.toArray(evenArr);
            Arrays.sort(sortedEvents, new EventTimeComparator());
            latestEvents.add(sortedEvents[0]);
            latestEvents.add(sortedEvents[1]);
            latestEvents.add(sortedEvents[2]);
            return latestEvents;

        } else if (events.size() == 2 ) {
            Event[] evenArr = new Event[events.size()];
            Event[] sortedEvents = events.toArray(evenArr);
            Arrays.sort(sortedEvents, new EventTimeComparator());
            latestEvents.add(sortedEvents[0]);
            latestEvents.add(sortedEvents[1]);

            return latestEvents;

        } else if (events.size() == 1) {
            Event[] evenArr = new Event[events.size()];
            Event[] sortedEvents = events.toArray(evenArr);
            Arrays.sort(sortedEvents, new EventTimeComparator());
            latestEvents.add(sortedEvents[0]);

            return latestEvents;
        }


        return latestEvents;
    }


    public void addEvent (String name, LocalDate startDate, LocalTime startTime, LocalDate  endDate,
                          LocalTime endTime, Country c, MultipartFile editPicture, String url, String ticketUrl,
                          RedirectAttributesModelMap redirectAttributesModelMap) throws IOException {

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
        String safeName = Jsoup.clean(name, Whitelist.basic());
        event.setName(safeName);
        event.setStartDate(startDate);
        event.setTimeStart(startTime);
        event.setEndDate(endDate);
        event.setTimeEnd(endTime);
        event.setLocation(c);

        if(!url.startsWith("https://")) {
            redirectAttributesModelMap.addFlashAttribute("urlError", "Invalid url");
        }
        if(!ticketUrl.contains("https://www.festicket.com")) {
            redirectAttributesModelMap.addFlashAttribute("urlErrorTicket", "Invalid url");
        }

        String safeUrl = Jsoup.clean(url, Whitelist.basic());
        event.setUrl(safeUrl);
        String safeTicketUrl = Jsoup.clean(ticketUrl, Whitelist.basic());
        event.setTicketUrl(safeTicketUrl);
        eventDao.save(event);

    }

}

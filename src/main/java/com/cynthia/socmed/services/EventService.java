package com.cynthia.socmed.services;

import com.cynthia.socmed.DAO.EventDao;
import com.cynthia.socmed.comp.EventTimeComparator;
import com.cynthia.socmed.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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



}

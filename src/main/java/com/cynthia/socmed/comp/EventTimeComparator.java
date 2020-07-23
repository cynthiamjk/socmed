package com.cynthia.socmed.comp;

import com.cynthia.socmed.models.Event;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class EventTimeComparator implements Comparator<Event> {

    @Override
    public int compare(Event ev1, Event ev2) {
        if (ev1.getStartDate().isBefore(ev2.getStartDate()))
            return -1;
        if (ev1.getStartDate().isAfter(ev2.getStartDate()))
            return 1;
        return 0;
    }
}

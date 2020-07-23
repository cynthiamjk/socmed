package com.cynthia.socmed.DAO;

import com.cynthia.socmed.models.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventDao  extends CrudRepository<Event, Integer> {


}

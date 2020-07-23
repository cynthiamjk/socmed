package com.cynthia.socmed.DAO;

import com.cynthia.socmed.models.LinkPreview;
import org.springframework.data.repository.CrudRepository;

public interface LinkPreviewDao extends CrudRepository<LinkPreview, Integer> {

  public LinkPreview findById(int id);

}

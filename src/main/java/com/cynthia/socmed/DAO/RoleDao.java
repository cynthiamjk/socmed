package com.cynthia.socmed.DAO;

import com.cynthia.socmed.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleDao extends CrudRepository<Role, Integer> {

    public Role findById(int id);

    public Role findByName(String name);
}

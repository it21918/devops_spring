package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.Lesson;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repoRole;

    public List<Role> getAllRoles() {
        return repoRole.findAll();
    }


}

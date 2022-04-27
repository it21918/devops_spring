package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.User;
import java.util.List;

public interface UserServiceInterface {
    public List<User> listAll() ;

    public User save(User user) throws Exception;

    public User get(Long id) ;

    public void delete(Long id) ;
}

package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.Request;

import java.util.List;

public interface RequestServiceInterface {

    List<Request> getAllRequests();

    void saveRequest(Request request);

    Request getRequestById(Long id);

    void deleteRequestById(Long id);
}

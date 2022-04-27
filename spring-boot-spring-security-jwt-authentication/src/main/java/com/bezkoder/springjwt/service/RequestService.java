package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.Request;
import com.bezkoder.springjwt.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService implements RequestServiceInterface {

    @Autowired
    private RequestRepository repoReq;


    @Override
    public List<Request> getAllRequests() {
        return repoReq.findAll();
    }

    @Override
    public void saveRequest(Request request) {
        this.repoReq.save(request);
    }

    @Override
    public Request getRequestById(Long id) {
        return repoReq.getRequestById(id);
    }

    @Override
    public void deleteRequestById(Long id) {
        this.repoReq.deleteById(id);
    }

    public List<Request> getStudentRequests(Long id) {
        return repoReq.getStudentRequests(id);
    }

    public List<Request> getTeacherRequests(Long id) {
        return repoReq.getTeachersRequests(id);
    }

}

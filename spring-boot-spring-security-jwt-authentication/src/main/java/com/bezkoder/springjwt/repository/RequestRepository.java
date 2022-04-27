package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("SELECT r FROM Request r WHERE r.sender.id = ?1")
    List<Request> getStudentRequests(Long id);

    @Query("SELECT r FROM Request r WHERE r.id = ?1")
    Request getRequestById(Long id);

    @Query("SELECT r FROM Request r WHERE r.receiver.id = ?1")
    List<Request> getTeachersRequests(Long id);

}

package com.bezkoder.springjwt.models;


import javax.persistence.*;

@Entity
@Table(name = "recommendation_letter")
public class RecommendationLetter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_id", nullable = false)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "requests_id", nullable = false)
    private Request requests;

    @Column(name = "text", nullable = false, length = 1000)
    private String text;

    @Column(name = "timestamp", nullable = false, length = 45)
    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Request getRequests() {
        return requests;
    }

    public void setRequests(Request requests) {
        this.requests = requests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
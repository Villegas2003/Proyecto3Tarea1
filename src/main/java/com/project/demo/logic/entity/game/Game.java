package com.project.demo.logic.entity.game;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "game")
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String imgURL;
    @CreationTimestamp
    @Column(updatable = false, name = "created at")
    private Date createdAT;
    @UpdateTimestamp
    @Column(updatable = false, name = "updated at")
    private Date updatedAT;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Date getCreatedAT() {
        return createdAT;
    }

    public void setCreatedAT(Date createdAT) {
        this.createdAT = createdAT;
    }

    public Date getUpdatedAT() {
        return updatedAT;
    }

    public void setUpdatedAT(Date updatedAT) {
        this.updatedAT = updatedAT;
    }
}

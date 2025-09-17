package com.education.course_management_spring_boot.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Entity
@Table(name = "courses")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 120, nullable = false)
    String title;

    @Column(length = 256, nullable = false)
    String description;

    @Column(nullable = false)
    int level;


    @Column(name = "is_published")
    boolean isPublished;

    public Course() {
    }

    public Course(String title, String description, int level, boolean isPublished) {
        this.title = title;
        this.description = description;
        this.level = level;
        this.isPublished = isPublished;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    /*
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    LocalDate createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    LocalDate updatedAt;
*/

}

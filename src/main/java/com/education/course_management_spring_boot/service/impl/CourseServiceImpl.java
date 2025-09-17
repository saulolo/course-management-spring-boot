package com.education.course_management_spring_boot.service.impl;

import com.education.course_management_spring_boot.domain.entity.Course;
import com.education.course_management_spring_boot.repository.CourseRepository;
import com.education.course_management_spring_boot.service.interfaces.ICourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements ICourseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);
    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Course saveOrUpdateCourse(Course course) {
        LOGGER.info("Creado o actualizado curso.");
        return courseRepository.save(course);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
        LOGGER.info("Curso eliminado con éxito.");
    }
}

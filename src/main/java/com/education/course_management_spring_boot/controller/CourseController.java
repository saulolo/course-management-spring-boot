package com.education.course_management_spring_boot.controller;

import com.education.course_management_spring_boot.domain.entity.Course;
import com.education.course_management_spring_boot.service.interfaces.ICourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final ICourseService courseService;

    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }


/*    @GetMapping
    public String home() {
        return "redirect:/course";
    }*/


    @GetMapping
    public String getAllCourses(Model model) {
        List<Course> courseList = courseService.getAllCourses();
        model.addAttribute("courseList", courseList);
        return "course/course";
    }
}

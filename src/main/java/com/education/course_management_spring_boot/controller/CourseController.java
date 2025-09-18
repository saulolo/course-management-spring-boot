package com.education.course_management_spring_boot.controller;

import com.education.course_management_spring_boot.domain.entity.Course;
import com.education.course_management_spring_boot.service.interfaces.ICourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    /**
     * Handles GET requests to the /viewCourses endpoint.
     * Retrieves a list of all courses and adds them to the model for display.
     *
     * @param model The Spring Model object to add attributes to.
     * @return The name of the Thymeleaf template to render, which is "course/course".
     */
    @GetMapping("/viewCourses")
    public String getAllCourses(Model model) {
        List<Course> courseList = courseService.getAllCourses();
        model.addAttribute("courseList", courseList);
        return "course/course";
    }


    @GetMapping("/forNewCourse")
    public String showFormAddCourse(Model model) {
        Course cou = new Course();
        model.addAttribute("cou", cou);
        model.addAttribute("pageTitle", "Nuevo Curso");
        return "course/formAddCourse";
    }

    @PostMapping("/createOrUpdateCourse")
    public String saveOrUpdateCourse(@ModelAttribute Course course, RedirectAttributes redirectAttributes) {
        try {
            courseService.saveOrUpdateCourse(course);
            redirectAttributes.addFlashAttribute("message", "El curso ha sido guradado con éxito. \uD83D\uDC4D");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/courses/viewCourses";
    }

}

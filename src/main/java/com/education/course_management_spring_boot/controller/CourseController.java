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

    /**
     * Muestra el formulario para crear un nuevo curso.
     * Añade un objeto de tipo Course al modelo para ser rellenado por el formulario.
     *
     * @param model Objeto de tipo Model de Spring para añadir atributos a la vista.
     * @return El nombre de la plantilla de Thymeleaf, "course/formAddCourse".
     */
    @GetMapping("/forNewCourse")
    public String showFormAddCourse(Model model) {
        Course cou = new Course();
        model.addAttribute("cou", cou);
        model.addAttribute("pageTitle", "Nuevo Curso");
        return "course/formAddCourse";
    }
    
    /**
     * Procesa la petición POST para guardar un curso nuevo o actualizar uno existente.
     * Utiliza @ModelAttribute para recibir los datos del formulario y los guarda a través del servicio.
     * Redirige a la página que muestra la lista de cursos con un mensaje de éxito o error.
     *
     * @param course             El objeto Course que se recibe del formulario.
     * @param redirectAttributes Utilizado para añadir mensajes que persistan después de la redirección.
     * @return La redirección a la página principal de cursos.
     */
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

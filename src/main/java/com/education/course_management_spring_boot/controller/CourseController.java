package com.education.course_management_spring_boot.controller;

import com.education.course_management_spring_boot.domain.entity.Course;
import com.education.course_management_spring_boot.service.interfaces.ICourseService;
import com.education.course_management_spring_boot.util.report.CoursePdfReportGenerator;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);
    private final ICourseService courseService;
    private final CoursePdfReportGenerator pdfGenerator;


    public CourseController(ICourseService courseService, CoursePdfReportGenerator pdfGenerator) {
        this.courseService = courseService;
        this.pdfGenerator = pdfGenerator;
    }


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
    @GetMapping("/formNewCourse")
    public String showFormAddCourse(Model model) {
        Course course = new Course();
        model.addAttribute("cou", course);
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
            redirectAttributes.addFlashAttribute("message", "El curso ha sido guardado con éxito. \uD83D\uDC4D");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al guardar el curso: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

        }
        return "redirect:/courses/viewCourses";
    }


    /**
     * Muestra el formulario para editar un curso.
     * @param id El ID del curso a editar.
     * @param model El objeto Model de Spring.
     * @return El nombre de la plantilla de Thymeleaf para el formulario.
     */
    @GetMapping("/formUpdateCourse/{id}")
    public String showFormUpdateCourse(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id).orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + id));
        model.addAttribute("pageTitle", "Editar Curso: " + id);
        model.addAttribute("cou", course);
        return "course/formAddCourse";
    }

    /**
     * Elimina un curso por su ID.
     * @param id El ID del curso a eliminar.
     * @param redirectAttributes Atributos para redirigir con un mensaje.
     * @return La redirección a la página de la lista de cursos.
     */
    @GetMapping("/deleteCourse/{id}")
    public String deleteCourseById(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        courseService.deleteCourseById(id);
        redirectAttributes.addFlashAttribute("message", "El curso ha sido eliminado con éxito. \uD83D\uDC4D");
        LOGGER.info("Curso con ID: {} eliminado con éxito. ✔", id);
        return "redirect:/courses/viewCourses";
    }

    /**
     * Genera y exporta un reporte en PDF de todos los cursos.
     * @param response La respuesta HTTP para enviar el archivo.
     * @throws IOException Si ocurre un error de E/S.
     */
    @GetMapping("/export/pdf")
    public void pdfReportGenerator(HttpServletResponse response) throws IOException {
        List<Course> courseList = courseService.getAllCourses();
        pdfGenerator.export(courseList, response);
    }

}

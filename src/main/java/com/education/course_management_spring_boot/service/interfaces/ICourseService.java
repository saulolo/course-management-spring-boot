package com.education.course_management_spring_boot.service.interfaces;

import com.education.course_management_spring_boot.domain.entity.Course;

import java.util.List;
import java.util.Optional;

public interface ICourseService {

    /**
     * Recupera una lista con todos los cursos.
     * @return Una lista de objetos {@link Course}
     */
    List<Course> getAllCourses();

    /**
     * Busca un curso por su ID.
     * @param id El ID dell curso.
     * @return Un {@link Optional} que contiene el curso si lo encuetra, un {@link Optional} vacio si no.
     */
    Optional<Course> getCourseById(Long id);

    /**
     * Guarda o Actuliza un curso en la base de datos.
     * Si el curso tiene un ID válido, se actualizará, de lo contrario se creará uno nuevo.
     * @param course El curso a actualizar o gurdar.
     * @return El objeto {@link Course} Actualizado o Guardado.
     */
    Course saveOrUpdateCourse(Course course);

    /**
     * Elimina un curso por su ID.
     * @param id El ID del curso.
     */
    void deleteCourseById(Long id);

}

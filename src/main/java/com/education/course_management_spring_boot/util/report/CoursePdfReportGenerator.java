package com.education.course_management_spring_boot.util.report;

import com.education.course_management_spring_boot.domain.entity.Course;
import jakarta.servlet.http.HttpServletResponse;
import org.openpdf.text.Font;
import org.openpdf.text.*;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class CoursePdfReportGenerator {

    private List<Course> courseList;

    public CoursePdfReportGenerator(List<Course> courseList) {
        this.courseList = courseList;
    }


    /**
     * Escribe el encabezado de la tabla para el reporte PDF.
     * @param pdfPTable El objeto de la tabla PDF.
     */
    private void writeTableHeader(PdfPTable pdfPTable) {
        PdfPCell pdfCell = new PdfPCell();
        pdfCell.setBackgroundColor(Color.BLUE);
        pdfCell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        pdfCell.setPhrase(new Phrase("ID", font));
        pdfPTable.addCell(pdfCell);

        pdfCell.setPhrase(new Phrase("Título", font));
        pdfPTable.addCell(pdfCell);

        pdfCell.setPhrase(new Phrase("Descripción", font));
        pdfPTable.addCell(pdfCell);

        pdfCell.setPhrase(new Phrase("Nivel", font));
        pdfPTable.addCell(pdfCell);

        pdfCell.setPhrase(new Phrase("Publicado", font));
        pdfPTable.addCell(pdfCell);

        pdfCell.setPhrase(new Phrase("Creado ", font));
        pdfPTable.addCell(pdfCell);

        pdfCell.setPhrase(new Phrase("Modificado", font));
        pdfPTable.addCell(pdfCell);
    }

    /**
     * Rellena la tabla PDF con los datos de la lista de cursos.
     * @param pdfPTable El objeto de la tabla PDF.
     * @param courseList La lista de cursos con los datos.
     */
    private void writeTableData(PdfPTable pdfPTable, List<Course> courseList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (Course course : courseList) {
            pdfPTable.addCell(String.valueOf(course.getId()));
            pdfPTable.addCell(String.valueOf(course.getTitle()));
            pdfPTable.addCell(String.valueOf(course.getDescription()));
            pdfPTable.addCell(String.valueOf(course.getLevel()));
            pdfPTable.addCell(String.valueOf(course.isPublished()));

            String createdAt = (course.getCreatedAt() != null) ? course.getCreatedAt().format(formatter) : "N/A";
            pdfPTable.addCell(createdAt);

            String updatedAt = (course.getUpdatedAt() != null) ? course.getUpdatedAt().format(formatter) : "N/A";
            pdfPTable.addCell(updatedAt);
        }
    }

    /**
     * Exporta la lista de cursos a un archivo PDF.
     * @param courseList La lista de cursos a exportar.
     * @param response La respuesta HTTP para enviar el archivo PDF.
     * @throws IOException Si ocurre un error de E/S.
     */
    public void export(List<Course> courseList, HttpServletResponse response) throws IOException {

        try (Document document = new Document(PageSize.A4)) {
             PdfWriter.getInstance(document, response.getOutputStream());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=courses_report_" + System.currentTimeMillis() + ".pdf";
            response.setHeader(headerKey, headerValue);
            response.setContentType("application/pdf");

            document.open();

            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setSize(18);
            font.setColor(Color.BLUE);

            Paragraph paragraph = new Paragraph("Lista de Cursos", font);
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(paragraph);

            PdfPTable pdfPTable = new PdfPTable(7);
            pdfPTable.setWidthPercentage(100f);
            pdfPTable.setWidths(new float[]{1.3f, 2.0f, 3.5f, 2.0f, 3.0f, 3.5f, 3.5f});
            pdfPTable.setSpacingBefore(10);

            writeTableHeader(pdfPTable);
            writeTableData(pdfPTable, courseList);

            document.add(pdfPTable);

        }
    }

}

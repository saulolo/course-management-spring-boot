package com.education.course_management_spring_boot.util.report;

import com.education.course_management_spring_boot.domain.entity.Course;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Genera reportes en formato Excel para cursos.
 */
@Component
public class CourseExcelReportGenerator {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public CourseExcelReportGenerator(List<Course> courseList) {
        workbook = new XSSFWorkbook();
    }

    /**
     * Escribe el encabezado de la tabla para el reporte Excel.
     */
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Cursos");
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "Título", style);
        createCell(row, 2, "Descripción", style);
        createCell(row, 3, "Nivel", style);
        createCell(row, 4, "Publicado", style);
        createCell(row, 5, "Creado", style);
        createCell(row, 6, "Modificado", style);
    }

    /**
     * Crea una celda con un valor y un estilo específicos.
     * @param row La fila donde se creará la celda.
     * @param columnCount El índice de la columna.
     * @param value El valor a establecer en la celda.
     * @param style El estilo de la celda.
     */
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue(String.valueOf(value));
        }
        cell.setCellStyle(style);
    }

    /**
     * Rellena la hoja de Excel con los datos de la lista de cursos.
     * @param courseList La lista de cursos con los datos.
     */
    private void writeDataLines(List<Course> courseList) {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Course course : courseList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, course.getId(), style);
            createCell(row, columnCount++, course.getTitle(), style);
            createCell(row, columnCount++, course.getDescription(), style);
            createCell(row, columnCount++, course.getLevel(), style);
            createCell(row, columnCount++, course.isPublished(), style);

            String createdAt = (course.getCreatedAt() != null) ? course.getCreatedAt().format(formatter) : "N/A";
            createCell(row, columnCount++, createdAt, style);

            String updatedAt = (course.getUpdatedAt() != null) ? course.getUpdatedAt().format(formatter) : "N/A";
            createCell(row, columnCount++, updatedAt, style);

        }
    }

    /**
     * Exporta la lista de cursos a un archivo Excel.
     * @param courseList La lista de cursos a exportar.
     * @param response La respuesta HTTP para enviar el archivo.
     * @throws IOException Si ocurre un error de E/S.
     */
    public void export(List<Course> courseList, HttpServletResponse response) throws IOException {
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=courses_report_" + System.currentTimeMillis() + ".xlsx";
        response.setHeader(headerKey, headerValue);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        writeHeaderLine();
        writeDataLines(courseList);

        try (ServletOutputStream outputStream = response.getOutputStream();
             XSSFWorkbook workbook = this.workbook) {
            workbook.write(outputStream);
        }
    }
}
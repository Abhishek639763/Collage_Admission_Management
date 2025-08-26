package com.college.export;

import com.college.util.DBUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Exporter {

    /**
     * Export joined admission list to CSV
     */
    public static void exportToCSV(String outPath) throws Exception {
        String sql = "SELECT a.id as app_id, s.id as student_id, s.name as student_name, s.email, s.marks, c.code as course_code, c.name as course_name, a.status FROM applications a JOIN students s ON a.student_id=s.id JOIN courses c ON a.course_id=c.id ORDER BY c.id, a.status";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             PrintWriter pw = new PrintWriter(new FileWriter(outPath))) {
            pw.println("application_id,student_id,student_name,email,marks,course_code,course_name,status");
            while (rs.next()) {
                pw.printf("%d,%d,\"%s\",%s,%.2f,%s,\"%s\",%s\n",
                        rs.getInt("app_id"),
                        rs.getInt("student_id"),
                        rs.getString("student_name"),
                        rs.getString("email"),
                        rs.getDouble("marks"),
                        rs.getString("course_code"),
                        rs.getString("course_name"),
                        rs.getString("status"));
            }
        }
    }

    /**
     * Export same data to a simple PDF using PDFBox
     */
    public static void exportToPDF(String outPath) throws Exception {
        String sql = "SELECT a.id as app_id, s.name as student_name, s.email, s.marks, c.code as course_code, c.name as course_name, a.status FROM applications a JOIN students s ON a.student_id=s.id JOIN courses c ON a.course_id=c.id ORDER BY c.id, a.status";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             PDDocument doc = new PDDocument()) {

            PDPage page = new PDPage(PDRectangle.LETTER);
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);
            content.beginText();
            content.setFont(PDType1Font.HELVETICA_BOLD, 14);
            content.setLeading(14.5f);
            content.newLineAtOffset(50, 700);
            content.showText("Admission List");
            content.newLine();
            content.newLine();
            content.setFont(PDType1Font.HELVETICA, 11);

            while (rs.next()) {
                String line = String.format("App:%d | %s | %s | marks:%.2f | %s - %s | %s",
                        rs.getInt("app_id"), rs.getString("student_name"), rs.getString("email"),
                        rs.getDouble("marks"), rs.getString("course_code"), rs.getString("course_name"),
                        rs.getString("status"));
                // break long output into lines if needed
                if (line.length() > 100) {
                    // simple wrap
                    content.showText(line.substring(0, 100));
                    content.newLine();
                    content.showText(line.substring(100));
                } else {
                    content.showText(line);
                }
                content.newLine();
            }

            content.endText();
            content.close();
            doc.save(outPath);
        }
    }
}

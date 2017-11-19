package operations;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class FileOperations {

    public static String OUTPUT_FILENAME = "result.xlsx";

    public static Iterator<Row> open(String filename) {

        Iterator<Row> iterator = null;
        try {
            FileInputStream excelFile = new FileInputStream(new File(filename));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            iterator = datatypeSheet.iterator();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return iterator;
    }

    public static void write(List<String> naglowki, List<List<String>> data) throws IOException { //List<String> naglowki, List<List<String>> data
        List<List<String>> result = new ArrayList<>();
        result.add(naglowki);
        result.addAll(data);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");

        int rowNum = 0;
        System.out.println("Creating excel");

        for (List<String> rowData : result) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (String field : rowData) {
                Cell cell = row.createCell(colNum++);
                    cell.setCellValue(field);
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(OUTPUT_FILENAME);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

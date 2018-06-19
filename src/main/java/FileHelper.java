import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class FileHelper {
    public static final String OUTPUT_FILE_NAME = "Eng%s.xlsx";
    public static final String OUTPUT_FILE_PATH = "/Volume";
    public static int counter = 0;

    public static Map<Integer, Map<String, String>> excelConverterToMap(String filePath) {
        List<String> rawData = new ArrayList<>();
        InputStream reader;
        XSSFWorkbook workbook = null;
        try {
            reader = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(reader);
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (workbook == null) {
            return null;
        }
        Sheet rows = workbook.getSheetAt(0);
        StringBuilder builder = new StringBuilder();
        Iterator<Row> rowIterator = rows.rowIterator();
        Map<Integer, Map<String, String>> dictionary = new HashMap<>();
        Map<String, String> pairs;
        int counter = 0;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.iterator();
            String eng = null, ger = null;
            counter++;
            for (int i = 0; cellIterator.hasNext(); i++) {
                Cell cell = cellIterator.next();
                int cellType = cell.getCellType();
                switch (cellType) {
                    case Cell.CELL_TYPE_STRING:
                        builder
                                .append(String.format("%-200s", cell.getStringCellValue()));
                        if (!cell.getStringCellValue().equals("")) {
                            if (i == 0) {
                                eng = cell.getStringCellValue();
                            } else {
                                ger = cell.getStringCellValue();
                            }
                        } else {
                            eng = "not translated";
                            ger = "not translated";
                        }

                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        builder
                                .append(String.format("%-14.2f    ", cell.getNumericCellValue()));

                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        builder
                                .append(String.format("%-14.2f    ", cell.getNumericCellValue()));

                        break;
                    case Cell.CELL_TYPE_BLANK:
                        break;
                }
            }

            pairs = new HashMap<>();
            pairs.put(eng, ger);
            dictionary.put(counter, pairs);
            if (!builder.toString().isEmpty()) {
                rawData.add(builder.toString().trim());
            }

            builder.setLength(0);
        }

        return dictionary;
    }

    public static Map<String, String> excelConverterToPairMap(String filePath) {
        List<String> rawData = new ArrayList<>();
        InputStream reader;
        XSSFWorkbook workbook = null;
        try {
            reader = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(reader);
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (workbook == null) {
            return null;
        }
        Sheet rows = workbook.getSheetAt(0);
        StringBuilder builder = new StringBuilder();
        Iterator<Row> rowIterator = rows.rowIterator();
        Map<String, String> dictionary = new HashMap<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.iterator();
            String eng = null, ger = null;
            for (int i = 0; cellIterator.hasNext(); i++) {
                Cell cell = cellIterator.next();
                int cellType = cell.getCellType();
                switch (cellType) {
                    case Cell.CELL_TYPE_STRING:
                        builder
                                .append(String.format("%-200s", cell.getStringCellValue()));
                        if (!cell.getStringCellValue().equals("")) {
                            if (i == 0) {
                                eng = cell.getStringCellValue();
                            } else {
                                ger = cell.getStringCellValue();
                            }
                        } else {
                            eng = "not translated";
                            ger = "not translated";
                        }

                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        builder
                                .append(String.format("%-14.2f    ", cell.getNumericCellValue()));

                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        builder
                                .append(String.format("%-14.2f    ", cell.getNumericCellValue()));

                        break;
                    case Cell.CELL_TYPE_BLANK:
                        break;
                }
            }
            dictionary.put(eng, ger);
            if (!builder.toString().isEmpty()) {
                rawData.add(builder.toString().trim());
            }

            builder.setLength(0);
        }

        return dictionary;
    }

    public static List<String> excelConverterToList(String filePath) {
        List<String> rawData = new ArrayList<>();
        //Set<String> raw = new HashSet<>();
        InputStream reader;
        XSSFWorkbook workbook = null;
        try {
            reader = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(reader);
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (workbook == null) {
            return null;
        }
        Sheet rows = workbook.getSheetAt(0);
        StringBuilder builder = new StringBuilder();
        Iterator<Row> rowIterator = rows.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.iterator();
            for (int i = 0; cellIterator.hasNext(); i++) {
                Cell cell = cellIterator.next();
                int cellType = cell.getCellType();
                switch (cellType) {
                    case Cell.CELL_TYPE_STRING:
                        builder
                                .append(String.format("%-30s", cell.getStringCellValue()));

                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        builder
                                .append(String.format("%-14.2f    ", cell.getNumericCellValue()));

                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        builder
                                .append(String.format("%-14.2f    ", cell.getNumericCellValue()));

                        break;
                    case Cell.CELL_TYPE_BLANK:
                        break;
                }
            }
            if (!builder.toString().isEmpty()) {
                rawData.add(builder.toString().trim());
                //raw.add(builder.toString().trim());
            }

            builder.setLength(0);
        }

        return rawData;
        //return raw;
    }

    public static boolean writeToExelFormat(Map<String, String> dictionary, Language language) {
        boolean isOk = false;

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(String.format(OUTPUT_FILE_NAME, language.getLanguage()));
        if (dictionary != null && !dictionary.isEmpty()) {
            String key = null;
            String value = null;
            dictionary.forEach((k, v) -> {
                Row row = sheet.createRow(++counter);
                if (k != null && v != null) {
                    row.createCell(0).setCellValue(k);
                    row.createCell(1).setCellValue(v);
                }
            });
            isOk = true;
            //FileOutputStream writeXlsx = new FileOutputStream(new File())
        }

        return isOk;
    }


}
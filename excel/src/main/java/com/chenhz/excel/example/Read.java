package com.chenhz.excel.example;

import com.chenhz.excel.Entity.Node;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author CHZ
 * @create 2018/10/29
 */
public class Read {

    public static final String SAMPLE_XLS_FILE_PATH = "file/java课程总体设计.xls";

    public static void main(String[] args) throws IOException, InvalidFormatException {
        File file = new ClassPathResource(SAMPLE_XLS_FILE_PATH).getFile();
//        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLS_FILE_PATH));
        Workbook workbook = WorkbookFactory.create(file);
        System.out.println("Workbook hsa "+workbook.getNumberOfSheets()+" Sheets");

        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        System.out.println("Retrieving Sheets using Iterator");

        // 1. You can obtain a sheetIterator and iterate over it
        while (sheetIterator.hasNext()){
            Sheet sheet = sheetIterator.next();
            System.out.println("==>" +sheet.getSheetName());
        }

        // Getting the Sheet at index zero
        Sheet sheet = workbook.getSheetAt(0);

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
        Iterator<Row> rowIterator = sheet.rowIterator();
        Node<String> topNode  = new Node<>("Top");
        Node<String> finalNode = topNode;
        while (rowIterator.hasNext()){
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator= row.cellIterator();
            while (cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                if (!StringUtils.isBlank(cellValue)){
                    Node<String> n = new Node<>(cellValue);
                    n.setPrev(finalNode);
                    finalNode.addNext(n);
                    finalNode = n;
                }else{
                    finalNode = finalNode.getNextLast();
                }
            }

            finalNode = topNode;

//            Row row = rowIterator.next();
//
//            Iterator<Cell> cellIterator = row.cellIterator();
//
//            while (cellIterator.hasNext()){
//                Cell cell = cellIterator.next();
//                String cellValue = dataFormatter.formatCellValue(cell);
//                if (StringUtils.isBlank(cellValue)){
//                    cellValue = "---------";
//                }
//                System.out.print(cellValue + "\t");
//            }
//            System.out.println();
        }
        // Closing the workbook
        workbook.close();
    }
}

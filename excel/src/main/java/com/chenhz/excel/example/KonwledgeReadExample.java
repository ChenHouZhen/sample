package com.chenhz.excel.example;

import com.chenhz.common.entity.ZNode;
import com.chenhz.excel.entity.Node;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author CHZ
 * @create 2018/10/29
 */

@RestController
public class KonwledgeReadExample {


    public static final String SAMPLE_XLS_FILE_PATH = "file/java课程总体设计.xls";

    public static <E> Node<E> get() throws IOException, InvalidFormatException {
        long before = System.currentTimeMillis();
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
        Node<E> topNode  = new Node<E>((E) "Top");
        Node<E> finalNode = topNode;
        while (rowIterator.hasNext()){
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator= row.cellIterator();
            while (cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                if (!StringUtils.isBlank(cellValue)){
                    Node<E> n = new Node<E>((E) cellValue);
                    n.setPrev(finalNode);
                    finalNode.addNext(n);
                    finalNode = n;
                }else{
                    finalNode = finalNode.getNextLast();
                }
            }
            finalNode = topNode;
        }
        // Closing the workbook
        workbook.close();
        long after = System.currentTimeMillis();
        System.out.println("Time use is :" +(after-before) +".ms");
        return topNode;
    }

    public static Map<String,Object> tree() throws IOException, InvalidFormatException {
        return get().iteratopMap(get());
    }

    public static List<ZNode> zNode() throws IOException, InvalidFormatException {
        return get().getBackList();
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        System.out.println(tree());
    }
}

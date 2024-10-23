package org.example;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.util.Iterator;

public class Project5 {
    public static void main(String[] args) {
        String fileName = "bookList.xls"; //프로젝트 폴더의 엑셀파일
        /* try-resources-catch 문은 try () 에 객체들을 넣음 */
        try (FileInputStream fis = new FileInputStream(fileName);
             HSSFWorkbook workbook = new HSSFWorkbook(fis);) {
            HSSFSheet sheet = workbook.getSheetAt(0); //첫번째 엑셀 시트
            Iterator<Row> rows = sheet.iterator();          //줄 이터레이터
            rows.next();                        //첫줄은 제목이므로 넘기기
            String[] temp = new String[5];      //엑셀 제목 수만큼
            while (rows.hasNext()) {
                HSSFRow row = (HSSFRow) rows.next();
                Iterator<Cell> cells = row.cellIterator(); //한줄안에 여러개 셀즈
                int i = 0;
                while (cells.hasNext()) {

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package org.example;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.example.model.ExelVO;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Project5 {
    public static void main(String[] args) {
        String fileName = "bookList.xls"; //프로젝트 폴더의 엑셀파일
        List<ExelVO> data = new ArrayList<ExelVO>();
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
                    HSSFCell cell = (HSSFCell) cells.next();
                    temp[i] = cell.toString();
                    i++;
                }
                //한줄의 데이터를 자바 객체에 넣어서 만듬
                ExelVO vo = new ExelVO(temp[0], temp[1], temp[2], temp[3], temp[4]);
                data.add(vo); //리스트에 객체 추가
            }
            showExelData(data); //엑셀파일의 모든 데이터를 한줄씩 출력하자.
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void showExelData(List<ExelVO> data) {
        for (ExelVO vo : data) {
            System.out.println(vo);
        }
    }
}

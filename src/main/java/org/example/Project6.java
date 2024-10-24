package org.example;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.example.model.ExelVO;

import java.io.FileInputStream;
import java.util.*;

public class Project6 {
    public static void main(String[] args) {
        String fileName = "zusic.xls"; //프로젝트 폴더의 엑셀파일
        //주식이름, 코드번호
        Map<String, String> list = new HashMap<String, String>();
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
                    if(i == 4) break;
                }
                //한줄의 데이터를 자바 객체에 넣어서 만듬
                list.put(temp[3], temp[1]);
            }
            //showExelData(list); //엑셀파일의 모든 데이터를 한줄씩 출력하자.
            showZusicCode(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 주식명을 입력하면 종목코드를 출력해줌
     */
    private static void showZusicCode(Map<String, String> list) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("주식명을 입력: ");
            String name = scanner.nextLine();
            System.out.println(list.get(name));
        }
    }

    private static void showExelData(Map<String, String> list) {
        for (Map.Entry<String, String> entry : list.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }
}

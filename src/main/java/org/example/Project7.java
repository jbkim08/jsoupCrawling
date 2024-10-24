package org.example;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.example.model.ExelVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Project7 {

    static final Scanner scanner = new Scanner(System.in);
    static List<ExelVO> bookList = new ArrayList<>();
    static HSSFWorkbook workBook = new HSSFWorkbook();

    public static void main(String[] args) {
        /* 계속 실행되게! */
        boolean running = true;
        do {
            menu();
            int key = Integer.parseInt(scanner.nextLine());
            switch (key) {
                case 1:
                    searchBook();
                    break;
                case 2:
                    listCheck();
                    break;
                case 3:
                    exelInput();
                    break;
                case 4:
                    running = false; //사용자가 4를 선택시 프로그램 종료!
                    break;
                default:
                    break;
            }
        } while (running);

    }

    private static void menu() {
        System.out.println("=========== 메 뉴 =============");
        System.out.println("1. 책 검색");
        System.out.println("2. 책 리스트 확인");
        System.out.println("3. 책 리스트 엑셀 저장");
        System.out.println("4. 종료");
        System.out.print("번호 선택 : ");
    }

    private static void searchBook() {
        System.out.print("책 검색 : ");
        String query = scanner.nextLine().trim();
        ExelVO vo = getNaverAPI(query);
        if(vo == null) return; // 메서드 종료

        System.out.print("검색된 책을 저장? (y/n) 선택 : ");
        if(scanner.nextLine().toLowerCase().charAt(0) == 'y') {
            bookList.add(vo);
            System.out.println("책이 저장됩니다.");
        } else {
            System.out.println("책이 저장되지 않습니다.");
        }
    }

    /**
     * 검색어 입력을 받아 네이버 API 로 책을 검색한 결과 리턴
     * @param query 검색어
     * @return 책 객체
     */
    private static ExelVO getNaverAPI(String query) {

        try {
            String openApi = "https://openapi.naver.com/v1/search/book.json?query="
                    + URLEncoder.encode(query, "UTF-8");
            URL url = new URL(openApi);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Naver-Client-Id", "Oex1SY46jLWbaoNsZ7RJ");
            conn.setRequestProperty("X-Naver-Client-Secret", "vcQMXqDaLi");

            //System.out.println("응답 코드 : " + conn.getResponseCode());

            BufferedReader br;
            if(conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            //System.out.println(sb.toString());
            br.close();
            conn.disconnect();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(sb.toString());
            JSONArray arr = (JSONArray)jsonObject.get("items");

            if(arr.isEmpty()) {
                System.out.println("검색된 책이 없습니다.");
                return null;
            }

            JSONObject book = (JSONObject)arr.get(0); //제이슨배열에서 첫번째 책을 가져옴
            ExelVO vo = new ExelVO();
            System.out.print(book.get("title") + "\t");
            System.out.print(book.get("author") + "\t");
            System.out.print(book.get("publisher") + "\t");
            System.out.print(book.get("isbn") + "\t");
            System.out.print(book.get("pubdate") + "\t");
            System.out.println();
            vo.setTitle(book.get("title").toString());
            vo.setAuthor(book.get("author").toString());
            vo.setCompany(book.get("publisher").toString());
            vo.setIsbn(book.get("isbn").toString());
            vo.setImgurl(book.get("image").toString());

            return vo;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void listCheck() {
       if(bookList.isEmpty()){
           System.out.println("리스트에 저장된 책이 없습니다.");
       } else {
           for (ExelVO vo : bookList) {
               System.out.println(vo);
           }
       }
    }

    // 엑셀에 북리스트에 담긴 데이터 저장
    private static void exelInput() {
        try {
            //가상의 엑셀 시트를 생성
            HSSFSheet sheet = null;
            if(workBook.getSheet("Book SHEET") != null ) {
                sheet = workBook.getSheet("Book SHEET");
            } else {
                sheet = workBook.createSheet("BOOK SHEET");
            }
            //엑셀 시트에 첫줄 만들기 (제목들 입력 열이 5개)
            HSSFRow rowA = sheet.createRow(0);
            HSSFCell cellA = rowA.createCell(0);
            cellA.setCellValue(new HSSFRichTextString("책제목"));
            HSSFCell cellB = rowA.createCell(1);
            cellB.setCellValue(new HSSFRichTextString("저자"));
            HSSFCell cellC = rowA.createCell(2);
            cellC.setCellValue(new HSSFRichTextString("출판사"));
            HSSFCell cellD = rowA.createCell(3);
            cellD.setCellValue(new HSSFRichTextString("isbn"));
            HSSFCell cellE = rowA.createCell(4);
            cellE.setCellValue(new HSSFRichTextString("이미지주소"));

            //북리스트의 데이터를 가상의 엑셀시트에 넣기
            int i=1; //첫줄은 스킵 1부터
            for(ExelVO book : bookList) {
                HSSFRow row = sheet.createRow(i++); //반복문이 진행될때마다 다음줄 만듬
                row.createCell(0).setCellType(CellType.STRING);
                row.createCell(0).setCellValue(book.getTitle());
                row.createCell(1).setCellType(CellType.STRING);
                row.createCell(1).setCellValue(book.getAuthor());
                row.createCell(2).setCellType(CellType.STRING);
                row.createCell(2).setCellValue(book.getCompany());
                row.createCell(3).setCellType(CellType.STRING);
                row.createCell(3).setCellValue(book.getIsbn());
                row.createCell(4).setCellType(CellType.STRING);
                row.createCell(4).setCellValue(book.getImgurl());
            }
            //프로젝트 폴더에 엑셀파일을 생성
            FileOutputStream fos = new FileOutputStream("BookList.xls");
            workBook.write(fos); //가상의 엑셀시트 내용을 파일에 담기
            fos.close();
            System.out.println("엑셀로 저장 성공!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

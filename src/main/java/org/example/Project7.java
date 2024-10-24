package org.example;

import org.example.model.ExelVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Project7 {

    static final Scanner scanner = new Scanner(System.in);

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
        System.out.println("책 리스트를 확인합니다.");
    }

    private static void exelInput() {
        System.out.println("엑셀에 책을 입력합니다.");
    }

}

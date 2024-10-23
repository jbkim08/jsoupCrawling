package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

public class Project3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("종목코드 입력 : ");
        String code = scanner.nextLine();
        /* 네이버 증권 */
        String url = "https://finance.naver.com/item/main.naver?code="+code;
        Document doc = null;

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //종목정보
        Elements todaylist = doc.select(".new_totalinfo dl>dd");

        String time = todaylist.get(0).text();
        String name = todaylist.get(1).text().split(" ")[1]; //종목명 빼고 이름만 나오게
        String juga = todaylist.get(3).text().split(" ")[1]; //현재 주가
        String dungRakrate = todaylist.get(3).text().split(" ")[6]; //퍼센트 등락률
        String siga = todaylist.get(5).text().split(" ")[1]; //시가
        String goga = todaylist.get(6).text().split(" ")[1]; //고가
        String zeoga = todaylist.get(8).text().split(" ")[1]; //저가
        String georaeryang = todaylist.get(10).text().split(" ")[1]; //거래량
        String stype = todaylist.get(3).text().split(" ")[3]; // 상한가,상승,보합,하한가,하락 구분

        String vsYesterday = todaylist.get(3).text().split(" ")[4]; //어제가격비교 차이

        System.out.printf("============== %s ================\n", name); // 종목명
        System.out.println("주가:" + juga);
        System.out.println("등락률:" + dungRakrate + "%");
        System.out.println("시가:" + siga);
        System.out.println("고가:" + goga);
        System.out.println("저가:" + zeoga);
        System.out.println("거래량:" + georaeryang);
        System.out.println("타입:" + stype);
        System.out.println("전일대비:" + vsYesterday);
        System.out.println("가져오는 시간:" + time);






    }
}

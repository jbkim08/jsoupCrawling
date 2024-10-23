package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Project1 {
    public static void main(String[] args) {
        /* 네이버 스포츠 해외축구 크롤링 */
        String url = "https://sports.news.naver.com/wfootball/index";
        Document doc = null; //html 문서

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(doc);
        Elements elements = doc.select("div.home_news");
        //제목글자만 가져오기
        String title = elements.select("h2").text();
        System.out.println("============================================");
        System.out.println(title);
        System.out.println("============================================");
        //모든 리스트의 li 내용을 출력
        for (Element el : elements.select("li")) {
            if(el.text().length() >= 30){
                System.out.println(el.text().substring(0, 30) + "...");
            } else {
                System.out.println(el.text());
            }
        }
        System.out.println("============================================");
    }
}

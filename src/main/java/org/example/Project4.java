package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

public class Project4 {
    public static void main(String[] args) {
        /* 인프런 강의 */
        String url = "https://www.inflearn.com/courses/it-programming";
        Document doc = null;

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 강의 컨텐츠 (제목,가격,평점등)
        Elements CardContents = doc.select("ul.mantine-1avyp1d");
        System.out.println(CardContents);
        // Iterator을 사용하여 하나씩 값 가져오기
        Iterator<Element> title = CardContents.select("p.css-10bh5qj").iterator();

        while (title.hasNext()) {
            System.out.println("===============================================================");
            System.out.println("강의 제목 : " + title.next().text());
        }


    }
}

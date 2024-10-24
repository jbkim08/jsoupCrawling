package org.example;

import java.util.Scanner;

public class Project7 {
    public static void main(String[] args) {
        /* 계속 실행되게! */
        boolean running = true;
        do {
            menu();
            Scanner scanner = new Scanner(System.in);
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
        System.out.println("책을 검색합니다.");
    }

    private static void listCheck() {
        System.out.println("책 리스트를 확인합니다.");
    }

    private static void exelInput() {
        System.out.println("엑셀에 책을 입력합니다.");
    }

}

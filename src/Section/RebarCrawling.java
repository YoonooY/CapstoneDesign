package Section;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class RebarCrawling {

    static double []rebarPrice;
    static double []UHSSRebarPrice;
    static double avrPrice;
    static double avrPrice2;
    static double price;
    static double UHSSPrice; //초고장력 철근
    static String[] newStr;
    static String[] newStr2;

    public static void crawling() {
        String URL = "https://www.koris.or.kr/library/library_sub03_01_01.php"; //한국응용통계연구원 주요자재 가격
        Document doc = null;
        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Elements elem = doc.select("td[class=\"a_r\"]");

        String[] str = elem.text().split(" ");
        newStr = new String[8];
        newStr2 = new String[8];
        rebarPrice = new double[8];
        UHSSRebarPrice = new double[8];

        for (int i = 0; i<8; i++) { //철근의 당월 가격에 대해서만 newStr에 저장
            newStr[i] = str[i*3].replaceAll(",","");
            newStr2[i] = str[24+i*3].replaceAll(",","");
        }
        for (int i = 0; i<8; i++) {
            rebarPrice[i] = Double.parseDouble(newStr[i]);
            UHSSRebarPrice[i] = Double.parseDouble(newStr2[i]);
        }

        double tot=0;
        double tot2=0;
        for (int i = 0; i<8; i++) {
            tot += rebarPrice[i];
            tot2 += UHSSRebarPrice[i];
        }

        avrPrice = tot/8;
        price = avrPrice / 1000 * 7850 / 1000000; //고장력철근 단위길이당 가격

        avrPrice2 = tot2/8;
        UHSSPrice = avrPrice2 / 1000 * 7850 / 1000000; //초고장력철근 단위길이당 가격

    }
    public static double getPrice() {
        return price;
    }
    public static double getUHSSPrice() {
        return UHSSPrice;
    }
}

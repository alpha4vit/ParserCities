package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static java.lang.Math.pow;

public class parserAvito {

    private static Document getPage(String url) throws IOException {
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }
    public static int getPrice(String str){
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i =0; i < str.length(); ++i){
            if (Character.isDigit(str.charAt(i))){
                nums.add((int) str.charAt(i));

            };
        }

        int num = 0;
        for (int i =0; i < nums.size(); ++i){
            num+=nums.get(i)*pow(10, nums.size()-i-1);
        }

        return num;
    }
    public static void parseAvito() throws IOException {
        Document page = getPage("https://www.avito.ru/all?q=macbook");
        Elements items = page.select("div[data-marker=item]");
        Collections.sort(items, new Comparator<Element>() {
            @Override
            public int compare(Element o1, Element o2) {
                Integer price1 = getPrice(o1.select("div[class=iva-item-priceStep-uq2CQ]").text());
                Integer price2 = getPrice(o2.select("div[class=iva-item-priceStep-uq2CQ]").text());
                return price1.compareTo(price2);

            }
        });
        for (Element el : items){
            System.out.print(el.select("h3[itemprop=name]").text()+ "\t\t\t\t");
            System.out.print(el.select("div[class=iva-item-priceStep-uq2CQ]").text()+"\t\t\t\t");
            System.out.println(el.select("div[class=iva-item-descriptionStep-C0ty1]").text());
        }

    }
}

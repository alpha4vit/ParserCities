package org.example;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import javax.print.Doc;
import java.io.IOException;

import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import static java.lang.Math.pow;

public class Parser {
    private static Document getPage(String url) throws IOException {
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }


    public static void parseCity() throws IOException{
        Document page = getPage( "https://www.krylatskoye.ru/content/ratings/2021/09/0928.html");
        Element table = page.selectFirst("table[class=table_free]");
        Element name = table.selectFirst("th[class=name]");
        System.out.println("\t\t\t\t\t\t\t\t"+name.text());
        Elements cities = table.select("tr");
        Collections.sort(cities, new Comparator<Element>() {
            @Override
            public int compare(Element o1, Element o2) {
                String fstr = o1.text();
                String sstr = o2.text();
                int fnum = 0;
                int snum = 0;
                while (Character.isDigit(fstr.charAt(fnum)) || Character.isDigit(sstr.charAt(snum))){
                    if (Character.isDigit(fstr.charAt(fnum))){
                        ++fnum;
                    }
                    if (Character.isDigit(sstr.charAt(snum))){
                        ++snum;
                    }
                }
                fstr = fstr.substring(fnum);
                sstr = sstr.substring(snum);
                return fstr.compareTo(sstr);
        }});
        for (Element th : cities.get(1).select("th")){
            System.out.print(th.text()+"\t\t\t");
        }
        for (int i = 1; i < cities.size();++i){
            Elements col = cities.get(i).select("td");
            for (Element el : col){
                System.out.print(el.text()+"\t\t\t");
            }
            System.out.println();
        }

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
    public static void parse() throws IOException{
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

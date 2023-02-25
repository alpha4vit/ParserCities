package org.example;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Parser {
    private static Document getPage() throws IOException {
        String url = "https://www.krylatskoye.ru/content/ratings/2021/09/0928.html";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }


    public static void parse() throws IOException{
        Document page = getPage();
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
        //System.out.println(table);
    }
}

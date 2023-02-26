package org.example;

import org.checkerframework.checker.units.qual.C;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class parserDomino {
    private static Document getPage(String url) throws IOException {
        return Jsoup.parse(new URL(url), 3000);
    }

    private static class Category{

        static String abslink = "https://dominodomoy.ru";
        ArrayList<Category.Item> items;
        String name;
        String link;

        Category (){
            this.name = "";
            this.link = "";
            this.items = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        @Override
        public String toString() {
           return "Категория: "+this.name + "\n"+this.items+"\n\n";
        }
        class Item {


            String itemName;
            String price;
            String imgLink;

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getImgLink() {
                return imgLink;
            }

            public void setImgLink(String imgLink) {
                this.imgLink = imgLink;
            }

            @Override
            public String toString() {
                return "\tНазвание товара:\t --- \t" + this.itemName + "\tСтоимость товара:\t --- \t" + this.price + "\tСсылка на изображение:\t --- \t" + abslink + this.imgLink + "\n";
            }
        }
    }

    public static void parseDomino() throws IOException{
        String link = "https://dominodomoy.ru";
        Document page = getPage("https://dominodomoy.ru/catalog/");
        Elements cats = page.getElementsByClass("catalog-section-list-item-information intec-grid-item intec-grid-item-450-1 intec-grid-item-shrink-1");
        ArrayList<Category> categories = new ArrayList<>();
        for (Element el: cats){
            Category cat = new Category();
            cat.setName(el.text());
            cat.setLink(el.select("a").attr("href"));
            Document tempPage = Jsoup.parse(new URL(link+cat.getLink()), 3000);
            Elements items = tempPage.select("div[class=catalog-section-item-background intec-ui-clearfix]");
            for (int i = 0; i < items.size(); ++i) {
                String price = items.get(i).getElementsByClass("catalog-section-item-price-base").text();
                String name = items.get(i).getElementsByClass("catalog-section-item-name").text();
                String imgUrl = items.get(i).select("img").attr("data-original");
                Category.Item item = cat.new Item();
                item.setItemName(name);
                item.setPrice(price);
                item.setImgLink(imgUrl);
                cat.items.add(item);
            }
            categories.add(cat);
        }
        System.out.println(categories.toString());




    }
}

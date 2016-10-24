package cz.lorenc;

import com.jaunt.*;
import com.jaunt.util.CacheException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static final String END_URL = " ";
    public static final String BEGIN_URL = "http://mobilni-telefony.heureka.cz/";
    public static final String URL = "http://mobilni-telefony.heureka.cz/";



    public static void getProducts(String baseURL) {
        // write your code here
        UserAgent userAgent = new UserAgent();         //create new userAgent (headless browser)
        try {
            userAgent.visit(URL);          //visit google
            //userAgent.doc.apply("butterflies");            //apply form input (starting at first editable field)
            //userAgent.doc.submit("Hledat Googlem");         //click submit button labelled "Google Search"
        } catch (ResponseException e) {
            e.printStackTrace();
        }

        Elements links = userAgent.doc.findEvery("<div class=product-container>").findEvery("<a href>");  //find search result links

//        for (int i = 0; i < links.size(); i++) {
//            try {
//                System.out.println(links.getElement(i).findEvery("<a href>"));           //print results
//            } catch (NotFound notFound) {
//                notFound.printStackTrace();
//            }
//        }

        for (Element link : links)
            try {
                System.out.println(link.getAt("href"));           //print results
            } catch (NotFound notFound) {
                notFound.printStackTrace();
            }
    }


    public static void main(String[] args) {
        Crawler crawler = new Crawler("http://heureka.cz/", ".heureka.cz");
        crawler.doCrawling().forEach(System.out::println);
    }
}

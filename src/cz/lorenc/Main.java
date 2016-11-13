package cz.lorenc;

import com.jaunt.*;
import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.util.CacheException;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.io.IOException;
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

        //System.out.println(crawler.getReviewsForModel("http://kontaktni-cocky.heureka.cz/johnson-johnson-acuvue-oasys-with-hydraclear-plus-6-cocek/recenze/"));
        JSONHelper jsonHelper = new JSONHelper();
        List<Review> list = crawler.doCrawling();
        jsonHelper.addReview(list);
//
//        jsonHelper.getReviews().forEach(System.out::println);

//        org.jsoup.nodes.Document doc = null;
//        try {
//            doc = Jsoup.connect("http://kontaktni-cocky.heureka.cz/johnson-johnson-acuvue-oasys-with-hydraclear-plus-6-cocek/recenze/").get();
//            org.jsoup.select.Elements newsHeadlines = doc.select(".revtext");
//            for(org.jsoup.nodes.Element element : newsHeadlines){
//                System.out.println(element.select("big").text());
//                System.out.println(element.select("p").text());
//                System.out.println("PLUS");
//                for (org.jsoup.nodes.Element e : element.select(".plus > ul >li")){
//                    System.out.println(e.text());
//                }
//                System.out.println("MINUS");
//                for (org.jsoup.nodes.Element e : element.select(".minus > ul > li")){
//                    System.out.println(e.text());
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}

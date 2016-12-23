package cz.lorenc;

import com.jaunt.UserAgent;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by United121 on 10/22/2016.
 */
public class Crawler {

    private int limitOfAddress = 500000;


    public Set<String> getUrlsReview() {
        return urlsReview;
    }

    public void setUrlsReview(Set<String> urlsReview) {
        this.urlsReview = urlsReview;
    }

    public List<Review> getReviewsForModel(String url) {
        List<Review> reviews = new ArrayList<Review>();
        org.jsoup.nodes.Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            org.jsoup.select.Elements newsHeadlines = doc.select(".review");
            for (org.jsoup.nodes.Element element : newsHeadlines) {
                String ratingText = element.select("big").text();
                String reviewText = element.select(".revtext").select("p").text();

                List<String> plus = new ArrayList<>();
                List<String> minus = new ArrayList<>();

                int usefulReview = 0;
                int uselessReview = 0;

                String date;
                String shop;

//                    System.out.println("PLUS");
                for (org.jsoup.nodes.Element e : element.select(".plus > ul >li")) {
                    plus.add(e.text());
                }
//                    System.out.println("MINUS");
                for (org.jsoup.nodes.Element e : element.select(".minus > ul > li")) {
                    minus.add(e.text());
                }

                Pattern p = Pattern.compile("[Ano|Ne].+(\\d).*");

                org.jsoup.select.Elements e = element.select(".evalreview > li:nth-child(2)");
                Matcher m = p.matcher(e.text());

                if (m.find()) {
                    usefulReview = Integer.parseInt(m.group(1));
                }

                org.jsoup.select.Elements e2 = element.select(".evalreview > li:nth-child(3)");
                m = p.matcher(e2.text());

                if (m.find()) {
                    uselessReview = Integer.parseInt(m.group(1));
                }

                org.jsoup.select.Elements e3 = element.select(".date");

                if (!e3.text().contains("2016")) {
                    Calendar cal = Calendar.getInstance();
                    DateFormat formatData = new SimpleDateFormat("d. MMMM yyyy");
                    date = formatData.format(cal.getTime());
                } else {
                    date = e3.text().substring(9);
                }

                org.jsoup.select.Elements e4 = element.select(".purchased > a");
                shop = e4.text();

                reviews.add(new Review(url, reviewText, ratingText, plus, minus, usefulReview, uselessReview, date, shop));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return reviews;
    }

//    private static final Pattern FilterAdd = Pattern.compile("^http://(.*mobilni.*|.*notebook.*|.*monitor.*|klavesnice.*|tiskarny.*" +
//            "|.*tablet.*|.*gps.*|.*tele.*|.*foto.*|.*digi.*|.*brasny.*|.*tv.*|.*tele.*|.*3D.*|proj.*|drony.*|.*pocitac.*" +
//            "|graficke.*|.*mys.*|.*komponent.*|.*pamet.*|.*procesor.*|.*zdroj.*|.*disk.*|.*karty.*|.*software.*|.*aplikace.*" +
//            "|.*system.*|.*vide.*)\\.heureka\\.cz/.*/recenze/$");
//    private static final Pattern FilterCrawl = Pattern.compile("^http://(.*mobilni.*|.*notebook.*|.*monitor.*|klavesnice.*|tiskarny.*" +
//            "|.*tablet.*|.*gps.*|.*tele.*|.*foto.*|.*digi.*|.*brasny.*|.*tv.*|.*tele.*|.*3D.*|proj.*|drony.*|.*pocitac.*" +
//            "|graficke.*|.*mys.*|.*komponent.*|.*pamet.*|.*procesor.*|.*zdroj.*|.*disk.*|.*karty.*|.*software.*|.*aplikace.*" +
//            "|.*system.*|.*vide.*)\\.heureka\\.cz/($|[^f]{1,}).*");

    private static final Pattern FilterAdd = Pattern.compile("^http://.*heureka.cz/.*/recenze/$");
    private static final Pattern FilterCrawl = Pattern.compile("^http://.*heureka.cz/.*");

    private Set<String> urlsReview = new HashSet<>();
    private Set<String> urls = new HashSet<>();


    public void crawl(String url, int level){
        urls.add(url);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("ERROR: This url " + url + " causes:");
            System.out.println(e);
            return;
        }
        Elements links = doc.select("a");

        for (Element link : links) {
            String foundUrl = link.attr("abs:href").toLowerCase();

            if(urlsReview.size() > limitOfAddress || level > 25){
                return;
            }

            if (FilterAdd.matcher(foundUrl).matches() && urlsReview.add(foundUrl)) {
                System.out.println("Adding: " + foundUrl);
            } else if (FilterCrawl.matcher(foundUrl).matches() && !urls.contains(foundUrl)) {
                if(foundUrl.contains("obchody") || foundUrl.contains("f:")){
                    return;
                }
                System.out.println("Crawling: " + foundUrl);
                crawl(foundUrl, ++level);
            } else {
                System.out.println("NOT ADDING " + foundUrl);
            }
        }
    }


//    public List<Review> doCrawling() {
//        List<Review> reviews = new ArrayList<>();
//        Set<String> newUrls = processLeftMenu();
//        Set<String> newUrls2 = new HashSet<>();
//        boolean evenRun = true;
//        do {
//            if (evenRun) {
//                for (String possibleWay : newUrls) {
//                    newUrls2.addAll(getNewUrls(possibleWay));
//                }
//            } else {
//                for (String possibleWay : newUrls2) {
//                    newUrls.addAll(getNewUrls(possibleWay));
//                }
//            }
//            evenRun = !evenRun;
//        } while (reviewUrls.size() <= limitOfAddress);
//
//        for (String url : reviewUrls) {
//            reviews.addAll(getReviewsForModel(url));
//        }
//        return reviews;
//        //return reviewUrls.stream().map(this::getReviewForModel).collect(Collectors.toList());
//    }
//
//    //--------------------PRIVATE-----------------------
//
//    private boolean addUrl(String url) {
//        if (url.contains(optimizingUrl) && !url.contains(".pdf") && !url.contains("https") && !url.contains("blog")) {
//            if (url.endsWith("recenze/")) {
//                reviewUrls.add(url);
//            }
//            return urls.add(url);
//        }
//        return false;
//    }

//    private Set<String> processLeftMenu() {
//        Set<String> newUrls = new HashSet<>();
//        try {
//            userAgent.visit(this.baseUrl);
//            Elements links = userAgent.doc.findEvery("<div class=left>").findEvery("<a href>");
//            for (Element elem : links) {
//                String link = elem.getAt("href");
//                if (addUrl(link)) {
//                    newUrls.add(link);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return newUrls;
//    }
//
//    private Set<String> getNewUrls(String urlToExplore) {
//        System.out.println("Exploring " + urlToExplore);
//        Set<String> newUrls = new HashSet<>();
//        try {
//            userAgent.visit(urlToExplore);
//            Elements links = userAgent.doc.findEvery("<span class=review-count>").findEvery("<a href>");
//            for (Element elem : links) {
//                String link = elem.getAt("href");
//                if (addUrl(link)) {
//                    newUrls.add(link);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return newUrls;
//    }
}

package cz.lorenc;

import com.jaunt.*;
import com.jaunt.Elements;
import org.jsoup.Jsoup;
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
    UserAgent userAgent;

    private Set<String> urls;
    private Set<String> reviewUrls;
    private String baseUrl;
    private String optimizingUrl;
    private int limitOfAddress = 50;

    public Crawler(String baseUrl, String optimizingUrl) {
        userAgent = new UserAgent();
        userAgent.settings.charset = "utf-8";
        this.baseUrl = baseUrl;
        this.optimizingUrl = optimizingUrl;
        this.urls = new HashSet<>();
        this.reviewUrls = new HashSet<>();
        this.urls.add(baseUrl);
    }

    public List<Review> getReviewsForModel(String url) {
        Review review = null;
        List<Review> reviews = new ArrayList<Review>();
        org.jsoup.nodes.Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            org.jsoup.select.Elements newsHeadlines = doc.select(".review");
            for (org.jsoup.nodes.Element element : newsHeadlines) {
                String ratingText = element.select("big").text();
                String reviewText = element.select("p").text();

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

                if(m.find()){
                    usefulReview = Integer.parseInt(m.group(1));
                }

                org.jsoup.select.Elements e2 = element.select(".evalreview > li:nth-child(3)");
                m = p.matcher(e2.text());

                if(m.find()){
                    uselessReview = Integer.parseInt(m.group(1));
                }

                org.jsoup.select.Elements e3 = element.select(".date");

                if(!e3.text().contains("2016")){
                    Calendar cal=Calendar.getInstance();
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

    public List<Review> doCrawling() {
        List<Review> reviews = new ArrayList<>();
        Set<String> newUrls = processLeftMenu();
        Set<String> newUrls2 = new HashSet<>();
        boolean evenRun = true;
        do {
            if (evenRun) {
                for (String possibleWay : newUrls) {
                    newUrls2.addAll(getNewUrls(possibleWay));
                }
            } else {
                for (String possibleWay : newUrls2) {
                    newUrls.addAll(getNewUrls(possibleWay));
                }
            }
            evenRun = !evenRun;
        } while (reviewUrls.size() <= limitOfAddress);

        for (String url : reviewUrls) {
            reviews.addAll(getReviewsForModel(url));
        }
        return reviews;
        //return reviewUrls.stream().map(this::getReviewForModel).collect(Collectors.toList());
    }

    //--------------------PRIVATE-----------------------

    private boolean addUrl(String url) {
        if (url.contains(optimizingUrl) && !url.contains(".pdf") && !url.contains("https") && !url.contains("blog")) {
            if (url.endsWith("recenze/")) {
                reviewUrls.add(url);
            }
            return urls.add(url);
        }
        return false;
    }

    private Set<String> processLeftMenu() {
        Set<String> newUrls = new HashSet<>();
        try {
            userAgent.visit(this.baseUrl);
            Elements links = userAgent.doc.findEvery("<div class=left>").findEvery("<a href>");
            for (Element elem : links) {
                String link = elem.getAt("href");
                if (addUrl(link)) {
                    newUrls.add(link);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newUrls;
    }

    private Set<String> getNewUrls(String urlToExplore) {
        System.out.println("Exploring " + urlToExplore);
        Set<String> newUrls = new HashSet<>();
        try {
            userAgent.visit(urlToExplore);
            Elements links = userAgent.doc.findEvery("<span class=review-count>").findEvery("<a href>");
            for (Element elem : links) {
                String link = elem.getAt("href");
                if (addUrl(link)) {
                    newUrls.add(link);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newUrls;
    }
}

package cz.lorenc;

import com.jaunt.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private int limitOfAddress = 10;

    public Crawler(String baseUrl, String optimizingUrl) {
        userAgent = new UserAgent();
        this.baseUrl = baseUrl;
        this.optimizingUrl = optimizingUrl;
        this.urls = new HashSet<>();
        this.reviewUrls = new HashSet<>();
        this.urls.add(baseUrl);
    }

    private Review getReviewForModel(String url) {
        Review review = null;
        UserAgent userAgent = new UserAgent();
        try {
            userAgent.visit(url);

            Elements oneReview = userAgent.doc.findEvery("<div class=review>").findEvery("<div class=revtext>");
            for(Element elem : oneReview) {
                Element textInReview = elem.findFirst("<p>");
                Element ratings = elem.findFirst("<big>");
                Elements plusInReview = elem.findEvery("<div class=plus>").findEvery("<li>");
                Elements minusInReview = elem.findEvery("<div class=minus>").findEvery("<li>");

                String reviewText = textInReview.getText();
                String ratingText = ratings.getText();

                List<String> plus = Stream.of(plusInReview).map(Element::getText).collect(Collectors.toList());
                List<String> minus = Stream.of(minusInReview).map(Element::getText).collect(Collectors.toList());

                review = new Review(reviewText, ratingText, plus, minus);
            }
        } catch (ResponseException | NotFound e) {
            e.printStackTrace();
        }
        return review;
    }

    public List<Review> doCrawling() {
        Set<String> newUrls = processLeftMenu();
        Set<String> newUrls2 = new HashSet<>();
        boolean evenRun = true;
        do{
            if(evenRun) {
                for (String possibleWay : newUrls) {
                    newUrls2.addAll(getNewUrls(possibleWay));
                }
            }else {
                for (String possibleWay : newUrls2) {
                    newUrls.addAll(getNewUrls(possibleWay));
                }
            }
            evenRun = !evenRun;
        } while (reviewUrls.size() <= limitOfAddress);

        return Stream.of(getReviewForModel(reviewUrls.iterator().next())).collect(Collectors.toList());
        //return reviewUrls.stream().map(this::getReviewForModel).collect(Collectors.toList());
    }

    //--------------------PRIVATE-----------------------

    private boolean addUrl(String url) {
        if (url.contains(optimizingUrl) && !url.contains(".pdf") && !url.contains("https") && !url.contains("blog")) {
            if(url.endsWith("recenze/")){
                reviewUrls.add(url);
            }
            return urls.add(url);
        }
        return false;
    }

    private Set<String> processLeftMenu(){
        Set<String> newUrls = new HashSet<>();
        try {
            userAgent.visit(this.baseUrl);
            Elements links = userAgent.doc.findEvery("<div class=left>").findEvery("<a href>");
            for (Element elem : links) {
                String link = elem.getAt("href");
                if(addUrl(link)){
                    newUrls.add(link);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newUrls;
    }

    private Set<String> getNewUrls(String urlToExplore){
        Set<String> newUrls = new HashSet<>();
        try {
            userAgent.visit(urlToExplore);
            Elements links = userAgent.doc.findEvery("<span class=review-count>").findEvery("<a href>");
            for (Element elem : links) {
                String link = elem.getAt("href");
                if(addUrl(link)){
                    newUrls.add(link);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newUrls;
    }
}

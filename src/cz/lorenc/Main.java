package cz.lorenc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        //CRAWLING PART
        Crawler crawler = new Crawler();

//        for (String s :
//                crawler.getRobotAllowedPages("https://www.heureka.cz/")) {
//            System.out.println(s);
//        }
//        JSONHelper jsonHelper = new JSONHelper();
//        List<Review> allReview = new ArrayList<>();
//
//        crawler.crawl("http://heureka.cz/", 0);
//        allReview = crawler.getUrlsReview().stream().map(crawler::getReviewsForModel).flatMap(List::stream).collect(Collectors.toList());
//
//        jsonHelper.addReview(allReview);


        CrawlerNet.startCrawling(args);
        //END OF CRAWLING PART

    }
}

package cz.lorenc;

import com.jaunt.Document;
import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;
import com.sun.deploy.net.HttpResponse;
import com.sun.deploy.trace.Trace;
import cz.lorenc.TfIdfTable.TFIDFCalculator;
import org.jsoup.Jsoup;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        //CRAWLING PART
//        Crawler crawler = new Crawler();
//        JSONHelper jsonHelper = new JSONHelper();
//        List<Review> allReview = new ArrayList<>();
//
//        crawler.crawl("http://heureka.cz/", 0);
//        allReview = crawler.getUrlsReview().stream().map(crawler::getReviewsForModel).flatMap(List::stream).collect(Collectors.toList());
//
//        jsonHelper.addReview(allReview);

        //END OF CRAWLING PART

//        System.out.println(Translator.translate("kolo"));
//        System.out.println(Translator.translate("auto"));
//        System.out.println(Translator.translate("moto"));

//
        JSONHelper jsonHelper = new JSONHelper();
        List<Review> reviews = jsonHelper.getReviews(JSONHelper.FILE_DESTINATION);
        Preproccesing preproccesing = new Preproccesing();
        List<Review> preproccesingList = preproccesing.doTranslate(reviews);
        jsonHelper.addReview(preproccesingList, JSONHelper.FILE_TRANSLATED_DESTINATION);


//        JSONHelper jsonHelper = new JSONHelper();
//        List<Review> reviews = jsonHelper.getReviews(JSONHelper.FILE_TRANSLATED_DESTINATION);
//        Preproccesing preproccesing = new Preproccesing();
//        List<ReviewPreproccesing> preproccesingList = preproccesing.doPreproccesing(reviews , Preproccesing.PREPROCCESING_URL_ENG);
//        jsonHelper.addReviewPreprocces(preproccesingList, "json_translated_stopwords_token.json");

//        List<ReviewPreproccesing> documents = jsonHelper.getReviewPreprocces(JSONHelper.FILE_DESTINATION_PREPROCCES);
//
//        TFIDFCalculator calculator = new TFIDFCalculator();
//        System.out.println(calculator.tfIdf(documents.get(4),documents,documents.get(4).getReview().get(0)));
    }
}

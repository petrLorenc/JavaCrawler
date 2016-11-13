package cz.lorenc;

import com.jaunt.Document;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;
import com.sun.deploy.net.HttpResponse;
import sun.net.www.http.HttpClient;

import java.util.List;

public class Main {

    public static final String END_URL = " ";
    public static final String BEGIN_URL = "http://mobilni-telefony.heureka.cz/";
    public static final String URL = "http://mobilni-telefony.heureka.cz/";


    public static void main(String[] args) {
        Crawler crawler = new Crawler("http://heureka.cz/", ".heureka.cz");

        JSONHelper jsonHelper = new JSONHelper();
        //List<Review> list = crawler.doCrawling();
        //jsonHelper.addReview(list);
//
        List<Review> reviews = jsonHelper.getReviews();

        Preproccesing preproccesing = new Preproccesing();
        List<ReviewPreproccesing> preproccesingList = preproccesing.doPreproccesing(reviews);
        jsonHelper.addReviewPreprocces(preproccesingList);

    }
}

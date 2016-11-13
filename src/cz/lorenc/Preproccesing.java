package cz.lorenc;

import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by petr.lorenc on 13.11.16.
 */
public class Preproccesing {

    private final String PREPROCCESING_URL = "http://127.0.0.1:9200/i/_analyze?analyzer=cestina_hunspell&pretty=true";

    public List<ReviewPreproccesing> doPreproccesing(List<Review> reviews) {
        UserAgent userAgent = new UserAgent();         //create new userAgent (headless browser)
        String baseUrl = "";
        List<ReviewPreproccesing> reviewPreproccesingList = new ArrayList<>();
        try {
            for (int i = 0; i < reviews.size(); i++) {
                Review review = reviews.get(i);

                if(!baseUrl.equals(review.getUrl().substring(0,review.getUrl().indexOf(".cz")))){
                    baseUrl = review.getUrl().substring(0,review.getUrl().indexOf(".cz"));
                    System.out.println("///////////////////////////////////");
                    System.out.println(baseUrl);
                    System.out.println("///////////////////////////////////");
                }
                System.out.println("=====");


                System.out.println(review.getReview());
                review.getPlus().forEach(System.out::println);
                review.getMinus().forEach(System.out::println);
                System.out.println("=====");

                List<String> terms = new ArrayList<>();

                // TODO: 13.11.16 Make regex for filter only work containing aplhabet
                // Process review
                if (review.getReview().length() > 0) {
                    userAgent.sendPOST(PREPROCCESING_URL, review.getReview());
                    userAgent.json.findEach("token").forEach(t -> terms.add(t.toString()));
                }

                List<String> termsPlus = new ArrayList<>();
                //Process plus
                for (int j = 0; j < review.getPlus().size(); j++) {
                    if(review.getPlus().get(j).length() == 0){
                        continue;
                    }
                    userAgent.sendPOST(PREPROCCESING_URL, review.getPlus().get(j));
                    userAgent.json.findEach("token").forEach(t -> termsPlus.add(t.toString()));
                }

                List<String> termsMinus = new ArrayList<>();
                //Process minus
                for (int j = 0; j < review.getMinus().size(); j++) {
                    if(review.getMinus().get(j).length() == 0){
                        continue;
                    }
                    userAgent.sendPOST(PREPROCCESING_URL, review.getMinus().get(j));
                    userAgent.json.findEach("token").forEach(t -> termsMinus.add(t.toString()));
                }

                reviewPreproccesingList.add(new ReviewPreproccesing(baseUrl,terms,review.getRating(),termsPlus,termsMinus));

                terms.forEach(System.out::println);
            }

        } catch (ResponseException e) {
            e.printStackTrace();
        } catch (NotFound notFound) {
            notFound.printStackTrace();
        }
        return reviewPreproccesingList;
    }
}

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
        List<String> terms = new ArrayList<>();
        List<String> termsPlus = new ArrayList<>();
        List<String> termsMinus = new ArrayList<>();
        try {
            for (int i = 0; i < reviews.size(); i++) {
                Review review = reviews.get(i);


                // TODO: 13.11.16 Make regex for filter only work containing aplhabet
                // Process review
                List<String> finalTerms = new ArrayList<>();
                if (review.getReview().length() > 0) {
                    try {
                        userAgent.sendPOST(PREPROCCESING_URL, review.getReview());
                    } catch (ResponseException e) {
                        System.out.println("ERROR:" + review.getReview());
                        continue;
                    }
                    userAgent.json.findEach("token").forEach(t -> finalTerms.add(t.toString()));
                }

                //Process plus
                List<String> finalTermsPlus = new ArrayList<>();
                for (int j = 0; j < review.getPlus().size(); j++) {
                    if (review.getPlus().get(j).length() == 0) {
                        continue;
                    }
                    try {
                        userAgent.sendPOST(PREPROCCESING_URL, review.getPlus().get(j));
                    } catch (ResponseException e) {
                        System.out.println("ERROR:" + review.getPlus().get(j));
                        continue;
                    }
                    userAgent.json.findEach("token").forEach(t -> finalTermsPlus.add(t.toString()));
                }

                //Process minus
                List<String> finalTermsMinus = new ArrayList<>();
                for (int j = 0; j < review.getMinus().size(); j++) {
                    if (review.getMinus().get(j).length() == 0) {
                        continue;
                    }
                    try {
                        userAgent.sendPOST(PREPROCCESING_URL, review.getMinus().get(j));
                    } catch (ResponseException e) {
                        System.out.println("ERROR:" + review.getMinus().get(j));
                        continue;
                    }
                    userAgent.json.findEach("token").forEach(t -> finalTermsMinus.add(t.toString()));
                }

                reviewPreproccesingList.add(new ReviewPreproccesing(
                        review.getUrl(),finalTerms,review.getRating(),finalTermsPlus,finalTermsMinus,
                        review.getUsefulReview(),review.getUselessReview(),
                        review.getDate(),review.getNameShop()));
            }

        } catch (NotFound notFound) {
            notFound.printStackTrace();
        }
        return reviewPreproccesingList;
    }


    public List<Review> doTranslate(List<Review> reviews) {
        List<Review> reviewPreproccesingList = new ArrayList<>();

        for (int i = 0; i < reviews.size(); i++) {
            Review review = reviews.get(i);
            String reviewText;
            List<String> termsPlus = new ArrayList<>();
            List<String> termsMinus = new ArrayList<>();

            reviewText = Translator.translate(review.getReview());
            for(String line : review.getPlus()){
                String plus = Translator.translate(line);
                termsPlus.add(plus);
            }
            for(String line : review.getMinus()){
                String minus = Translator.translate(line);
                termsPlus.add(minus);
            }

            System.out.println("original: " + review.getReview());
            System.out.println("reviewText: " + reviewText);

            reviewPreproccesingList.add(new Review(
                    review.getUrl(),reviewText,review.getRatingString(),termsPlus,termsMinus,
                    review.getUsefulReview(),review.getUselessReview(),
                    review.getDate(),review.getNameShop()));
        }


        return reviewPreproccesingList;
    }
}

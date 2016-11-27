package cz.lorenc.TfIdfTable;

import cz.lorenc.ReviewPreproccesing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by petr.lorenc on 13.11.16.
 */
public class TFIDFCalculator {
    private double tf(List<String> doc, String term) {
        double result = 0;
        for (String word : doc) {
            if (term.equalsIgnoreCase(word))
                result++;
        }
        return result / doc.size();
    }

    private double idf(List<List<String>> docs, String term) {
        double n = 0;
        for (List<String> doc : docs) {
            for (String word : doc) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }
        return Math.log(docs.size() / n);
    }

    private double tfIdf(List<String> doc, List<List<String>> docs, String term) {
        return tf(doc, term) * idf(docs, term);
    }

    public TFIDFResult tfIdf(ReviewPreproccesing doc, List<ReviewPreproccesing> docs, String term){
        List<List<String>> reviews = new ArrayList<>();
        List<List<String>> pluses = new ArrayList<>();
        List<List<String>> minuses = new ArrayList<>();

        for (ReviewPreproccesing review : docs) {
            reviews.add(review.getReview());
            pluses.add(review.getPlus());
            minuses.add(review.getMinus());
        }

        return new TFIDFResult(term,
                tfIdf(doc.getReview(),reviews,term),
                tfIdf(doc.getPlus(),pluses,term),
                tfIdf(doc.getMinus(),minuses,term));
    }
}

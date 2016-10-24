package cz.lorenc;

import java.util.List;

/**
 * Created by United121 on 10/23/2016.
 */
public class Review {
    private String review;
    private String rating;
    private List<String> plus;
    private List<String> minus;

    public Review(String review, String rating, List<String> plus, List<String> minus) {
        this.review = review;
        this.rating = rating;
        this.plus = plus;
        this.minus = minus;
    }

    public String getReview() {
        return review;
    }

    public String getRating() {
        return rating;
    }

    public List<String> getPlus() {
        return plus;
    }

    public List<String> getMinus() {
        return minus;
    }

    @Override
    public String toString() {
        return "Review{" +
                "review='" + review + '\'' +
                ", rating='" + rating + '\'' +
                ", plus=" + plus +
                ", minus=" + minus +
                '}';
    }
}

package cz.lorenc;

import java.util.List;

/**
 * Created by United121 on 10/23/2016.
 */
public class ReviewPreproccesing {
    private String url;
    private List<String> review;
    private int rating;
    private List<String> plus;
    private List<String> minus;
    private int usefulReview;
    private int uselessReview;
    private String date;
    private String nameShop;

    public ReviewPreproccesing(String url, List<String> review, int rating, List<String> plus, List<String> minus, int usefulReview, int uselessReview, String date, String nameShop) {
        this.url = url;
        this.review = review;
        this.rating = rating;
        this.plus = plus;
        this.minus = minus;
        this.usefulReview = usefulReview;
        this.uselessReview = uselessReview;
        this.date = date;
        this.nameShop = nameShop;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setReview(List<String> review) {
        this.review = review;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setPlus(List<String> plus) {
        this.plus = plus;
    }

    public void setMinus(List<String> minus) {
        this.minus = minus;
    }

    public List<String> getReview() {
        return review;
    }

    public int getRating() {
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

package cz.lorenc.TfIdfTable;

/**
 * Created by petr.lorenc on 14.11.16.
 */
public class TFIDFResult {
    String term;
    double reviewScore;
    double plusScore;
    double minusScore;

    public TFIDFResult(String term, double reviewScore, double plusScore, double minusScore) {
        this.term = term;
        this.reviewScore = reviewScore;
        this.plusScore = plusScore;
        this.minusScore = minusScore;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public double getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(double reviewScore) {
        this.reviewScore = reviewScore;
    }

    public double getPlusScore() {
        return plusScore;
    }

    public void setPlusScore(double plusScore) {
        this.plusScore = plusScore;
    }

    public double getMinusScore() {
        return minusScore;
    }

    public void setMinusScore(double minusScore) {
        this.minusScore = minusScore;
    }

    @Override
    public String toString() {
        return "TFIDFResult{" +
                "term='" + term + '\'' +
                ", reviewScore=" + reviewScore +
                ", plusScore=" + plusScore +
                ", minusScore=" + minusScore +
                '}';
    }
}

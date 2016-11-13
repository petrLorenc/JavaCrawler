package cz.lorenc.tokens;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tokens {

    @SerializedName("tokens")
    @Expose
    private List<Token> tokens = new ArrayList<Token>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Tokens() {
    }

    /**
     *
     * @param tokens
     */
    public Tokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     *
     * @return
     * The tokens
     */
    public List<Token> getTokens() {
        return tokens;
    }

    /**
     *
     * @param tokens
     * The tokens
     */
    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

}

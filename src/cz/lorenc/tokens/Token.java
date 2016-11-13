package cz.lorenc.tokens;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("start_offset")
    @Expose
    private int startOffset;
    @SerializedName("end_offset")
    @Expose
    private int endOffset;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("position")
    @Expose
    private int position;

    /**
     * No args constructor for use in serialization
     *
     */
    public Token() {
    }

    /**
     *
     * @param position
     * @param endOffset
     * @param token
     * @param type
     * @param startOffset
     */
    public Token(String token, int startOffset, int endOffset, String type, int position) {
        this.token = token;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.type = type;
        this.position = position;
    }

    /**
     *
     * @return
     * The token
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     * The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     *
     * @return
     * The startOffset
     */
    public int getStartOffset() {
        return startOffset;
    }

    /**
     *
     * @param startOffset
     * The start_offset
     */
    public void setStartOffset(int startOffset) {
        this.startOffset = startOffset;
    }

    /**
     *
     * @return
     * The endOffset
     */
    public int getEndOffset() {
        return endOffset;
    }

    /**
     *
     * @param endOffset
     * The end_offset
     */
    public void setEndOffset(int endOffset) {
        this.endOffset = endOffset;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The position
     */
    public int getPosition() {
        return position;
    }

    /**
     *
     * @param position
     * The position
     */
    public void setPosition(int position) {
        this.position = position;
    }

}

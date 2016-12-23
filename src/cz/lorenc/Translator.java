package cz.lorenc;

import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Created by petr.lorenc on 23.12.16.
 */
public class Translator {

    public static String translate(String sourceText){
        try {
            String responseBody = Jsoup.connect("https://translate.googleapis.com/translate_a/single?client=gtx&sl=cs&tl=en&dt=t&q=" + sourceText)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
                    .ignoreContentType(true).execute().body();
            return responseBody.substring(4, responseBody.indexOf("\"" , 4));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}

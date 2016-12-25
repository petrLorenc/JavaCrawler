package cz.lorenc;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by petr.lorenc on 23.12.16.
 */
public class Translator {

    private static final Pattern parseAnswer = Pattern.compile("(?<=\")(.*?)(?=\",)");
    private static final Pattern parseAnswerPostProcess = Pattern.compile("(?<=\")(.*)");

    public static List<String> translate(String sourceText){
        List<String> translatedStrings = new ArrayList<>();
        try {
            String responseBody = Jsoup.connect("https://translate.googleapis.com/translate_a/single?client=gtx&sl=cs&tl=en&dt=t&q=" + sourceText)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
                    .ignoreContentType(true).execute().body();

            Matcher responseMatches = parseAnswer.matcher(responseBody);

            int counter = 0;

            while(responseMatches.find()){
                if(counter % 2 == 0){
                    counter++;
                } else {
                    counter++;
                    continue;
                }

                String answer = responseMatches.group();
                Matcher matcher = parseAnswerPostProcess.matcher(answer);
                if(matcher.find()){
                   answer = matcher.group();
                }
                //System.out.println(answer);
                translatedStrings.add(answer);
            }

            return translatedStrings;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return translatedStrings;
    }
}

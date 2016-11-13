package cz.lorenc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by United121 on 10/28/2016.
 */
public class JSONHelper {

    public static String FILE_DESTINATION = "file.json";
//    public static String FILE_DESTINATION_PREPROCCES = "file_preprocess.json";
    public static String FILE_DESTINATION_PREPROCCES = "file_preprocess_category.json";

    public void addReview(List<Review> value) {

        Gson gson = new GsonBuilder().create();
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(FILE_DESTINATION) , "UTF-8")) {
            writer.write("[");
            //writer.write(gson.toJson(value, Review.class));
            for (int i = 0; i < value.size(); i++) {
                gson.toJson(value.get(i), Review.class, writer);
                if (i != value.size() - 1) {
                    writer.write(",");
                }
            }
            writer.write("]");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addReviewPreprocces(List<ReviewPreproccesing> value) {

        Gson gson = new GsonBuilder().create();
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(FILE_DESTINATION_PREPROCCES) , "UTF-8")) {
            writer.write("[");
            //writer.write(gson.toJson(value, Review.class));
            for (int i = 0; i < value.size(); i++) {
                gson.toJson(value.get(i), ReviewPreproccesing.class, writer);
                if (i != value.size() - 1) {
                    writer.write(",");
                }
            }
            writer.write("]");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Review> getReviews() {

        Gson gson = new GsonBuilder().create();
        List<Review> reviews = null;

        try (FileReader reader = new FileReader(FILE_DESTINATION)) {
            reviews = Stream.of(gson.fromJson(reader, Review[].class)).collect(Collectors.<Review>toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return reviews;
    }


}

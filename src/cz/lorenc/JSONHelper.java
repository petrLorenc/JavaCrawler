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

    public void addReview(List<Review> value) {

//        JSONObject obj = new JSONObject();
//        obj.put("Url", value.getUrl());
//        obj.put("Review", value.getReview());
//        obj.put("Rating", value.getRating());
//
//        obj.put("Plus", new JSONArray().addAll(value.getPlus()));
//        obj.put("Minus", new JSONArray().addAll(value.getPlus()));
//
//        try (FileWriter file = new FileWriter(FILE_DESTINATION, true)) {
//            file.write(obj.toJSONString());
//        }

        Gson gson = new GsonBuilder().create();
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(FILE_DESTINATION) , "UTF-8")) {
//            JSONArray array =  new JSONArray();
//            writer.write(array.toJSONString());

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
        //JsonElement json = gson.fromJson(new FileReader("file.json"), JsonElement.class);
        //json.getAsJsonArray().add(gson.toJson(value, Review.class));
        //gson.toJson(value, new FileWriter("file.json",true));

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

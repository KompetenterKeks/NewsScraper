import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        HttpClient client = HttpClient.newHttpClient();
        JSONParser parser = new JSONParser();

        Integer date = 230330;

        JSONArray news = getNewsfeed(client, parser, date);

        JSONObject test = (JSONObject) news.get(0);
        System.out.println(test.get("sophoraId"));
    }

    private static JSONArray getNewsfeed(HttpClient client, JSONParser parser, Integer date) throws ParseException {
        // Prepare API URL with the right date
        String URL = String.format("https://www.tagesschau.de/api2/newsfeed-101~_date-%d.json", date);

        // Create a new HTTP request for accessing the API
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("accept", "application/json")
                .build();

        // Parse and return the newsfeed in form of a JSON Array
        String resp = client.sendAsync(request, BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();

        JSONObject data = (JSONObject) parser.parse(resp);
        return (JSONArray) data.get("news");
    }
}


// Cursor Parking
// __________
// |  |  |  |
// ‾‾‾‾‾‾‾‾‾‾


// curl -X 'GET' 'https://www.tagesschau.de/api2/newsfeed-101~_date-230331.json' -H 'accept: application/json'

//h
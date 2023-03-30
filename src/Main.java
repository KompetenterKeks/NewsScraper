import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) {

        int date = 230330;
        String URL = String.format("https://www.tagesschau.de/api2/newsfeed-101~_date-%d.json", date);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("accept", "application/json")
                .build();

        String resp = client.sendAsync(request, BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();


        JSONParser parser = new JSONParser();

        try {
			
            JSONObject data = (JSONObject) parser.parse(resp);
			
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
// Cursor Parking
// __________
// |  |  |  |
// ‾‾‾‾‾‾‾‾‾‾


// curl -X 'GET' 'https://www.tagesschau.de/api2/newsfeed-101~_date-230331.json' -H 'accept: application/json'

//h
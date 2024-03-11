package superapp.api;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
public class MealPlannerAPI
{
    private static final String API_KEY ="61ab61866846523324bf08c85f222d0f";
    private static final String APPLICATION_ID ="23dedf46";

    private static final String url = "https://api.edamam.com/api/meal-planner/v1";


    private static final AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();

    private static final Logger logger = LoggerFactory.getLogger(MealPlannerAPI.class);

    public static Mono<String> mealPlanner(int minCal, int maxCal) {
        String url = "https://api.edamam.com/api/meal-planner/v1" ;

        return Mono.fromFuture(() ->
                asyncHttpClient.prepareGet(url)
                        .setHeader("API-Key", API_KEY)
                        .setHeader("Host", url)
                        .execute()
                        .toCompletableFuture()
                        .thenApply(response -> {
                            String responseBody = response.getResponseBody();
                            logger.info("Response from meal planner: " + responseBody);
                            return responseBody;
                        }));
    }



}

package superapp.api;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class FitnessCalculatorApi {
    private static final String XRapidAPIKey = "2e8a24256dmshbea0683453efadfp17340ejsne6f37e095a15";
    private static final String RAPID_API_HOST = "fitness-calculator.p.rapidapi.com";

    // private final WebClient webClient;

    private static final AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();

    private static final Logger logger = LoggerFactory.getLogger(FitnessCalculatorApi.class);


    public static Mono<String> calculateBMI(double height, double weight, int age) {
        String url = "https://fitness-calculator.p.rapidapi.com/bmi?age=" + age + "&weight=" + weight + "&height=" + height;
        return Mono.fromFuture(() ->
                asyncHttpClient.prepareGet(url)
                        .setHeader("X-RapidAPI-Key", XRapidAPIKey)
                        .setHeader("X-RapidAPI-Host", RAPID_API_HOST)
                        .execute()
                        .toCompletableFuture()
                        .thenApply(response -> {
                            String responseBody = response.getResponseBody();
                            logger.info("Response from fitness calculator: " + responseBody);
                            return responseBody;
                        }));
    }

    public static Mono<String> calculateFatPercentage(int age, String gender, double weight, double height, double waist, double hip, double neck) {
        String url = "https://fitness-calculator.p.rapidapi.com/bodyfat?age=" + age + "&gender=" + gender +
                "&weight=" + weight + "&height=" + height + "&neck=" + neck + "&waist=" + waist + "&hip=" + hip;
        return Mono.fromFuture(() ->
                asyncHttpClient.prepareGet(url)
                        .setHeader("X-RapidAPI-Key", XRapidAPIKey)
                        .setHeader("X-RapidAPI-Host", RAPID_API_HOST)
                        .execute()
                        .toCompletableFuture()
                        .thenApply(response -> {
                            String responseBody = response.getResponseBody();
                            logger.info("Response from fitness calculator: " + responseBody);
                            return responseBody;
                        }));

    }
    
    public static Mono<String> calculateDailyCalory(int age, String gender, double height, double weight, String activityLevel) {
        String url = "https://fitness-calculator.p.rapidapi.com/bodyfat?age=" + age + "&gender=" + gender +
                "&weight=" + weight + "&height=" + height + "&activitylevel=" +activityLevel;
        return Mono.fromFuture(() ->
                asyncHttpClient.prepareGet(url)
                        .setHeader("X-RapidAPI-Key", XRapidAPIKey)
                        .setHeader("X-RapidAPI-Host", RAPID_API_HOST)
                        .execute()
                        .toCompletableFuture()
                        .thenApply(response -> {
                            String responseBody = response.getResponseBody();
                            logger.info("Response from fitness calculator: " + responseBody);
                            return responseBody;
                        }));

    }
    
    public static Mono<String> calculateIdelWeight(String gender, double height) {
        String url = "https://fitness-calculator.p.rapidapi.com/idealweight?gender=" + gender + "&height=" + height;
        return Mono.fromFuture(() ->
                asyncHttpClient.prepareGet(url)
                        .setHeader("X-RapidAPI-Key", XRapidAPIKey)
                        .setHeader("X-RapidAPI-Host", RAPID_API_HOST)
                        .execute()
                        .toCompletableFuture()
                        .thenApply(response -> {
                            String responseBody = response.getResponseBody();
                            logger.info("Response from fitness calculator: " + responseBody);
                            return responseBody;
                        }));

    }
}
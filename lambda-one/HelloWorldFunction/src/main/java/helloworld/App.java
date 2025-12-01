package helloworld;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        try {
            // Read POST body (may be null if no body sent)
            String requestBody = input != null ? input.getBody() : null;
            if (requestBody == null || requestBody.trim().isEmpty()) {
                requestBody = "{}";
            }

            // Example response: echo the body back with a message
            String output = String.format("{ \"message\": \"hello world\", \"requestBody\": %s }", requestBody);

            return response
                    .withStatusCode(200)
                    .withBody(output);
        } catch (Exception e) {
            return response
                    .withBody("{\"error\":\"internal error\"}")
                    .withStatusCode(500);
        }
    }
}

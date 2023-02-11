package processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.InputModel;
import model.OutputModel;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Map;

import static java.net.http.HttpClient.*;

@ApplicationScoped
public class MessageProcessor implements Processor {
    private static Logger log = LoggerFactory.getLogger(MessageProcessor.class);

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void process(Exchange exchange) throws JsonProcessingException {
        var input = exchange.getIn().getBody(InputModel.class);
        log.info("Message received: {}", input.toString());
        Date now = new Date();
        var output = new OutputModel();
        var id = exchange.getProperty("RandomId");
        log.info("Integer from headers: " + id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://dummyjson.com/products/" + id))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Response received: {}", response.body());
        Map<String, String> body = objectMapper.readValue(response.body(), Map.class);
        output.setEnrichment(body.get("title"));
        output.setMessage(input.getMessage());
        output.setProcessedDate(now);
        log.info("Output given: {}", output);
        exchange.getIn().setBody(output);
    }
}
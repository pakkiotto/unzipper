import model.InputModel;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.zipfile.ZipSplitter;
import org.apache.camel.model.dataformat.JsonLibrary;
import processor.MessageProcessor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.ThreadLocalRandom;

@ApplicationScoped
public class ZipRoute extends RouteBuilder {
    @Inject
    private MessageProcessor messageProcessor;

    @Override
    public void configure() throws Exception {
        from("file:files/in?moveFailed=.error")
                .routeId("ZipRoute")
                .log(LoggingLevel.INFO, "start ingestion file from ZipRoute: ${header.CamelFileName}")
                .split(new ZipSplitter())
                    .streaming()
                    .log(LoggingLevel.INFO, "start ingestion of splitted file from zip: ${header.CamelFileName}")
                    .setProperty("RandomId", simple(String.valueOf(ThreadLocalRandom.current().nextInt(1, 12))))
                    .unmarshal()
                    .json(JsonLibrary.Jackson, InputModel.class)
                    .process(messageProcessor)
                .marshal()
                .json(JsonLibrary.Jackson)
                .to("file:files/out");
    }

}
package hr.javacro.tn;

import hr.javacro.tn.adapter.TempSensorProducer;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ApplicationScoped
public class FlashGordonMain {

    @Inject
    Logger LOGGER;

    @ConfigProperty(name = "app.file.location")
    String fileLocation;

    @Inject
    TempSensorProducer producer;

    @Scheduled(every = "2s")
    void increment() {
        Path path = Paths.get(fileLocation);

        if (!Files.isReadable(path)) {
            LOGGER.error("Error while loading file");
            return;
        }

        try {
            String line = Files.readString(path).trim();
            producer.sendData(line);
        } catch (Exception e) {
            LOGGER.error("Error while sending data", e);
        }
    }
}
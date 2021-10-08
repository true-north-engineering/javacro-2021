package hr.javacro.tn;

import hr.javacro.tn.adapter.TempSensorProducer;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@QuarkusMain
public class FlashGordonMain implements QuarkusApplication {

    @Inject
    Logger LOGGER;

    @ConfigProperty(name = "directory.location")
    String directoryLocation;

    @Inject
    TempSensorProducer producer;

    @Override
    public int run(String... args) throws Exception {
        Path path = Paths.get(directoryLocation);
        if (!Files.isDirectory(path) || !Files.isReadable(path)) {
            LOGGER.error("Error while loading hot folder");
            return 1;
        }
        while (true) {
            try {
                List<Path> files = listFilesInDir(path);
                for (Path file : files) {
                    List<String> lines = Files.lines(file).collect(Collectors.toList());
                    lines.forEach(l -> producer.sendData(l));

                }
            } catch (IOException e) {
                LOGGER.error("Error while deleting file", e);
                return 0;
            }
        }
    }

    private List<Path> listFilesInDir(Path path) throws IOException {
        List<Path> files;
        try (Stream<Path> stream = Files.list(path)) {
            files = stream.collect(Collectors.toList());
        }
        return files;
    }

    void onStop(@Observes ShutdownEvent ev) {
        Quarkus.asyncExit();
        LOGGER.info("The application is stopping...");
    }
}



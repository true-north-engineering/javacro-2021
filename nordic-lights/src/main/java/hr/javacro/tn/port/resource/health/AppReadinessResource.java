package hr.javacro.tn.port.resource.health;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import java.nio.file.Files;
import java.nio.file.Path;

@Readiness
@ApplicationScoped
public class AppReadinessResource implements HealthCheck {

    @ConfigProperty(name = "directory.location")
    String directoryLocation;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Hot folder connection health check");
        Path path = Path.of(directoryLocation);
        if (!Files.isDirectory(path) || !Files.isReadable(path)) {
            responseBuilder.down();
        } else
        responseBuilder.up();

        return responseBuilder.build();
    }
}

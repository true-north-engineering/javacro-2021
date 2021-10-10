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

    @ConfigProperty(name = "app.file.location")
    String fileLocation;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("File health check");
        Path path = Path.of(fileLocation);
        if (!Files.isReadable(path)) {
            responseBuilder.down();
        } else
        responseBuilder.up();

        return responseBuilder.build();
    }
}

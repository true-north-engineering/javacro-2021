package hr.javacro.tn.adapter;

import hr.javacro.tn.domain.model.TempSensor;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.InetAddress;
import java.net.UnknownHostException;

@ApplicationScoped
public class TempSensorProducer {

    @Inject
    Logger LOGGER;

    @Channel("temp-sensor-data")
    Emitter<TempSensor> tempSensorEmitter;

    private final String name;

    public TempSensorProducer() {
        String hostname;
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostname = "Unknown";
        }

        name = hostname;
    }

    public void sendData(String data) {
        LOGGER.info("Producer Record: " + data);
        String[] tmp = data.split(",");
        tempSensorEmitter.send(new TempSensor(name, tmp[0]));
    }
}

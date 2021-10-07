package hr.javacro.tn.adapter;

import hr.javacro.tn.domain.model.TempSensor;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TempSensorProducer {

    @Inject
    Logger LOGGER;

    @Channel("temp-sensor-data")
    Emitter<TempSensor> tempSensorEmitter;


    public void sendProducerRecord(TempSensor data) {
        LOGGER.info("Producer Record: " + data);
        tempSensorEmitter.send(data);
    }
}

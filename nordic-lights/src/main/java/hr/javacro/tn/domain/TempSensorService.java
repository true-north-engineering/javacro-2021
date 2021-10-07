package hr.javacro.tn.domain;

import hr.javacro.tn.adapter.TempSensorProducer;
import hr.javacro.tn.domain.model.TempSensor;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TempSensorService {

    @Inject
    Logger LOGGER;

    @Inject
    TempSensorProducer producer;

    public void sendData(String data) {
        LOGGER.info("Sending data: " + data);
        producer.sendProducerRecord(new TempSensor("1.0C", "test"));
    }
}

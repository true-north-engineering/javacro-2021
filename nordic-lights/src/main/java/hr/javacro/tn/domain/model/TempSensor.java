package hr.javacro.tn.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TempSensor {
    private String name;
    private String temp;
}

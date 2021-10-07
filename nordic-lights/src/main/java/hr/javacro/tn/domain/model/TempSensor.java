package hr.javacro.tn.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TempSensor {
    private String temp;
    private String name;
}

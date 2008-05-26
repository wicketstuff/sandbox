package org.wicketstuff.objectautocomplete.example;

import java.util.List;
import java.util.Arrays;

/**
 * @author roland
 * @since May 26, 2008
 */
public class CarRepository {
    public static List<Car> allCars() {
                return Arrays.asList(
                new Car(1,"Audi"),
                new Car(2,"BMW"),
                new Car(3,"Benz"),
                new Car(4,"Renault"));
    }
}

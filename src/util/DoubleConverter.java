/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javafx.util.StringConverter;

/**
 *
 * @author Jan
 */
public class DoubleConverter extends StringConverter<Double> {

    @Override
    public String toString(Double value) {
        if (value == null) {
            return "";
        }

        return Double.toString(value.doubleValue());
    }

    @Override
    public Double fromString(String value) {
        if (value == null) {
            return null;
        }

        double db = 0;

        value = value.trim();
        try {
            db = Double.valueOf(value);
        } catch (Exception ex) {

        }

        if (db < -273.15) {
            db = -273.15;
        }
        return db;
    }

}

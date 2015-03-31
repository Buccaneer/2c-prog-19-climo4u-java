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
public class IntegerConverter extends StringConverter<Integer> {

    @Override
    public String toString(Integer value) {
         if (value == null) {
            return "";
        }

        return Integer.toString(value.intValue());
    }

    @Override
    public Integer fromString(String value) {
        if (value == null) {
            return null;
        }

        int db = 0;

        value = value.trim();
        try {
            db = Integer.valueOf(value);
        } catch (Exception ex) {

        }

        if (db < 0) {
            db = 0;
        }
        return db;
    }

}

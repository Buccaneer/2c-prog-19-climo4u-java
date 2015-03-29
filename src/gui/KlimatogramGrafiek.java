/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dto.KlimatogramDto;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Jan
 */
public class KlimatogramGrafiek {

    public StackPane createChart(KlimatogramDto klimatogram) {
        return layerCharts(createBarChart(klimatogram), createLineChart(klimatogram));
    }

    private StackPane layerCharts(final XYChart<String, Number>... charts) {
        StackPane stackpane = new StackPane();
        stackpane.getChildren().addAll(charts);

        return stackpane;
    }

    private NumberAxis createYaxisNeerslag(KlimatogramDto klimatogram) {
        double[] temperaturen = klimatogram.maanden.stream().mapToDouble(m -> m.getTemperatuur()).toArray();
        int[] neerslagen = klimatogram.maanden.stream().mapToInt(m -> m.getNeerslag()).toArray();

        double max;
        double maxNeerslag = Arrays.stream(neerslagen).max().getAsInt();
        double maxTemperatuur = Arrays.stream(temperaturen).max().getAsDouble();

        if (maxTemperatuur > maxNeerslag) {
            max = maxTemperatuur * 2;
        } else {
            max = maxNeerslag;
        }

        double min = Arrays.stream(temperaturen).min().getAsDouble();
        if (min >= 0) {
            min = 0;
        }

        max = max + ((max % 10) > 0 ? 10 : 0);
        max += (max / 10) % 2 != 0 ? 10 : 0;

        if (min >= 0) {
            if (min % 10 != 0) {
                min += 10 - (min % 10);
            }
        } else {
            if (min % 10 != 0) {
                min -= 10 + (min % 10);
            }
        }
        int tickInterval = max > 100 ? 20 : 10;

        max += 10;

        final NumberAxis axis = new NumberAxis("Neerslagen in mm", min * 2, max, tickInterval);
        axis.setMinorTickCount(10);
        axis.setTranslateX(0);
        axis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(axis) {
            @Override
            public String toString(Number object) {
                return object.intValue() >= 0 ? String.format("%d", object.intValue()) : "";
            }
        });

        return axis;
    }

    private NumberAxis createYAxisTemperatuur(KlimatogramDto klimatogram) {
        double[] temperaturen = klimatogram.maanden.stream().mapToDouble(m -> m.getTemperatuur()).toArray();
        int[] neerslagen = klimatogram.maanden.stream().mapToInt(m -> m.getNeerslag()).toArray();

        double max;
        double maxNeerslag = Arrays.stream(neerslagen).max().getAsInt();
        double maxTemperatuur = Arrays.stream(temperaturen).max().getAsDouble();

        if (maxTemperatuur > maxNeerslag) {
            max = maxTemperatuur * 2;
        } else {
            max = maxNeerslag;
        }

        double min = Arrays.stream(temperaturen).min().getAsDouble();
        if (min >= 0) {
            min = 0;
        }

        max = max + ((max % 10) > 0 ? 10 : 0);
        max += (max / 10) % 2 != 0 ? 10 : 0;

        if (min >= 0) {
            if (min % 10 != 0) {
                min += 10 - (min % 10);
            }
        } else {
            if (min % 10 != 0) {
                min -= 10 + (min % 10);
            }
        }
        int tickInterval = max > 100 ? 20 : 10;

        max += 10;

        final NumberAxis axis = new NumberAxis("Temperaturen in °C", min, max / 2, tickInterval / 2);
        axis.setMinorTickCount(10);
        axis.setSide(Side.RIGHT);
        axis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(axis) {
            @Override
            public String toString(Number object) {
                return String.format("%d", object.intValue());
            }
        });
        return axis;
    }

    private BarChart<String, Number> createBarChart(KlimatogramDto klimatogram) {
        final BarChart<String, Number> chart = new BarChart<>(new CategoryAxis(), createYaxisNeerslag(klimatogram));
        setDefaultChartProperties(chart);
        ObservableList neerslagen = FXCollections.observableArrayList();
        klimatogram.maanden.forEach(m -> neerslagen.add(new XYChart.Data(m.getNaam().substring(0, 3), m.getNeerslag())));
        chart.getData().addAll(new XYChart.Series(neerslagen));
        chart.setVerticalGridLinesVisible(false);
        return chart;
    }

    private LineChart<String, Number> createLineChart(KlimatogramDto klimatogram) {
        final LineChart<String, Number> chart = new LineChart<>(new CategoryAxis(), createYAxisTemperatuur(klimatogram));
        setDefaultChartProperties(chart);
        chart.setCreateSymbols(false);
        ObservableList temperaturen = FXCollections.observableArrayList();
        klimatogram.maanden.forEach(m -> temperaturen.add(new XYChart.Data(m.getNaam().substring(0, 3), m.getTemperatuur())));
        chart.getData().addAll(new XYChart.Series(temperaturen));
        chart.setTranslateX(47);
        chart.setAlternativeRowFillVisible(false);
        chart.setAlternativeColumnFillVisible(false);
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.getXAxis().setVisible(false);
        chart.getYAxis().setVisible(false);
        chart.getXAxis().setOpacity(0);
        return chart;
    }

    private void setDefaultChartProperties(final XYChart<String, Number> chart) {
        chart.setLegendVisible(false);
        chart.setAnimated(false);
    }
}

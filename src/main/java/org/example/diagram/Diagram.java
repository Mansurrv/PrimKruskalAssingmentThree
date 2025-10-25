package org.example.diagram;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Diagram extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Algorithm Performance Comparison");

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Graph ID");
        yAxis.setLabel("Execution Time (ms)");

        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Kruskal vs Prim vs Prim Optimized");

        XYChart.Series<String, Number> kruskal = new XYChart.Series<>();
        kruskal.setName("Kruskal");

        XYChart.Series<String, Number> prim = new XYChart.Series<>();
        prim.setName("Prim");

        XYChart.Series<String, Number> primOpt = new XYChart.Series<>();
        primOpt.setName("Prim Optimized");

        String csvFile = "compareResult/comparison_results.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] values = line.split(",");

                if (values.length < 15) continue;

                String graphId = values[0].trim();
                double kruskalTime = Double.parseDouble(values[11]);
                double primTime = Double.parseDouble(values[12]);
                double primOptTime = Double.parseDouble(values[13]);

                kruskal.getData().add(new XYChart.Data<>(graphId, kruskalTime));
                prim.getData().add(new XYChart.Data<>(graphId, primTime));
                primOpt.getData().add(new XYChart.Data<>(graphId, primOptTime));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        chart.getData().addAll(kruskal, prim, primOpt);

        Scene scene = new Scene(chart, 900, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

package com.minesweeper;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class ExampleApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final StringProperty greeting = new SimpleStringProperty("");
    private final StringProperty name = new SimpleStringProperty("");

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent(), 600, 400);

        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

    private Region createContent() {
        VBox results = new VBox(20, createNameInput(), createHelloButton(), createOutputLabel());
        results.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/css/style.css")).toExternalForm());
        results.setAlignment(Pos.CENTER);

        return results;
    }

    private Node createNameInput() {
        TextField inputField = new TextField("");
        inputField.textProperty().bindBidirectional(name);

        HBox results = new HBox(6, createStyledLabel("Nome: ", "prompt-label"), inputField);
        results.setAlignment(Pos.CENTER);

        return results;
    }

    private Node createHelloButton() {
        Button results = new Button("Agraceie-me");
        results.setOnAction(event -> setGreeting());

        return results;
    }

    private Node createOutputLabel() {
        Label results = createStyledLabel("", "greeting-label");
        results.textProperty().bind(greeting);

        return results;
    }

    private Label createStyledLabel(String text, String cssClass) {
        Label result = new Label(text);
        result.getStyleClass().add(cssClass);

        return result;
    }

    private void setGreeting() {
        greeting.setValue("Olá " + name.get());
    }
}
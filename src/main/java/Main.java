import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Calc calc = new Calc();
        Label display = new Label("0");
        display.getStyleClass().add("display");
        display.setMinWidth(0);
        display.setMaxWidth(Double.MAX_VALUE);
        StackPane displayArea = new StackPane(display);
        displayArea.setAlignment(Pos.BOTTOM_RIGHT);

        String[][] keys = {
            {"AC", "±", "%", "÷"},
            {"7", "8", "9", "×"},
            {"4", "5", "6", "−"},
            {"1", "2", "3", "+"},
            {"0", ".", "="}
        };

        GridPane keypad = new GridPane();
        keypad.setHgap(10);
        keypad.setVgap(10);
        for (int col = 0; col < 4; col++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(25);
            column.setFillWidth(true);
            keypad.getColumnConstraints().add(column);
        }

        for (int row=0; row<keys.length; row++){
            for (int col=0; col<keys[row].length; col++){

                String key = keys[row][col];
                String type;

                if (key.equals("AC") || key.equals("±") || key.equals("%")){
                    type = "function-key";
                } else if ("÷×−+=".contains(key)) {
                    type = "operator-key";
                } else {
                    type = "digit-key";
                }

                Button button = makeButton(key, type);
                button.setOnAction(event -> display.setText(calc.press(key)));

                if (key.equals("0")){
                    keypad.add(button, 0, row, 2, 1);
                    button.getStyleClass().add("zero-key");
                } else {
                    int column = row == 4 ? col + 1 : col;
                    keypad.add(button, column, row);
                }
            }
        }

        VBox root = new VBox(16);
        root.getStyleClass().add("calculator");
        root.getChildren().addAll(displayArea, keypad);

        VBox.setVgrow(displayArea, Priority.ALWAYS);

        root.setSpacing(16);
        root.setPadding(new Insets(24, 18, 24, 18));

        displayArea.widthProperty().addListener((observable, oldWidth, newWidth) ->
                resizeDisplay(display, newWidth.doubleValue()));
        display.textProperty().addListener((observable, oldText, newText) ->
                resizeDisplay(display, displayArea.getWidth()));

        Scene scene = new Scene(root, 390, 600);
        scene.getStylesheets().add(
                getClass().getResource("/styles.css").toExternalForm()
        );
        stage.setTitle("Calculator");
        stage.setMinWidth(280);
        stage.setScene(scene);
        stage.show();
    }

    private Button makeButton(String textContent, String type){
        Button button = new Button(textContent);
        button.getStyleClass().addAll("key", type);
        button.setMinWidth(0);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setPrefHeight(80);
        return button;
    }

    private void resizeDisplay(Label display, double width) {
        double size = Math.min(58, (width - 8) / (display.getText().length() * 0.6));
        display.setStyle("-fx-font-size: " + Math.max(18, size) + "px;");
    }

    public static void main(String[] args){
        launch(args);
    }
}

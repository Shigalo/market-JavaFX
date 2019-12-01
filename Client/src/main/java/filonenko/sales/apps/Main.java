package filonenko.sales.apps;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/loginWork/sample.fxml"));
        primaryStage.setTitle("Продажи компьютерной техники");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("file:src/main/resources/images/icon.png"));
        primaryStage.getScene().getStylesheets().add("css/style.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
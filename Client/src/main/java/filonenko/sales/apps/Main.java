package filonenko.sales.apps;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/sample.fxml"));
        primaryStage.setTitle("Продажи компьютерной техники");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}

/*FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(Controller.class.getResource("/FXML/browse.fxml"));
                        Pane page = (Pane) loader.load();
                        Stage dialogStage = new Stage();
                        dialogStage.initModality(Modality.WINDOW_MODAL);
                        dialogStage.setScene(new Scene(page));
                        Browse controller = loader.getController();
                        controller.setDialogStage(dialogStage, str, list);
                        dialogStage.show();*/

/*Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Ошибка");
                        alert.setHeaderText(null);
                        alert.setContentText("Данные не выбраны!");
                        alert.showAndWait();*/

//                passwordConfirm.setPromptText("pass");
//        passwordField.textProperty().addListener((observable, oldValue, newValue) -> saveButton.setDisable(newValue.trim().isEmpty()));

package ua.edu.sumdu.j2se.gorenko.pavel.tasks;
/*
 * это основной запускаемый модуль программы
 * */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.gorenko.pavel.tasks.controller.TaskManagerController;
import ua.edu.sumdu.j2se.gorenko.pavel.tasks.controller.TaskDetailsController;
import ua.edu.sumdu.j2se.gorenko.pavel.tasks.model.*;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    final static String titleApp = "Task Manager";
    final static String icoApp = "file:ico/ico.png";
    private static final Logger logger = Logger.getLogger(MainApp.class);

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/RootView.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 500);

        TaskManagerController controller = loader.getController();
        controller.setMainApp(this);

        primaryStage.setTitle(titleApp);
        primaryStage.getIcons().add(new Image(icoApp));
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
        logger.info("Primary stage start normally");
    }

    public static void main(String[] args) {
        launch(args);
    }

    public boolean showTaskDetails(Task task, String title) throws IOException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TaskDetailsView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            TaskDetailsController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTask(task, title);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            logger.error(e);
            System.out.println("Primary Stage" + e.getMessage());
            return false;
        }
    }

}

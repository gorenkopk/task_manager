package ua.edu.sumdu.j2se.gorenko.pavel.tasks.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import ua.edu.sumdu.j2se.gorenko.pavel.tasks.MainApp;
import ua.edu.sumdu.j2se.gorenko.pavel.tasks.model.Task;
import ua.edu.sumdu.j2se.gorenko.pavel.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.gorenko.pavel.tasks.view.RootView;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

public class TaskManagerController extends RootView  {

    private static final Logger logger = Logger.getLogger(TaskManagerController.class);

    @FXML
    public void onActionBtnAdd() throws IOException {
        Task tempTask = new Task();
        boolean okClicked = mainApp.showTaskDetails(tempTask, "Добавить новую задачу");
        if (okClicked) {
            taskList.add(tempTask);
            logger.info("Task add successful");
        }
    }

    @FXML
    public void onActionBtnEdit() throws IOException {
        Task tempTask = null;
        boolean okClicked = false;
            if(tableView.getSelectionModel().selectedItemProperty().getValue() != null) {
                tempTask = tableView.getSelectionModel().selectedItemProperty().getValue();
                okClicked = mainApp.showTaskDetails(tempTask, "Изменить(редактировать) задачу");
            } else {
                showAlert("редактировать");
            }
        if (okClicked) {
            refreshTable();
        }
    }

    @FXML
    public void onActionBtnInfo() throws IOException {
        Task tempTask = null;
        boolean okClicked = false;
        if(tableView.getSelectionModel().selectedItemProperty().getValue() != null) {
            tempTask = tableView.getSelectionModel().selectedItemProperty().getValue();
            okClicked = mainApp.showTaskDetails(tempTask, "Просмотр данных по задаче");

        } else {
            showAlert("посмотреть");
        }
    }

    @FXML
    public void onActionBtnSave() {
        try {
            TaskIO.writeBinary(taskList, new File("tasks.bin"));
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @FXML public void onActionBtnDel() {
        if(tableView.getSelectionModel().selectedItemProperty().getValue() != null) {
            taskList.remove(tableView.getSelectionModel().selectedItemProperty().getValue());
        } else {
            showAlert("удалить");
        }
    }

    public void showAlert(String typeMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Selection");
        alert.setHeaderText("Не выбрана задача");
        alert.setContentText("Пожалуйста, выберите из списка задачу, которую хотите " + typeMessage + ".");
        logger.warn("Some alerts: " + typeMessage);
        alert.showAndWait();
    }
}

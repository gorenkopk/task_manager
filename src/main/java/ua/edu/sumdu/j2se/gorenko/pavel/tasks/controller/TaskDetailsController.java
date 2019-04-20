package ua.edu.sumdu.j2se.gorenko.pavel.tasks.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.gorenko.pavel.tasks.model.Task;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.apache.log4j.Logger;

public class TaskDetailsController  {

    public static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private static final Logger logger = Logger.getLogger(TaskDetailsController.class);

    private Stage dialogStage;
    private boolean okClicked = false;
    private Task task;


    @FXML private TextField titleField;
    @FXML private JFXDatePicker editDateTimeField;
    @FXML private JFXTimePicker editTimeTimeField;
    @FXML private JFXDatePicker editDateStartField;
    @FXML private JFXTimePicker editTimeStartField;
    @FXML private JFXDatePicker editDateEndField;
    @FXML private JFXTimePicker editTimeEndField;
    @FXML private TextField editIntervalField;
    @FXML private Button BtnOK;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setTask(Task task, String message) {
        this.task = task;
        if (message.equals("Просмотр данных по задаче")) {
            BtnOK.setDisable(true);
        }
        titleField.setText(task.getTitle());
        if(task.getTime() != null) {
            editDateTimeField.setValue(task.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            editTimeTimeField.setValue(task.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
        }
        if(task.getStartTime() != null) {
            editDateStartField.setValue(task.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            editTimeStartField.setValue(task.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
        }
        if(task.getEndTime() != null) {
            editDateEndField.setValue(task.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            editTimeEndField.setValue(task.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
        }

        editIntervalField.setText(Integer.toString(task.getRepeatInterval()));
        logger.info("Show: " + message);
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        switch(isInputValid()) {
            case 2:
                task.setTitle(titleField.getText());
                Date times = Date.from(editTimeTimeField.getValue().atDate(editDateTimeField.getValue()).atZone(ZoneId.systemDefault()).toInstant());
                task.setTime(times);
                okClicked = true;
                break;
            case 14:
            case 15:
                task.setTitle(titleField.getText());
                Date start = Date.from(editTimeStartField.getValue().atDate(editDateStartField.getValue()).atZone(ZoneId.systemDefault()).toInstant());
                Date end = Date.from(editTimeEndField.getValue().atDate(editDateEndField.getValue()).atZone(ZoneId.systemDefault()).toInstant());
                int interval = Integer.parseInt(editIntervalField.getText());
                task.setTime(start, end, interval);
                okClicked = true;
                break;
            default:
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No right input");
                alert.setHeaderText("Введенные данные не корректны");
                alert.setContentText("Пожалуйста, проверьте введенные данные и нажмите \"Записать\" еще раз.");
                alert.showAndWait();
                logger.warn("Not valid input");
                okClicked = false;
        }
        logger.debug("okClicked - " + okClicked);
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public int isInputValid() {
        int rightInput = 0;
        rightInput += ((titleField.getText() == null || titleField.getText().equals("")) ? 0 : 1);
        rightInput += ((editTimeTimeField.getValue() == null || editDateTimeField.getValue() == null) ? 0 : 1);
        rightInput += ((editDateStartField.getValue() == null || editTimeStartField.getValue() == null) ? 0 : 3);
        rightInput += ((editDateEndField.getValue() == null || editTimeEndField.getValue() == null) ? 0 : 3);
        try {
             if (Integer.parseInt(editIntervalField.getText()) > 0)
                 rightInput += 7;
                    } catch (NumberFormatException e) {
                        logger.error(e);
                    }
         logger.debug("isInputValid - " + rightInput);
        return rightInput;
    }
}

package ua.edu.sumdu.j2se.gorenko.pavel.tasks.view;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ua.edu.sumdu.j2se.gorenko.pavel.tasks.MainApp;
import ua.edu.sumdu.j2se.gorenko.pavel.tasks.model.*;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class RootView implements Observer{

    public static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    @FXML public TableView<Task> tableView;
    @FXML public TableColumn<Task, String> firstNameColumn;
    @FXML public TableColumn<Task, Boolean> activeColumn;
    @FXML public TableColumn<Task, String> timeColumn;
    @FXML public TableColumn<Task, String> endColumn;
    @FXML public TableColumn<Task, String> intervalColumn;
    @FXML public TableColumn<Task, Boolean> repatedColumn;

    public MainApp mainApp;

    public ArrayTaskList taskList = new ArrayTaskList();
    public ObservableList listView = FXCollections.observableArrayList();

    public RootView()  {
        taskList.registerObserver(this);
        try {
            TaskIO.readBinary(taskList, new File("tasks.bin"));
        } catch (IOException e) {
            System.out.println(e);
        }
        refreshTable();
    }

    @FXML private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        activeColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isActive()));
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(dateFormat.format(cellData.getValue().getTime())));
        endColumn.setCellValueFactory(cellData -> new SimpleStringProperty(dateFormat.format(cellData.getValue().getEndTime())));
        intervalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getRepeatInterval())));
        repatedColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isRepeated()));
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        tableView.setItems(listView);
    }

    public void refreshTable() {
        listView.remove(0, listView.size());
        for (Task t: taskList) {
            listView.add(t);
        }
    }

    @Override
    public void notification(String message) {
        if(message.equals("refresh")) {
            refreshTable();
        }
    }
}

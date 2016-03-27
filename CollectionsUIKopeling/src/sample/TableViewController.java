package sample;

import com.sun.javafx.font.freetype.HBGlyphLayout;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.text.TableView;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class TableViewController {

    private final TextField txtName;
    private final TextField txtLastName;
    private final Button addButton;
    private final Button deleteButton;

    javafx.scene.control.TableView<Person> employees = new javafx.scene.control.TableView<>();


    private final ObservableList<Person> data =
            FXCollections.observableArrayList(
                    new Person("Jacob", "Smith"),
                    new Person("Isabella", "Johnson"),
                    new Person("Ethan", "Williams"),
                    new Person("Emma", "Jones"),
                    new Person("Michael", "Brown")
            );


    public TableViewController() {
        Stage primaryStage = new Stage();
        //COLLUMNS
        TableColumn firstNameCol = new TableColumn("Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("name"));

        TableColumn lastname = new TableColumn("lastName");
        lastname.setMinWidth(200);
        lastname.setCellValueFactory(
                new PropertyValueFactory<Person, String>("lastName"));

        employees.setItems(data);
        employees.getColumns().add(firstNameCol);
        employees.getColumns().add(lastname);

        //HBOX
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);

        txtName = new TextField();
        txtName.setPromptText("Name");
        txtName.setMinWidth(100);

        txtLastName = new TextField();
        txtLastName.setPromptText("Last Name");
        txtLastName.setMinWidth(100);

        addButton = new Button("Add");
        addButton.setOnAction(e->addButtonClicked());

        deleteButton = new Button("Delete");
        deleteButton.setOnAction(e->deleteButtonClicked());

        hBox.getChildren().addAll(txtName,txtLastName, addButton,deleteButton);


        VBox vBox = new VBox();
        primaryStage.setTitle("Employee Data");
        vBox.getChildren().addAll(employees, hBox);
        primaryStage.setScene(new Scene(vBox));
        primaryStage.show();
    }

    private void addButtonClicked() {
        Person p = new Person(txtName.getText(),txtLastName.getText());
        data.add(p);
    }

    private void deleteButtonClicked() {
        ObservableList<Person> selected, all;
        all = employees.getItems();
        selected = employees.getSelectionModel().getSelectedItems();

        selected.forEach(all::remove);
    }
}

package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * Created by wouter on 26-3-2016.
 */
public class TreeViewController {


    private final TextField txtName;
    private final TextField txtLastName;
    private final Button addButton;
    private final Button deleteButton;

    javafx.scene.control.TreeView<String> employees = new javafx.scene.control.TreeView<>();


    public TreeViewController() {

        Stage primaryStage = new Stage();

        TreeItem<String> rootItem = new TreeItem<String>("Employees");
        rootItem.setExpanded(true);

        List<Group> data = getDemoData();

        for(Group g : data) {
            TreeItem<String> itemGroup = new TreeItem<String>(g.getName());
            itemGroup.setExpanded(true);
            rootItem.getChildren().add(itemGroup);

            for (Person p : g.getEmployees()) {
                TreeItem<String> itemPerson = new TreeItem<String>(p.getName() + p.getLastName());
                itemPerson.setExpanded(true);
                itemGroup.getChildren().add(itemPerson);
            }

        }
        employees = new TreeView<String>(rootItem);

        StackPane root = new StackPane();
        root.getChildren().add(employees);


        //HBOX
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);

        txtName = new TextField();
        txtName.setPromptText("Name");
        txtName.setMinWidth(100);

        txtLastName = new TextField();
        txtLastName.setPromptText("Last Name");
        txtLastName.setMinWidth(100);

        addButton = new Button("Add");
        addButton.setOnAction(e -> addButtonClicked());

        deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonClicked());

        hBox.getChildren().addAll(txtName, txtLastName, addButton, deleteButton);


        VBox vBox = new VBox();
        primaryStage.setTitle("Employee Data treeview");
        vBox.getChildren().addAll(employees, hBox, root);
        primaryStage.setScene(new Scene(vBox));
        primaryStage.show();

    }

    private void addButtonClicked() {
        TreeItem c = (TreeItem) employees.getSelectionModel().getSelectedItem();
        c.getChildren().add(new TreeItem<>(txtName.getText()));
        c.setExpanded(true);
    }

    private void deleteButtonClicked() {
        TreeItem c = (TreeItem) employees.getSelectionModel().getSelectedItem();
        boolean remove = c.getParent().getChildren().remove(c);


    }

    public ObservableList<Group> getDemoData() {
        ObservableList<Group> data = FXCollections.observableArrayList();

        Group g1 = new Group("marketing");
        data.add(g1);
        Group g2 = new Group("sales");
        data.add(g2);


        g1.addEmployee(new Person("Jacob", "Smith"));

        g1.addEmployee(new Person("Isabella", "Johnson"));
        g1.addEmployee(new Person("Ethan", "Williams"));
        g2.addEmployee(new Person("Emma", "Jones"));
        g2.addEmployee(new Person("Michael", "Brown"));


        return data;
    }
}

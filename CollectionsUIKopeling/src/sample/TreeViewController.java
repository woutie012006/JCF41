package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    private final Node rootIcon = new ImageView(
//            new Image(getClass().getResourceAsStream("sample/rootitem.png"))
            new Image("sample/rootitem.png",20,20,true,true)
    );

    public TreeViewController() {

        Stage primaryStage = new Stage();

        TreeItem<String> rootItem = new TreeItem<String>("Employees", rootIcon);
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
        employees.setEditable(true);
        employees.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
            @Override
            public TreeCell<String> call(TreeView<String> p) {
                return new TextFieldTreeCellImpl();
            }
        });


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

final class TextFieldTreeCellImpl extends TreeCell<String> {

    private TextField textField;

    public TextFieldTreeCellImpl() {
    }

    @Override
    public void startEdit() {
        super.startEdit();

        if (textField == null) {
            createTextField();
        }
        setText(null);
        setGraphic(textField);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText((String) getItem());
        setGraphic(getTreeItem().getGraphic());
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(getTreeItem().getGraphic());
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    commitEdit(textField.getText());
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}
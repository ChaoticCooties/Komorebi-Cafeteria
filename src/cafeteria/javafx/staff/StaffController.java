package cafeteria.javafx.staff;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static cafeteria.data.RecordMethods.isDuplicate;
import static cafeteria.data.RecordMethods.search;
import static cafeteria.data.RecordParser.*;
import static cafeteria.util.util.*;

public class StaffController implements Initializable {

    @FXML
    private JFXTextField dishNameHolder;
    @FXML
    private JFXTextField dishPriceHolder;
    @FXML
    private JFXTextField dishDetailHolder;
    @FXML
    private JFXListView menuView;
    @FXML
    private JFXTextField editDishField;
    @FXML
    private JFXTextField editPriceField;
    @FXML
    private JFXTextField editDetailsField;
    @FXML
    private JFXTextField addUsernameHolder;
    @FXML
    private JFXTextField addNameHolder;
    @FXML
    private JFXPasswordField addPasswordHolder;
    @FXML
    private JFXPasswordField addConfirmPasswordHolder;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initListView();
    }

    @FXML
    private void handleAddItemAction(ActionEvent event) {
        String name = dishNameHolder.getText();
        String price = dishPriceHolder.getText();
        String desc = dishDetailHolder.getText();
        String[] insert = new String[]{name, price, desc};
        Pattern regex = Pattern.compile("^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1,2})?\\s*$");

        if (!(name.isEmpty() || desc.isEmpty())) {
            if (regex.matcher(price).matches()) {
                if (!isDuplicate(name, 0, MENU_DATA_LOC)) {
                    try {
                        insertRecord(insert, MENU_DATA_LOC);
                        clearAddField();
                        initListView();
                        sendAlert("New Entry", "Added new menu item.");
                    } catch (Exception ex) {
                        Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    sendAlert("Duplicate Entry", "Duplicate entry detected, please check your entry");
                }
            } else {
                sendAlert("Error", "Please input a valid price.");
            }
        } else {
            sendAlert("Error", "Please input a valid name/details field.");
        }
    }

    void clearAddField() {
        dishPriceHolder.setText("");
        dishNameHolder.setText("");
        dishDetailHolder.setText("");
    }

    @FXML
    private void handleUpdateMenuEvent(ActionEvent event) {
        String dish = editDishField.getText();
        String price = editPriceField.getText();
        String detail = editDetailsField.getText();

        try {
            String print = dish + "," + price + "," + detail;
            editRecord(0, dish, print, MENU_DATA_LOC);
            initListView();
            clearEditField();
            sendAlert("Success", "Fields were edited.");
        } catch (Exception ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleDeleteMenuEvent(ActionEvent event) {
        try {
            String name = editDishField.getText();
            deleteRecord(name, 0, MENU_DATA_LOC);
            clearEditField();
            initListView();
            sendAlert("Success", "Deleted the selected entry");
        } catch (Exception ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void clearEditField() {
        editDetailsField.setText("");
        editDishField.setText("");
        editPriceField.setText("");
    }

    @FXML
    private void handleCreateCustomerEvent(ActionEvent event) {
        String username = addUsernameHolder.getText();
        String name = addNameHolder.getText();
        String password = addPasswordHolder.getText();
        String cfmpass = addConfirmPasswordHolder.getText();

        if (password.equals(cfmpass)) {
            try {
                //won't create user class so it doesn't "login"
                String[] credentials = {username, name, password, "customer", currentTimeString};
                insertRecord(credentials, USER_DATA_LOC);
                clearCreateField();
                sendAlert("Success", "Created new customer account.");
            } catch (Exception ex) {
                Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void clearCreateField() {
        addUsernameHolder.setText("");
        addNameHolder.setText("");
        addPasswordHolder.setText("");
        addConfirmPasswordHolder.setText("");
    }

    private void initListView() {
        List<String> menuList = listRecord("", 0, MENU_DATA_LOC);
        ObservableList<String> menuOList = (FXCollections.observableArrayList(menuList));
        menuView.setItems(menuOList);
        menuView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                        editDishField.setText(new_val);
                        editPriceField.setText(search(new_val, 0, 1, MENU_DATA_LOC));
                        editDetailsField.setText(search(new_val, 0, 2, MENU_DATA_LOC));
                    }
                });

    }
}

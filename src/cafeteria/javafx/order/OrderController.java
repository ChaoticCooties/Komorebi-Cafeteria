package cafeteria.javafx.order;

import cafeteria.data.User;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static cafeteria.data.Receipt.createRows;
import static cafeteria.data.RecordMethods.search;
import static cafeteria.data.RecordParser.insertRecord;
import static cafeteria.data.RecordParser.listRecord;
import static cafeteria.util.util.*;

public class OrderController implements Initializable {

    public int totalPrice;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initListView();
        disableEdit();
        printField.setVisible(false);
    }

    @FXML
    private JFXListView orderView;
    @FXML
    private JFXTextArea orderField;
    @FXML
    private JFXTextArea printField;
    @FXML
    private JFXTextField cartTotal;
    @FXML
    private JFXTextField cartPrice;
    @FXML
    private JFXTextField cartDetail;

    @FXML
    private void handleResetCartEvent(ActionEvent event) {
        clearFields();
    }

    void clearFields() {
        cartDetail.clear();
        cartPrice.clear();
        cartTotal.clear();
        orderField.clear();
    }

    @FXML
    private void handleConfirmOrderEvent(ActionEvent event) {
        //add to order.txt

        String customerusername = User.getUsername();
        String[] item = orderField.getText().split("\n");
        ArrayList<String> list = new ArrayList<>();

        for (String dish : item) {
            insertRecord(dish + "," + customerusername + "," + currentTimeString, ORDER_DATA_LOC);
            list.add(dish);
        }

        sendReceiptAlert(list);
    }

    private void sendReceiptAlert(ArrayList<String> list) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Order is confirmed.");
        alert.setContentText("Would you like a receipt?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //skimmed through, sorry for this monstrosity
            printField.appendText("-------------------------------------\n");
            printField.appendText("          Komorebi Cafeteria          \n");
            printField.appendText("-------------------------------------\n");
            printField.appendText("-------------------------------------\n");
            printField.appendText("    Food        Amount      Price    \n");
            printField.appendText("-------------------------------------\n");
            printField.appendText(createRows(list));
            printField.appendText("-------------------------------------\n");
            printField.appendText(" Total amount: " + cartTotal.getText() + "               \n");
            printField.appendText("-------------------------------------\n");
            print();
        }
    }

    @FXML
    private void print() {
        TextFlow printArea = new TextFlow(new Text(printField.getText()));

        PrinterJob printerJob = javafx.print.PrinterJob.createPrinterJob();

        if (printerJob != null && printerJob.showPrintDialog(printField.getScene().getWindow())) {
            PageLayout pageLayout = printerJob.getJobSettings().getPageLayout();
            printArea.setMaxWidth(pageLayout.getPrintableWidth());

            if (printerJob.printPage(printArea)) {
                printerJob.endJob();
                // done printing
            } else {
                System.out.println("Failed to print");
            }
        } else {
            System.out.println("Canceled");
        }
    }

    @FXML
    private void handleAddItemEvent(ActionEvent event) {
        String orderItem = orderView.getSelectionModel().getSelectedItem().toString();
        orderField.appendText(orderItem + "\n");
        String searchResult = search(orderItem, 0, MENU_DATA_LOC);
        String[] item = searchResult.split(",");
        totalPrice += Integer.valueOf(item[1]);
        cartTotal.setText("" + totalPrice);
    }

    private void initListView() {
        List<String> menuList = listRecord("", 0, MENU_DATA_LOC);
        ObservableList<String> menuOList = (FXCollections.observableArrayList(menuList));
        orderView.setItems(menuOList);

        orderView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                        String searchResult = search(new_val, 0, MENU_DATA_LOC);
                        String[] menuInfo = searchResult.split(",");
                        cartPrice.setText(menuInfo[1]);
                        cartDetail.setText(menuInfo[2]);
                    }
                });
    }

    void disableEdit() {
        orderField.setEditable(false);
        cartPrice.setEditable(false);
        cartDetail.setEditable(false);
        cartTotal.setEditable(false);
    }
}



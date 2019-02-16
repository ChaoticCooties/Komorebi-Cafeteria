package cafeteria.javafx.register;

import cafeteria.data.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import static cafeteria.util.util.sendAlert;

public class RegisterController {

    @FXML
    private JFXTextField regUsername;
    @FXML
    private JFXTextField regName;
    @FXML
    private JFXPasswordField regPassword;
    @FXML
    private JFXPasswordField regCfmPassword;

    @FXML
    private void handleCreateUserAction(ActionEvent event) {
        String username = regUsername.getText().toLowerCase();
        String name = regName.getText();
        String password = regPassword.getText();
        String cfmpassword = regCfmPassword.getText();

        if (password.equals(cfmpassword)) {
            User customer = new User(username, name, password);
            customer.register();
            regUsername.setText("");
            regName.setText("");
            regPassword.setText("");
            regCfmPassword.setText("");
            sendAlert("Success", "Successfully Registered");
        } else {
            sendAlert("Error", "Password does not match.");
        }
    }
}

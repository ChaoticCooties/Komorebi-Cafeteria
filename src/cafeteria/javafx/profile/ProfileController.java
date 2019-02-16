package cafeteria.javafx.profile;

import cafeteria.data.User;
import cafeteria.javafx.login.LoginController;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import static cafeteria.data.RecordParser.editRecord;
import static cafeteria.util.util.USER_DATA_LOC;
import static cafeteria.util.util.sendAlert;

public class ProfileController extends LoginController {

    @FXML
    private JFXTextField newName;
    @FXML
    private JFXPasswordField oldPassword;
    @FXML
    private JFXPasswordField newPassword;
    @FXML
    private JFXPasswordField confirmPassword;


    @FXML
    private void handleChangeNameEvent(ActionEvent event) {
        String newnm = newName.getText();
        String oldUsername = User.getUsername();

        User.setName(newnm);
        editRecord(0, oldUsername, 1, newnm, USER_DATA_LOC);
        sendAlert("Success", "Successfully changed Name.");
    }

    @FXML
    private void handleChangePasswordEvent(ActionEvent event) {
        String oldPass = User.getPassword();
        String oldUsername = User.getUsername();
        String oldpw = oldPassword.getText();
        String newpw = newPassword.getText();
        String cfmpw = confirmPassword.getText();

        if (oldPass.equals(oldpw) && newpw.equals(cfmpw)) {
            try {
                editRecord(0, oldUsername, 2, newpw, USER_DATA_LOC);
                User.setPassword(newpw);
                clearPasswordField();
                sendAlert("Success", "Successfully changed Password.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            sendAlert("Error", "Invalid entry, please try again.");
            clearPasswordField();
        }
    }

    void clearPasswordField() {
        oldPassword.setText("");
        newPassword.setText("");
        confirmPassword.setText("");
    }
}

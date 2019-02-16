package cafeteria.javafx.main.sidebar;

import cafeteria.data.User;
import cafeteria.util.util;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SidebarController implements Initializable {

    @FXML
    private JFXButton staffPanel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String role = User.getRole();
        if (!role.equals("staff")) {
            staffPanel.setVisible(false);
        }
    }

    @FXML
    private void loadEditProfile(ActionEvent event) {
        util.loadWindow(getClass().getResource("/cafeteria/javafx/profile/profile.fxml"), "Edit Profile", null);
    }

    @FXML
    private void loadOrder(ActionEvent event) {
        util.loadWindow(getClass().getResource("/cafeteria/javafx/order/order.fxml"), "Order", null);
    }

    @FXML
    private void loadStaffPanel(ActionEvent event) {
        util.loadWindow(getClass().getResource("/cafeteria/javafx/staff/staff.fxml"), "Staff Panel", null);
    }

    @FXML
    private void loadSettings(ActionEvent event) {
        util.loadWindow(getClass().getResource("/cafeteria/javafx/settings/settings.fxml"), "Settings", null);
    }
}

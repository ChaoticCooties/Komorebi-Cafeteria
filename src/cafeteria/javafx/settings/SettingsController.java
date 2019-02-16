package cafeteria.javafx.settings;


import cafeteria.util.Preferences;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private JFXComboBox themeHolder;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComboBox();
    }

    @FXML
    private void handleApplyThemeEvent(ActionEvent event) {

        String theme = themeHolder.getValue().toString();
        Preferences preferences = new Preferences();
        preferences.setTheme(theme);
        Preferences.writePreferenceToFile(preferences);
    }

    private void initComboBox() {
        Preferences preferences = Preferences.getPreferences();
        themeHolder.setPromptText(preferences.getTheme());
        themeHolder.getItems().addAll(
                "/resources/light-theme.css",
                "/resources/dark-theme.css"
        );
    }
}

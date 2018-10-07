package sample;

import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private TextField loginSignUpField;

    @FXML
    private TextField nameSignUpField;

    @FXML
    private PasswordField passwordSignUpField;

    @FXML
    private TextField secondnameSignUpField;


    @FXML
    void initialize() {


        loginSignUpButton.setOnAction(event -> { //собственно добавление по нажатию
            try {
                new Thread(new Client()).start();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        });


    }


}

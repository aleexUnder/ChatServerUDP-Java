package sample;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authSignInButton;

    @FXML
    private TextField loginField;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private PasswordField passwordField;
    @FXML
    private Text notrightLogin;

    @FXML
    private Text notrightPassword;


    public Client alone;



    @FXML
    void initialize() {
        authSignInButton.setOnAction(event ->{//проверка на пустые поля
            String loginText=loginField.getText().trim();
            String loginPassword=passwordField.getText().trim();

            if(!loginText.equals("")&&!loginPassword.equals("")){ //поправить это говно, там должны быть contains
                loginUser(loginText,loginPassword);
                notrightLogin.setText("");
                notrightPassword.setText("");
            } if(loginText.equals("")){
                notrightLogin.setText("Логин должен быть введен!");
            }else notrightLogin.setText("");
            if(loginPassword.equals("")){
                notrightPassword.setText("Пароль должен быть введен!");
            }else notrightPassword.setText("");
        } );

        loginSignUpButton.setOnAction(event -> {//кнопка регистрации
            loadFXML("signUp.fxml");

        });


    }

    private void loginUser(String loginText, String loginPassword) {
        try {
            alone=new Client(loginText,loginPassword);
            alone.SendAuth();
            if(alone.Reciev("success~"))loadFXML("Chat.fxml");
            else System.out.println("нет такого пользователя");
           // new Thread(alone).start();
        } catch (UnknownHostException|SocketException e) {
            e.printStackTrace();
        }
    }
    private void loadFXML(String nameFXML){
        loginSignUpButton.getScene().getWindow().hide(); //прячем окно входа
        //запускаем нужную сцену
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource(nameFXML));//файл для загрузки
        try {
            loader.load();//загружаем сцену
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root=loader.getRoot();
        Stage stage=new Stage();
        stage.setTitle("Чат");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

}

package sample;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sun.security.x509.IPAddressName;


import static java.awt.Color.RED;
import static javafx.print.PrintColor.COLOR;


public class ChatController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addFriendButton;

    @FXML
    private TextField addFriendNickName;

    @FXML
    private TextField addFriendPort;

    @FXML
    private TextField addFriendiPaddress;

    @FXML
    private Text addWarningText;

    @FXML
    private AnchorPane anchorPaneFriendsList;

    @FXML
    private AnchorPane chatAnchorPane;

    @FXML
    private AnchorPane leftAnchorPane;

    @FXML
    private AnchorPane mainAnchorPain;

    @FXML
    private TextField myInformation_IPAddress;

    @FXML
    private TextField myInformation_Port;

    @FXML
    private TextField myInformation_nickname;

    @FXML
    private Button sendFile;

    @FXML
    private Button sendMessage;

    @FXML
    private SplitPane splitPaneHorizontal;
    @FXML
    private AnchorPane anchorPaneScrollPaneFriendList;
    @FXML
    private ScrollPane scrollFriendListPane;

    @FXML
    private TextArea textMessage;

    @FXML
    private Button turnOff;


   public ArrayList<Friend> friends=new ArrayList<Friend>();
   public ArrayList<TextArea> list_friends=new ArrayList<TextArea>();
   public double x_lastFriend=10;
   public double y_lastFriend=15;
   public double width_rect=150;
   public double height_rect=55;
   public String ip;
   public int port;
   public String nickname;

   public int x_left=10,x_right=210;
   public int rows;
   public Box box_text;

    @FXML
    void initialize() {

        AnchorPane anch=new AnchorPane();
        anch.setPrefHeight(anchorPaneFriendsList.getPrefHeight()-10);
        anch.setPrefWidth(anchorPaneFriendsList.getPrefWidth()-20);
        ScrollPane scr=new ScrollPane(anch);
        scr.setPrefHeight(anchorPaneFriendsList.getPrefHeight());
        scr.setPrefWidth(anchorPaneFriendsList.getPrefWidth());
        System.out.println(scr.getPrefHeight());
        anchorPaneFriendsList.getChildren().add(scr);

        String textIp="";                                 //заполняем мою информацию
        textIp+=Client.getInetAddress();
        myInformation_IPAddress.setText(textIp);

        String textPort="";
        textPort+=Client.getPort();
        myInformation_Port.setText(textPort);

        String textNick="";
        textNick+=Client.getNickname();
        myInformation_nickname.setText(textNick);

  sendMessage.setOnAction(event -> {
      if(!textMessage.getText().equals("")){
          String s=textMessage.getText();

          String test="test~"+s.getBytes().length+"~";
          Client.Send(test);
          for(Friend f:friends){
              if(nickname.equals(f.name)){

              AnchorPane box=Box.messageBox(s);
                    box.setLayoutX(x_right);
                    box.setLayoutY(f.y);

                    f.y+=10+box.getPrefHeight();
                  f.anchorPane.getChildren().add(box);
                  textMessage.setText("");



              }
          }
      }
  });

  addFriendButton.setOnAction(event -> {
      if(!addFriendNickName.getText().equals("")&& !addFriendiPaddress.getText().equals("") && !addFriendPort.getText().equals("")){
          String friend=addFriendNickName.getText()+"~"+addFriendiPaddress.getText()+"~"+addFriendPort.getText()+"~";
          String add="ADD~"+friend;
          Client.Send(add);
          if(Client.Reciev("successADD~")){
              addFriendNickName.setText("");
              addFriendiPaddress.setText("");
              addFriendPort.setText("");
             addFriend(friend);
             AnchorPane box=Box.friendBox(friend);

             box.setLayoutX(x_lastFriend);
             box.setLayoutY(y_lastFriend);
             y_lastFriend+=10+height_rect;
             box.setOnMouseClicked(event1 -> {
                 for(Friend f:friends){
                     if(Box.nickname.equals(f.name)){
                         nickname=f.name;
                            f.anchorPane_inFriendsList=box;
                         if(f.k==0){

                             f.anchorPane.setPrefWidth(chatAnchorPane.getPrefWidth()-13);
                             f.anchorPane.setPrefHeight(chatAnchorPane.getPrefHeight()-15);

                             f.scrollPane=new ScrollPane(f.anchorPane);
                             f.scrollPane.setPrefWidth(chatAnchorPane.getPrefWidth());
                             f.scrollPane.setPrefHeight(chatAnchorPane.getPrefHeight());
                             f.scrollPane.setId(f.name);
                             f.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                             f.k++;
                             chatAnchorPane.getChildren().add(f.scrollPane);
                         }else{
                             chatAnchorPane.getChildren().removeAll(f.scrollPane);
                             chatAnchorPane.getChildren().add(f.scrollPane);
                         }
                     }
                 }
             });
              anch.getChildren().add(box);
          }else addWarningText.setText("Нет такого пользователя");

      }else addWarningText.setText("Заполните все поля!");

        System.out.println("test git");

  });

    }
    public void loadFXML(String fxml){
        //addFriend.getScene().getWindow().hide(); //прячем окно входа
        //запускаем нужную сцену
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));//файл для загрузки
        try {
            loader.load();//загружаем сцену
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root=loader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
    public Friend addFriend(String friend){

        String[] brothers=friend.split("~");
        String friendName=brothers[0];
       int friendPort=Integer.parseInt(brothers[2]);
        String friendIp=brothers[1];
        Friend bro=new Friend(friendName,friendPort,friendIp,0,new AnchorPane());
        friends.add(bro);
        return bro;




    }

}

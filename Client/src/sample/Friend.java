package sample;


import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

public class Friend {
    public String name;
    public int port;
    public String ipaddress;
    public int k;
    public ScrollPane scrollPane;
    public AnchorPane anchorPane;
    public AnchorPane anchorPane_inFriendsList;
    public double y=20;

    public Friend(String name,int port,String ipaddress,int count,AnchorPane anchorPane){
        this.name=name;
        this.port=port;
        this.ipaddress=ipaddress;
        this.k=count;
        this.anchorPane=anchorPane;
    }
}

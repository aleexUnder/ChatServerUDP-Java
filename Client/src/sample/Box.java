package sample;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.text.DateFormat;
import java.util.Date;


public class Box {
    public static String nickname;
    public static String ipaddress;
    public static int port;

    public static AnchorPane friendBox(String s){
        String[] data={"Nick","IPAddress","Port"};
        double width=150;
        double height=55;
        AnchorPane box=new AnchorPane();
       // box.setLayoutX(0);
        //box.setLayoutY(10);
        box.setPrefWidth(width);
        box.setPrefHeight(height);
        Rectangle rectBox=new Rectangle();
        rectBox.setWidth(width);
        rectBox.setHeight(height);
        rectBox.setFill(Color.PEACHPUFF);
        box.getChildren().add(rectBox);
        String[] information=s.split("~");
        nickname=information[0];
        ipaddress=information[1];
        port=Integer.parseInt(information[2]);
        for(int i=0;i<3;i++){
            Text text=new Text(data[i]+": "+information[i]);
            text.setFont(new Font("Yu Gothic UI Semibold",13));
            text.setX(10);
            text.setY((i+1)*15);

            box.getChildren().add(text);
        }

        return box;
    }

    public static AnchorPane messageBox(String text){
        String[] enters=text.split("\\n");
        String result="";
        int size=27;
            for(String s:enters){
                //System.out.println(s);
                int i=0,length=s.length();
                while(length>size){
                    result+=s.substring(i,i+size)+"\n";
                    length-=size;
                    i+=size;
                }result+=s.substring(i,i+length)+"\n";
            }
            String[] textMes=result.split("\n");
            double height=18*(textMes.length+1);
            double width=220;
            double y=10;
            AnchorPane box=new AnchorPane();
            box.setPrefWidth(width);
            box.setPrefHeight(height);

            Rectangle rectBox=new Rectangle();
            rectBox.setWidth(width);
            rectBox.setHeight(height);
            rectBox.setFill(Color.PLUM);
          rectBox.setArcWidth(15);
          rectBox.setArcHeight(15);
            box.getChildren().add(rectBox);
            Text nick=new Text(Client.getNickname());
            nick.setFont(new Font("Yu Gothic UI Semibold",10));
            nick.setX(5);nick.setY(10);
            box.getChildren().add(nick);
            for(String s:textMes){
                Text line=new Text(s);
                y+=16;
                line.setX(5);
                line.setY(y);
                line.setFont(new Font("Yu Gothic UI Semibold",12));
                box.getChildren().add(line);
            }
            Text time=new Text( DateFormat.getTimeInstance(DateFormat.MEDIUM).format(new Date()));
            time.setFont(new Font("Arial",10));
            time.setX(width-44);
            time.setY(height-2);
            box.getChildren().add(time);
            return box;
    }
}

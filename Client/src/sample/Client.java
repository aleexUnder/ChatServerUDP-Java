package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Client implements Runnable{


    private BufferedReader inFromUser;
    private static DatagramSocket clientSocket;
    private static InetAddress IPAddress;
    private static int port;
    private static String login,password;


    private byte[] outData;
    private byte[] inData;


    public Client() throws SocketException, UnknownHostException{
        clientSocket = new DatagramSocket();
        IPAddress = InetAddress.getByName("localhost");
        port=clientSocket.getLocalPort();
        inFromUser = new BufferedReader(new InputStreamReader(System.in));
    }
    public Client(String loginForAuth,String passwordForAuth) throws SocketException, UnknownHostException{
        clientSocket = new DatagramSocket();
        IPAddress = InetAddress.getByName("localhost");
        inFromUser = new BufferedReader(new InputStreamReader(System.in));
        port=clientSocket.getLocalPort();
        login=loginForAuth;
        password=passwordForAuth;


    }
    public static String getInetAddress(){
        return IPAddress.toString();
    }
    public static int getPort(){
        return port;
    }
    public static String getNickname(){
        return login;
    }

    private void shutdown(){
        clientSocket.close();
    }

    public void run() {


        while(true){
            try {
                inData = new byte[1024];
                outData = new byte[1024];

                //System.out.print("> ");
                //String sentence = inFromUser.readLine();
               // outData = sentence.getBytes();

                DatagramPacket in = new DatagramPacket(inData, inData.length);
                clientSocket.receive(in);
                String modifiedSentence = new String(in.getData());
                System.out.println("пришла строчка "+modifiedSentence);

                /*DatagramPacket out = new DatagramPacket(outData, outData.length, IPAddress, 10000);
                clientSocket.send(out);*/
            } catch (IOException e) {

            }
        }
    }
    public void SendAuth()  {
        String sendAuth="AUTH~"+login+"~"+password;

        byte[] outAuth = new byte[sendAuth.getBytes().length];
        outAuth=sendAuth.getBytes();

        DatagramPacket out = new DatagramPacket(outAuth, outAuth.length, IPAddress, 10000);
        try {
            clientSocket.send(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean Reciev(String s)  {



        byte[] in_inf=new byte[s.getBytes().length];
       while(true){
           DatagramPacket in = new DatagramPacket(in_inf, in_inf.length);
           try {//Thread.sleep(2000);
               clientSocket.receive(in);
           } catch (IOException e) {
               e.printStackTrace();
           }
           String modifiedSentence = new String(in.getData());
           //System.out.println(modifiedSentence);
           if(modifiedSentence.contains(s))
           {
               return true;
           }
           else return false;

       }
    }

    public static void Send(String s){
        byte[] test=new byte[s.getBytes().length];

        test=s.getBytes();
        DatagramPacket out = new DatagramPacket(test, test.length, IPAddress, 10000);
        try {
            clientSocket.send(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void SendMessage(String s){

    }


}
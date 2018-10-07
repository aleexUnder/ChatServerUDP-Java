import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServerLab1 implements Runnable{


    private DatagramSocket serverSocket;

    private byte[] in;
    private byte[] out;
    private byte[] out_success;
    private int n=1024;

    /*
     * Our constructor which instantiates our serverSocket
     */
    public ServerLab1() throws SocketException{
        serverSocket = new DatagramSocket(10000);

    }

    public void run() {

        while(true){
            try {
                in = new byte[n];
                out = new byte[n];
                out_success=new byte[11];

                DatagramPacket receivedPacket = new DatagramPacket(in, in.length);

               serverSocket.receive(receivedPacket);
                InetAddress IPAddress = receivedPacket.getAddress();
                int port = receivedPacket.getPort();


                String text = new String(receivedPacket.getData());
                System.out.println(text);
                if(text.contains("test~")){//подготовка массива к получению данных
                    String[] test=text.split("~");

                    if(Integer.valueOf(test[1])<50){n=50;}else {n=Integer.valueOf(test[1]);}

                }
                //out = text.toUpperCase().getBytes();

                System.out.println("String Received: " + text);
                if(text.contains("AUTH~"))
                {
                    String mes="success~";
                    out=mes.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(out, out.length, IPAddress, port);
                    serverSocket.send(sendPacket);
                }else
                if(text.contains("ADD~"))
                {
                    String mes="successADD~";
                    out_success=mes.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(out_success, out_success.length, IPAddress, port);
                    serverSocket.send(sendPacket);
                }






            } catch (IOException e) {

            }

        }
    }
    public static void main(String[] args) throws SocketException {
         new Thread(new ServerLab1()).start();
    }



}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                clientRun();
            }
        };
    }

    public static void clientRun(){
        int port = 8010;
        try{
            InetAddress localhost = InetAddress.getLocalHost();
            Socket socket = new Socket(localhost, port);
            PrintWriter toSocket = new PrintWriter(socket.getOutputStream());
            BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toSocket.println("Hello from the client");
            String line = fromSocket.readLine();
            System.out.println("Response from the socket is :"+line);
            toSocket.close();
            fromSocket.close();
            socket.close();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void spawn100Threads(Client client){
        for(int i = 0; i < 100; i++){
            try{
                Thread thread = new Thread(client.getRunnable());
                thread.start();
            }catch (Exception e){
                return;
            }
        }
    }
    public static void main(String[] args) {
        try{
            spawn100Threads(new Client());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

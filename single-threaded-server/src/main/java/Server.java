import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void run() {
        int port = 8010;
            try {
                ServerSocket serverSocket = new ServerSocket(port); //Open a socket in the port to my address
                serverSocket.setSoTimeout(10000); // 10000 - 10sec
                System.out.println("Server listening on port " + port);
                while (true) {
                    Socket acceptedConnection = serverSocket.accept(); //once client connection accepted
                    System.out.println("Connection established from " + acceptedConnection.getRemoteSocketAddress());
                    PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream());
                    BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream())); //convert in byte form
                    toClient.println("Hello from the single-threaded-server");
                    toClient.close();
                    fromClient.close();
                    acceptedConnection.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
    public static void main(String[] args) {
        try{
            run();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    public static Consumer<Socket> getConsumer() {
        return socket -> {
            try{
                PrintWriter toClient = new PrintWriter(socket.getOutputStream());
                toClient.println("Hello World");
                toClient.close();
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        };
    }

    public static void run() {
        int port = 8010;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("Server listening on port " + port);
            while (true) {
                Socket acceptedConnection = serverSocket.accept(); // once client connection accepted new socket created and given to client
                Thread thread = new Thread(()->getConsumer().accept(acceptedConnection));
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        try{
            run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

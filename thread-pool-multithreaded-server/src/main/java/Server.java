import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ExecutorService threadPool;
    public Server(int poolSize) {
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    public void handleClient(Socket clientSocket) {
        try(PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream())){
            toClient.println("Hello from sever "+clientSocket.getInetAddress());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int poolSize = 100;
        int port = 8010;
        Server server = new Server(poolSize);
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(70000);
            System.out.println("Server started on port "+port);

            while(true){
                Socket clientSocket = serverSocket.accept();
                server.threadPool.execute(() -> server.handleClient(clientSocket));
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            server.threadPool.shutdown();
        }
    }
}

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8085;
    private BufferedReader in;
    private PrintWriter out;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private String username;

    //connects to a chat server
    public ChatClient(String username) {
        this.username = username;
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Send the username to the server
            out.println(username);

            // Start listening for messages from the server
            executor.execute(this::listenForMessages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenForMessages() {
        String message;
        try {
            while ((message = in.readLine()) != null) {
                if (message.startsWith("MESSAGE")) {
                    // Handle incoming messages
                    System.out.println(message.substring(8));
                } else if (message.startsWith("USERLIST")) {
                    // Handle user list update
                    System.out.println("User list: " + message.substring(9));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String recipient, String message) {
        out.println("/msg " + recipient + " " + message);
    }

    public void close() {
        try {
            in.close();
            out.close();
            executor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }
}

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 8083;
    private static HashMap<String, PrintWriter> clientWriters = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Chat server started at PORT: "+ PORT);
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private String username;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Read username from client
                username = in.readLine();
                synchronized (clientWriters) {
                    clientWriters.put(username, out);
                }

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("/msg ")) {
                        String[] tokens = message.split(" ", 3);
                        if (tokens.length == 3) {
                            String recipient = tokens[1];
                            String msg = tokens[2];
                            sendMessageToClient(recipient, username + ": " + msg);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                synchronized (clientWriters) {
                    clientWriters.remove(username);
                }
            }
        }

        private void sendMessageToClient(String recipient, String message) {
            PrintWriter writer;
            synchronized (clientWriters) {
                writer = clientWriters.get(recipient);
            }
            if (writer != null) {
                writer.println(message);
            }
        }
    }
}

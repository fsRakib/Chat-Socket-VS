import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class ChatUI extends JFrame {
    private User loggedInUser;
    private JList<String> userList;
    private DefaultListModel<String> userListModel;
    private JTextPane chatArea; 
    private JTextField messageField;
    private JButton sendButton;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private JLabel loggedInLabel;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/chatapp2";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public ChatUI(User user) {
        this.loggedInUser = user;

         setTitle("Chat Application - developed by RAKIB " );

        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Initialize userListModel for JList
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);

        // Add userList to a JScrollPane
        JScrollPane userListScrollPane = new JScrollPane(userList);
        userListScrollPane.setPreferredSize(new Dimension(200, 0));
        panel.add(userListScrollPane, BorderLayout.WEST);

        // Initialize chatArea and add to JScrollPane
        chatArea = new JTextPane(); 
        chatArea.setEditable(false);
        chatArea.setEditorKit(new HTMLEditorKit());
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        panel.add(chatScrollPane, BorderLayout.CENTER);

        // Message input and send button
        JPanel inputPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        sendButton = new JButton("Send");
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        panel.add(inputPanel, BorderLayout.SOUTH);

        // Logged-in user label
        loggedInLabel = new JLabel("<html><b>Logged in as: " + loggedInUser.getUsername() + "</b></html>");
        loggedInLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loggedInLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        panel.add(loggedInLabel, BorderLayout.NORTH);

        add(panel);

        // Fetch and display registered users
        updateRegisteredUsers();

        // Connect to chat server
        connectToServer();

        // Add action listener to send button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Add action listener to message field for Enter key
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        setVisible(true);
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 8083); 
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send logged in username to the server
            out.println(loggedInUser.getUsername());

            // Start a new thread to listen for incoming messages
            new Thread(() -> {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        String[] parts = message.split(": ", 2);
                        if (parts.length == 2) {
                            appendMessage(parts[0], parts[1], false); //change:2
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser != null) {
                appendMessage("Me", message, true); //change:2

                out.println("/msg " + selectedUser + " " + message);
                messageField.setText("");
                saveMessage(loggedInUser.getUsername(), selectedUser, message);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a user to send the message.");
            }
        }
    }

    //change:2 - updated method to format and append messages
    private void appendMessage(String sender, String message, boolean isSent) {
        String alignment = isSent ? "right" : "left";
        String formattedMessage = String.format(
                "<div style='text-align: %s;'><strong>%s:</strong> %s</div>",
                alignment, sender, message
        );
        try {
            Document doc = chatArea.getDocument();
            HTMLEditorKit kit = (HTMLEditorKit) chatArea.getEditorKit(); //change:2
            kit.insertHTML((HTMLDocument) doc, doc.getLength(), formattedMessage, 0, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveMessage(String sender, String receiver, String message) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "INSERT INTO messages (sender, receiver, content, timestamp) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, sender);
            stmt.setString(2, receiver);
            stmt.setString(3, message);
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close JDBC objects in finally block
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateRegisteredUsers() {
        ArrayList<String> registeredUsers = fetchRegisteredUsersFromDatabase();
        for (String user : registeredUsers) {
            userListModel.addElement(user);
        }
    }

    private ArrayList<String> fetchRegisteredUsersFromDatabase() {
        ArrayList<String> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "SELECT username FROM users WHERE username != ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, loggedInUser.getUsername());
            rs = stmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                users.add(username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close JDBC objects in finally block
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return users;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginUI().setVisible(true);
        });
    }
}

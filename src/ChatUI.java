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
import java.util.HashSet;
import java.util.Set;

public class ChatUI extends JFrame {
    private User loggedInUser;
    private JList<String> userList;
    private DefaultListModel<String> userListModel;
    private JTextPane chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private JButton refreshButton;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private JLabel loggedInLabel;
    private Set<String> activeUsers = new HashSet<>();

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/chatapp2";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public ChatUI(User user) {
        this.loggedInUser = user;

        setTitle("Chat App - Logged in as " + user.getUsername());

        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Initialize userListModel for JList
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);

        // Set custom cell renderer to change font style and add active status
        userList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
                        cellHasFocus);
                String username = (String) value;
                if (activeUsers.contains(username)) {
                    label.setText("<html><span style='color: black;'>" + username
                            + "</span>   <span style='color: green;'>(Online)</span></html>");
                    label.setHorizontalAlignment(SwingConstants.LEFT);
                } else {
                    label.setText("<html><span style='color: black;'>" + username + "</span></html>");
                }
                label.setFont(label.getFont().deriveFont(Font.BOLD, 14f));
                return label;
            }
        });

        // Add userList to a JScrollPane
        JScrollPane userListScrollPane = new JScrollPane(userList);
        userListScrollPane.setPreferredSize(new Dimension(200, 0));

        // Panel for user list and logged-in label
        JPanel userListPanel = new JPanel(new BorderLayout());
        JLabel userListLabel = new JLabel("User List", JLabel.CENTER);
        userListLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        userListPanel.add(userListLabel, BorderLayout.NORTH);
        userListPanel.add(userListScrollPane, BorderLayout.CENTER);

        // Add refresh button below the user list
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRegisteredUsers();
            }
        });
        JPanel refreshButtonPanel = new JPanel(new BorderLayout());
        refreshButtonPanel.add(refreshButton, BorderLayout.SOUTH);
        userListPanel.add(refreshButtonPanel, BorderLayout.SOUTH);

        panel.add(userListPanel, BorderLayout.WEST);

        // Initialize chatArea and add to JScrollPane
        chatArea = new JTextPane();
        chatArea.setEditable(false);
        chatArea.setEditorKit(new HTMLEditorKit());
        JScrollPane chatScrollPane = new JScrollPane(chatArea);

        // Panel for chat area and label
        JPanel chatPanel = new JPanel(new BorderLayout());
        JLabel chatLabel = new JLabel("Chat Rooms", JLabel.CENTER);
        chatLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        chatPanel.add(chatLabel, BorderLayout.NORTH);
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);

        panel.add(chatPanel, BorderLayout.CENTER);

        // Message input and send button
        JPanel inputPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        sendButton = new JButton("Send");

        // Create a new panel for messageField and sendButton
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.add(messageField, BorderLayout.CENTER);
        messagePanel.add(sendButton, BorderLayout.EAST);

        inputPanel.add(messagePanel, BorderLayout.CENTER);

        // Adjust the size of messageField
        messageField.setPreferredSize(new Dimension(400, 30));

        // Logged-in user label
        loggedInLabel = new JLabel("<html><b>Logged user:  <span style='font-size: 14pt;'>"
                + loggedInUser.getUsername() + "</span></b></html>");

        loggedInLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loggedInLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        loggedInLabel.setPreferredSize(new Dimension(200, 30)); // Adjusted width to match user list

        inputPanel.add(loggedInLabel, BorderLayout.WEST);

        panel.add(inputPanel, BorderLayout.SOUTH);

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
            socket = new Socket("localhost", 8085);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send logged in username to the server
            out.println(loggedInUser.getUsername());

            // Start a new thread to listen for incoming messages and active users
            new Thread(() -> {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        if (message.startsWith("/activeusers ")) {
                            updateActiveUsers(message.substring(13));
                        } else {
                            String[] parts = message.split(": ", 2);
                            if (parts.length == 2) {
                                appendMessage(parts[0], parts[1], false);
                            }
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

    private void updateActiveUsers(String activeUserList) {
        activeUsers.clear();
        String[] users = activeUserList.split(",");
        for (String user : users) {
            activeUsers.add(user);
        }
        userList.repaint();
    }

    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser != null) {
                appendMessage("Me", message, true);

                out.println("/msg " + selectedUser + " " + message);
                messageField.setText("");
                saveMessage(loggedInUser.getUsername(), selectedUser, message);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a user to send the message.");
            }
        }
    }

    private void appendMessage(String sender, String message, boolean isSent) {
        String alignment = isSent ? "right" : "left";
        String formattedMessage = String.format(
                "<div style='text-align: %s;  font-size: 18pt;'><strong>%s:</strong> %s</div>",
                alignment, sender, message);
        try {
            Document doc = chatArea.getDocument();
            HTMLEditorKit kit = (HTMLEditorKit) chatArea.getEditorKit();
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
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateRegisteredUsers() {
        userListModel.clear();
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
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
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

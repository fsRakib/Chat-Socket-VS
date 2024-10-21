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

    private static final String DB_URL = "jdbc:mysql://localhost:3306/chatapp2";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Custom colors
    private static final Color DARK_GREEN = new Color(0, 128, 105);
    private static final Color LIGHT_GREEN = new Color(37, 211, 102);
    private static final Color BLUE = new Color(66, 133, 244); // For Load button
    private static final Color WHITE = Color.WHITE;
    private static final Color LIGHT_GRAY = new Color(240, 240, 240); // For selected user background

    public ChatUI(User user) {
        this.loggedInUser = user;

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Chat App - Logged in as " + user.getUsername());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Dark green background panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DARK_GREEN);

        // User list
        JLabel userListHeader = new JLabel("Users");
        userListHeader.setFont(new Font("Arial", Font.BOLD, 16));
        userListHeader.setForeground(DARK_GREEN); // Text color dark green
        userListHeader.setHorizontalAlignment(SwingConstants.CENTER);
        userListHeader.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setFont(new Font("Arial", Font.PLAIN, 14));
        userList.setBackground(WHITE); // White background for user list panel
        userList.setForeground(DARK_GREEN); // Text color dark green

        // Cell renderer for user list with custom styling for selected user and online status
        userList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
                        cellHasFocus);
                String username = (String) value;
                label.setText("<html><span style='color: darkgreen;'>" + username + "</span></html>");

                if (activeUsers.contains(username)) {
                    label.setText("<html><span style='color: darkgreen;'>" + username
                            + "</span> <span style='font-weight: bold; color: green;'>(Online)</span></html>");
                }

                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                label.setFont(new Font("Arial", Font.PLAIN, 14));

                if (isSelected) {
                    label.setBackground(LIGHT_GRAY); // Light gray for selected user background
                } else {
                    label.setBackground(WHITE); // White background for unselected users
                }

                return label;
            }
        });

        JScrollPane userListScrollPane = new JScrollPane(userList);
        userListScrollPane.setPreferredSize(new Dimension(200, 0));

        JPanel userListPanel = new JPanel(new BorderLayout());
        userListPanel.add(userListHeader, BorderLayout.NORTH);
        userListPanel.add(userListScrollPane, BorderLayout.CENTER);

        refreshButton = new JButton("Load");
        refreshButton.setBackground(BLUE); // Blue background for Load button
        refreshButton.setForeground(WHITE);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14)); // Bold font for "Load" text

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

        // Chat area setup
        chatArea = new JTextPane();
        chatArea.setEditable(false);
        chatArea.setEditorKit(new HTMLEditorKit());
        chatArea.setBackground(DARK_GREEN); // Dark green background for chat
        chatArea.setForeground(WHITE); // White text for chat
        JScrollPane chatScrollPane = new JScrollPane(chatArea);

        JPanel chatPanel = new JPanel(new BorderLayout());

        JLabel chatLabel = new JLabel("Chat", JLabel.CENTER);
        chatLabel.setFont(new Font("Arial", Font.BOLD, 16));
        chatLabel.setForeground(DARK_GREEN); // Dark green text for "Chat"
        chatLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        chatPanel.add(chatLabel, BorderLayout.NORTH);
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);

        panel.add(chatPanel, BorderLayout.CENTER);

        // Message input field
        messageField = new JTextField();
        messageField.setPreferredSize(new Dimension(400, 40));
        messageField.setBackground(WHITE); // White background for message input
        messageField.setForeground(DARK_GREEN); // Dark green text color

        sendButton = new JButton("Send");
        sendButton.setBackground(LIGHT_GREEN); // Light green for send button
        sendButton.setForeground(Color.BLACK); // Bold black text
        sendButton.setFont(new Font("Arial", Font.BOLD, 14)); // Bold text for send button

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Logged in label
        loggedInLabel = new JLabel("<html><b>Logged in as: <span style='font-size: 14pt; color: darkgreen;'>"
                + loggedInUser.getUsername() + "</span></b></html>");
        loggedInLabel.setHorizontalAlignment(SwingConstants.LEFT);
        loggedInLabel.setBackground(WHITE); // White background for the label
        loggedInLabel.setOpaque(true); // Make the background color visible
        loggedInLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Set padding for label

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(WHITE); // White background for bottom panel
        bottomPanel.add(loggedInLabel, BorderLayout.WEST);
        bottomPanel.add(inputPanel, BorderLayout.CENTER);

        panel.add(bottomPanel, BorderLayout.SOUTH);
        add(panel);

        updateRegisteredUsers();

        // Connect to chat server
        connectToServer();

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

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

            out.println(loggedInUser.getUsername());

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

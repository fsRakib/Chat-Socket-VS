import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginUI() {
        setTitle("User Login");
        setSize(400, 300); // Increased size for better spacing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel with custom background color
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(60, 63, 65)); // Dark background
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding
        mainPanel.setLayout(new BorderLayout());

        // Title label with additional bottom margin
        JLabel titleLabel = new JLabel("Login to Chat", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0)); // Bottom margin for the title
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 15, 15)); // Added more vertical/horizontal gaps
        formPanel.setOpaque(false); // Transparent background

        // Username label and field with bigger font
        JLabel usernameLabel = new JLabel(" Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Bigger font size
        usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(169, 169, 169), 1));
        usernameField.setBackground(new Color(43, 43, 43));
        usernameField.setForeground(Color.WHITE);
        usernameField.setPreferredSize(new Dimension(200, 30));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(169, 169, 169), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Password label and field with bigger font
        JLabel passwordLabel = new JLabel(" Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Bigger font size
        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(169, 169, 169), 1));
        passwordField.setBackground(new Color(43, 43, 43));
        passwordField.setForeground(Color.WHITE);
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(169, 169, 169), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Add labels and fields to form panel
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel with custom padding and style
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Transparent background

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        styleButton(loginButton);
        styleButton(registerButton);

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Action listeners for buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                User user = authenticateUser(username, password);
                if (user != null) {
                    JOptionPane.showMessageDialog(null, "Login Successful");
                    dispose();
                    new ChatUI(user).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Login Failed");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RegistrationUI().setVisible(true);
            }
        });
    }

    // Helper method to style buttons
    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(120, 35)); // Button size
        button.setBackground(new Color(100, 149, 237)); // Cornflower blue
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Rounded corners for the button
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 102, 204), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
    }

    private User authenticateUser(String username, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Database.getConnection();
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.close(conn, stmt, rs);
        }

        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginUI().setVisible(true);
            }
        });
    }
}

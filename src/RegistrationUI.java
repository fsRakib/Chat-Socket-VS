import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JButton registerButton;

    public RegistrationUI() {
        setTitle("User Registration");
        setSize(400, 350);  // Increased size for better spacing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with custom background and padding
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(60, 63, 65)); // Dark background
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding around the panel
        mainPanel.setLayout(new BorderLayout());

        // Title Label with additional bottom margin
        JLabel titleLabel = new JLabel("Register an Account", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0)); // Bottom margin
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 15));  // Adjusted spacing between elements
        formPanel.setOpaque(false);  // Transparent background

        // Username label and field with bigger font and border radius
        JLabel usernameLabel = new JLabel(" Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));  // Bigger font size
        usernameField = new JTextField();
        styleTextField(usernameField);

        // Password label and field
        JLabel passwordLabel = new JLabel(" Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField = new JPasswordField();
        styleTextField(passwordField);

        // Name label and field
        JLabel nameLabel = new JLabel(" Full Name:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField = new JTextField();
        styleTextField(nameField);

        // Phone label and field
        JLabel phoneLabel = new JLabel(" Phone:");
        phoneLabel.setForeground(Color.WHITE);
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        phoneField = new JTextField();
        styleTextField(phoneField);

        // Email label and field
        JLabel emailLabel = new JLabel(" Email:");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField = new JTextField();
        styleTextField(emailField);

        // Add components to form panel
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel with custom padding and style
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);  // Transparent background

        registerButton = new JButton("Register");
        styleButton(registerButton);
        buttonPanel.add(registerButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Action listener for the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String name = nameField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();

                if (registerUser(username, password, name, phone, email)) {
                    JOptionPane.showMessageDialog(null, "Registration Successful");
                    dispose();
                    new LoginUI().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Registration Failed");
                }
            }
        });
    }

    // Helper method to style text fields
    private void styleTextField(JTextField textField) {
        textField.setBorder(BorderFactory.createLineBorder(new Color(169, 169, 169), 1));
        textField.setBackground(new Color(43, 43, 43));
        textField.setForeground(Color.WHITE);
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(169, 169, 169), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)  // Padding inside text field
        ));
    }

    // Helper method to style buttons
    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(150, 40));  // Button size
        button.setBackground(new Color(100, 149, 237));  // Cornflower blue
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

    private boolean registerUser(String username, String password, String name, String phone, String email) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Database.getConnection();
            String query = "INSERT INTO users (username, password, name, phone, email) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, name);
            stmt.setString(4, phone);
            stmt.setString(5, email);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            Database.close(conn, stmt, null);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RegistrationUI().setVisible(true);
            }
        });
    }
}

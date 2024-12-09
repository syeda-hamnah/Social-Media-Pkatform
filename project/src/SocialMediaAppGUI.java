// Updated imports for UI improvements
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocialMediaAppGUI {

    private static final Map<String, String> userDatabase = new HashMap<>();
    private static final Map<String, List<String>> postsDatabase = new HashMap<>();
    private static final Map<String, List<String>> messagesDatabase = new HashMap<>();
    private static String loggedInUser = null;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Social Media App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        JPanel loginPanel = createLoginPanel(frame, cardPanel, cardLayout);
        JPanel registerPanel = createRegisterPanel(frame, cardPanel, cardLayout);
        JPanel mainPanel = createMainPanel(frame, cardPanel, cardLayout);

        cardPanel.add(loginPanel, "Login");
        cardPanel.add(registerPanel, "Register");
        cardPanel.add(mainPanel, "Main");

        frame.add(cardPanel);
        frame.setVisible(true);
    }

    private static JPanel createLoginPanel(JFrame frame, JPanel cardPanel, CardLayout cardLayout) {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(300, 30));

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(300, 30));

        JButton loginButton = createStyledButton("Login", new Color(0, 123, 255));
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter both username and password.");
                return;
            }

            if (loginUser(username, password)) {
                cardLayout.show(cardPanel, "Main");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.");
            }
        });

        JButton switchToRegisterButton = createFlatButton("Don't have an account? Register");
        switchToRegisterButton.addActionListener(e -> cardLayout.show(cardPanel, "Register"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        loginPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx++;
        loginPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        loginPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx++;
        loginPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);

        gbc.gridy++;
        loginPanel.add(switchToRegisterButton, gbc);

        return loginPanel;
    }
    private static boolean registerUser(String username, String password) {
        if (userDatabase.containsKey(username)) {
            return false;
        }
        userDatabase.put(username, password);
        postsDatabase.put(username, new ArrayList<>());
        messagesDatabase.put(username, new ArrayList<>());
        return true;
    }

    private static boolean loginUser(String username, String password) {
        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            loggedInUser = username;
            return true;
        }
        return false;
    }

    private static void createPost(String content) {
        postsDatabase.get(loggedInUser).add(content);
    }

    private static String viewPosts() {
        List<String> posts = postsDatabase.get(loggedInUser);
        StringBuilder result = new StringBuilder();
        for (String post : posts) {
            result.append("- ").append(post).append("\n");
        }
        return result.toString();
    }

    private static void sendMessage(String recipientUsername, String messageContent) {
        if (userDatabase.containsKey(recipientUsername)) {
            messagesDatabase.get(recipientUsername).add("From " + loggedInUser + ": " + messageContent);
            JOptionPane.showMessageDialog(null, "Message sent!");
        } else {
            JOptionPane.showMessageDialog(null, "Recipient not found!");
        }
    }

    private static String viewMessages() {
        List<String> messages = messagesDatabase.get(loggedInUser);
        StringBuilder result = new StringBuilder();
        for (String message : messages) {
            result.append("- ").append(message).append("\n");
        }
        return result.toString();
    }

    private static JPanel createRegisterPanel(JFrame frame, JPanel cardPanel, CardLayout cardLayout) {
        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(300, 30));

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(300, 30));

        JButton registerButton = createStyledButton("Register", new Color(40, 167, 69));
        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter both username and password.");
                return;
            }

            if (registerUser(username, password)) {
                JOptionPane.showMessageDialog(frame, "Registration successful! Please login.");
                cardLayout.show(cardPanel, "Login");
            } else {
                JOptionPane.showMessageDialog(frame, "Username already exists. Please try a different one.");
            }
        });

        JButton switchToLoginButton = createFlatButton("Already have an account? Login");
        switchToLoginButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        registerPanel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        registerPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx++;
        registerPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        registerPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx++;
        registerPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        registerPanel.add(registerButton, gbc);

        gbc.gridy++;
        registerPanel.add(switchToLoginButton, gbc);

        return registerPanel;
    }

    private static JPanel createMainPanel(JFrame frame, JPanel cardPanel, CardLayout cardLayout) {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel welcomeLabel = new JLabel("Welcome, " + loggedInUser + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton createPostButton = createStyledButton("Create Post", new Color(0, 123, 255));
        JButton viewPostsButton = createStyledButton("View Posts", new Color(0, 123, 255));
        JButton sendMessageButton = createStyledButton("Send Message", new Color(0, 123, 255));
        JButton viewMessagesButton = createStyledButton("View Messages", new Color(0, 123, 255));
        JButton logoutButton = createStyledButton("Logout", new Color(220, 53, 69));

        createPostButton.addActionListener(e -> {
            String content = JOptionPane.showInputDialog(frame, "Enter your post:");
            if (content != null && !content.trim().isEmpty()) {
                createPost(content.trim());
                JOptionPane.showMessageDialog(frame, "Post created successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Post cannot be empty.");
            }
        });

        viewPostsButton.addActionListener(e -> {
            String posts = viewPosts();
            JOptionPane.showMessageDialog(frame, posts.isEmpty() ? "No posts available." : posts);
        });

        sendMessageButton.addActionListener(e -> {
            String recipient = JOptionPane.showInputDialog(frame, "Enter recipient username:");
            String message = JOptionPane.showInputDialog(frame, "Enter your message:");
            if (recipient != null && message != null && !recipient.trim().isEmpty() && !message.trim().isEmpty()) {
                sendMessage(recipient.trim(), message.trim());
            } else {
                JOptionPane.showMessageDialog(frame, "Recipient and message cannot be empty.");
            }
        });

        viewMessagesButton.addActionListener(e -> {
            String messages = viewMessages();
            JOptionPane.showMessageDialog(frame, messages.isEmpty() ? "No messages available." : messages);
        });

        logoutButton.addActionListener(e -> {
            loggedInUser = null;
            cardLayout.show(cardPanel, "Login");
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(welcomeLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        mainPanel.add(createPostButton, gbc);

        gbc.gridy++;
        mainPanel.add(viewPostsButton, gbc);

        gbc.gridy++;
        mainPanel.add(sendMessageButton, gbc);

        gbc.gridy++;
        mainPanel.add(viewMessagesButton, gbc);

        gbc.gridy++;
        mainPanel.add(logoutButton, gbc);

        return mainPanel;
    }


    private static JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 40));
        button.setBorderPainted(false);
        return button;
    }

    private static JButton createFlatButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setForeground(new Color(100, 100, 100));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        return button;
    }

    // Logic remains unchanged (registerUser, loginUser, etc.)
}



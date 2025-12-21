package com.client.gui;
import com.client.dto.DishDto;
import com.client.logic.ServerConnector;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainWindow extends JFrame {
    private ServerConnector server = new ServerConnector();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            while (true) {
                boolean loggedIn = new MainWindow().showLoginDialog();
                if (loggedIn) {
                    new MainWindow().setVisible(true);
                    break;
                }
                else {
                    break;
                }
            }
        });
    }

    public MainWindow() {
        setTitle("Книга Рецептов");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Все рецепты", createPanel("all"));
        tabs.addTab("По Названию", createPanel("name"));
        tabs.addTab("По Ингредиенту", createPanel("ingredient"));
        tabs.addTab("По Категории", createPanel("category"));

        add(tabs);
    }

    private boolean showLoginDialog() {
        JPanel p = new JPanel(new GridLayout(3, 2));
        JTextField u = new JTextField();
        JPasswordField pass = new JPasswordField();
        p.add(new JLabel("Логин:")); p.add(u);
        p.add(new JLabel("Пароль:")); p.add(pass);

        while (true) {
            int res = JOptionPane.showOptionDialog(null, p, "Авторизация",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, new String[]{"Войти", "Регистрация"}, "Войти");

            if (res == JOptionPane.CLOSED_OPTION) return false;

            String login = u.getText().trim();
            String password = new String(pass.getPassword()).trim();

            if (login.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Введите логин и пароль!");
                continue;
            }

            String action = (res == 0) ? "login" : "reg";

            boolean success = server.auth(login, password, action);

            if (success) {
                if (action.equals("reg")) {
                    JOptionPane.showMessageDialog(null, "Регистрация успешна! Добро пожаловать, " + login);
                }
                return true;
            } else {
                if (action.equals("reg")) {
                    JOptionPane.showMessageDialog(null, "Ошибка регистрации!\nВозможно, имя '" + login + "' уже занято.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Неверный логин или пароль!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private JPanel createPanel(String type) {
        JPanel p = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        JTextField tf = new JTextField(20); JButton btn = new JButton(type.equals("all") ? "Обновить" : "Найти");
        if (!type.equals("all")) top.add(tf);
        top.add(btn);

        JTextArea area = new JTextArea(); area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));

        btn.addActionListener(e -> {
            List<DishDto> list = server.search(type, tf.getText());
            area.setText("");
            for (DishDto d : list) {
                area.append("=== " + d.name + " (" + d.category + ") ===\n");
                area.append("Ингредиенты:\n");
                for (String i : d.ingredients) area.append(" - " + i + "\n");
                area.append("Рецепт: " + d.recipe + "\n\n");
            }
        });

        p.add(top, BorderLayout.NORTH);
        p.add(new JScrollPane(area), BorderLayout.CENTER);
        return p;
    }
}
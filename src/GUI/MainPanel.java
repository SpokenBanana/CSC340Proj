package GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainPanel panel = new MainPanel(frame);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JFrame frame;
    private Font screenFont;

    public MainPanel(JFrame frame){
        // boilerplate
        setLayout(null);
        this.frame = frame;
        setPreferredSize(new Dimension(600,700));
        screenFont = new Font("Sans serif", Font.PLAIN, 30);

        // setting up the screen
        JLabel label = label("Welcome", 200, 50, 300, 50);

        add(label);
        add(label("What would you like to do?", 10, 100, 500, 100));
    }

    public JLabel label(String text, int x, int y, int width, int height){
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setFont(screenFont);
        return label;
    }

    public JButton button(String text, int x, int y, int width, int height, ActionListener e) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.addActionListener(e);
        button.setFont(screenFont);
        return button;
    }
}

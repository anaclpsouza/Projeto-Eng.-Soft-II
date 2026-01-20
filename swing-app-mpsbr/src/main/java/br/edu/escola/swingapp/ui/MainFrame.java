package br.edu.escola.swingapp.ui;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Minha App Swing MPS.Br");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Bem-vindo!");
        JButton button = new JButton("Clique aqui");

        panel.add(label);
        panel.add(button);

        add(panel, BorderLayout.CENTER);
    }
}

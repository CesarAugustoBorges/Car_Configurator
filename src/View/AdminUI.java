package  View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminUI extends JPanel{
	public AdminUI(){
		this.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.add("Admin",adminPanel());
		tabbedPane.add("Gestor",managerPanel());
		tabbedPane.add("ConfiguraFácil",new ClientUI());
		tabbedPane.setBounds(0,0,520,640);

		add(tabbedPane);
	}

	private JPanel adminPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(null);

		JSeparator divider = new JSeparator(SwingConstants.VERTICAL);
		divider.setBounds(260,00,520,640);

		JLabel user = new JLabel("Usuário:");
		user.setBounds(35,30,60,15);
		JTextField userTxt = new JTextField();
		userTxt.setBounds(30,50,190,30);
		JButton remove = new JButton("Remover");
		remove.setBounds(120,100,100,30);

		JLabel user2 = new JLabel("Usuário:");
		user2.setBounds(295,30,60,15);
        JLabel password = new JLabel("Senha:");
        password.setBounds(295,100,60,15);
        JTextField userTxt2 = new JTextField();
        userTxt2.setBounds(290,50,190,30);
        JTextField passTxt = new JTextField();
        passTxt.setBounds(290,120,190,30);
        JButton register = new JButton("Registar");
        register.setBounds(380,170,100,30);

		panel.add(divider);
		panel.add(user);
		panel.add(userTxt);
		panel.add(remove);

		panel.add(user2);
		panel.add(password);
		panel.add(userTxt2);
		panel.add(passTxt);
		panel.add(register);

		return panel;
	}

	private JPanel managerPanel(){
		JPanel panel = new JPanel();

		return panel;
	}
}
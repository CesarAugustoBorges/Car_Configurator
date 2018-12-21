package  View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminUI extends JPanel{
	public AdminUI(){
		this.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.add("Admin",adminPanel());
		tabbedPane.add("Gestor",new ManagerUI());
		tabbedPane.add("ConfiguraFácil",new ClientUI());
		tabbedPane.setBounds(0,0,520,640);

		add(tabbedPane);
	}

	private JPanel adminPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(null);

		JSeparator divider = new JSeparator(SwingConstants.VERTICAL);
		divider.setBounds(250,00,520,640);

		JLabel info1 = new JLabel("Remover Fucionário");
        info1.setBounds(30,10,150,15);

		JLabel user = new JLabel("Usuário:");
		user.setBounds(35,40,60,15);

		JTextField userTxt = new JTextField();
		userTxt.setBounds(30,60,190,30);

		JLabel success = new JLabel("Funcionário removido com sucesso");
		success.setBounds(5,160,240,30);
		success.setForeground(Color.green);
		success.setVisible(false);

		JLabel failure = new JLabel("Erro ao remover Funcionário");
		failure.setBounds(5,160,240,30);
		failure.setForeground(Color.red);
		failure.setVisible(false);

		JButton remove = new JButton("Remover");
		remove.setBounds(120,110,100,30);
		remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(false){//removerFuncionario(Integer.parseInt(userTxt.getText()))){
                	success.setVisible(true);
                	failure.setVisible(false);
                }
                else{
                	failure.setVisible(true);
                	success.setVisible(false);
                }
            }
        });

		JLabel info2 = new JLabel("Registar Fucionário");
        info2.setBounds(290,10,150,15);

		JLabel user2 = new JLabel("Usuário:");
		user2.setBounds(295,40,60,15);

        JLabel password = new JLabel("Senha:");
        password.setBounds(295,110,60,15);
        
        JTextField userTxt2 = new JTextField();
        userTxt2.setBounds(290,60,190,30);
        
        JTextField passTxt = new JTextField();
        passTxt.setBounds(290,130,190,30);

        JLabel success2 = new JLabel("Funcionário registado com sucesso");
		success2.setBounds(255,220,240,30);
		success2.setForeground(Color.green);
		success2.setVisible(false);

		JLabel failure2 = new JLabel("Erro ao registar Funcionário");
		failure2.setBounds(255,220,240,30);
		failure2.setForeground(Color.red);
		failure2.setVisible(false);
        
        JButton register = new JButton("Registar");
        register.setBounds(380,180,100,30);
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(false){//adicionarFuncionario(Integer.parseInt(userTxt2.getText()), passTxt.getText())){
                	success2.setVisible(true);
                	failure2.setVisible(false);
                }
                else{
                	failure2.setVisible(true);
                	success2.setVisible(false);
                }
            }
        });

        panel.add(info1);
		panel.add(user);
		panel.add(userTxt);
		panel.add(remove);
		panel.add(success);
		panel.add(failure);

		panel.add(divider);

		panel.add(info2);
		panel.add(user2);
		panel.add(password);
		panel.add(userTxt2);
		panel.add(passTxt);
		panel.add(register);
		panel.add(success2);
		panel.add(failure2);

		return panel;
	}
}
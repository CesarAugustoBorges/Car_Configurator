package  View;

import Business.Sistema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminUI extends JPanel{

	private Sistema sistema;

	public AdminUI(Sistema s) throws Exception{

		this.setLayout(null);
		this.sistema = s;
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.add("Admin",adminPanel());
		tabbedPane.add("Gestor",new ManagerUI(s));
		tabbedPane.add("ConfiguraFácil",new ClientUI(s));
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
				try {
					if (sistema.removerFuncionario(Integer.parseInt(userTxt.getText()))!=false) {
						success.setVisible(true);
						failure.setVisible(false);
					} else {
						failure.setVisible(true);
						success.setVisible(false);
					}
				}catch (Exception b){
					b.printStackTrace();
				}
			}
        });

		JLabel info2 = new JLabel("Registar Fucionário");
        info2.setBounds(290,10,150,15);

		JLabel user2 = new JLabel("Usuário:");
		user2.setBounds(295,40,60,15);

        JLabel password = new JLabel("Senha:");
        password.setBounds(295,110,60,15);

		JLabel tipo = new JLabel("Tipo:");
		tipo.setBounds(295,170,60,15);

		JLabel identifcador = new JLabel("Identificador:");
		identifcador.setBounds(295,240,60,15);

        
        JTextField userTxt2 = new JTextField();
        userTxt2.setBounds(290,60,190,30);
        
        JTextField passTxt = new JTextField();
        passTxt.setBounds(290,130,190,30);

		JTextField TipoTxt = new JTextField();
		TipoTxt.setBounds(290,200,190,30);

		JTextField identificadorTxtx = new JTextField();
		identificadorTxtx.setBounds(290,270,190,30);

        JLabel success2 = new JLabel("Funcionário registado com sucesso");
		success2.setBounds(255,300,240,30);
		success2.setForeground(Color.green);
		success2.setVisible(false);

		JLabel failure2 = new JLabel("Erro ao registar Funcionário");
		failure2.setBounds(255,220,240,30);
		failure2.setForeground(Color.red);
		failure2.setVisible(false);
        
        JButton register = new JButton("Registar");
        register.setBounds(380,300,100,30);
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				try {

					if (sistema.adicionarFuncionario(userTxt2.getText(), Integer.parseInt(identificadorTxtx.getText()) ,passTxt.getText(),TipoTxt.getText(),"123456789")) {
						success2.setVisible(true);
						failure2.setVisible(false);
					} else {
						failure2.setVisible(true);
						success2.setVisible(false);
					}
				} catch (Exception b) {
					b.printStackTrace();
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
		panel.add(tipo);
		panel.add(identifcador);
		panel.add(userTxt2);
		panel.add(passTxt);
		panel.add(identificadorTxtx);
		panel.add(TipoTxt);
		panel.add(register);
		panel.add(success2);
		panel.add(failure2);

		return panel;
	}
}
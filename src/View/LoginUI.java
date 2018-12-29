package View;

import Business.Sistema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginUI{
    private static JFrame notLoginFrame;

	public LoginUI(){
        Sistema sistema = new Sistema();
        JLabel wrongInput = new JLabel("Credenciais erradas!");
        wrongInput.setBounds(50,15,140,15);
        wrongInput.setVisible(false);
        wrongInput.setForeground(Color.red);

		JLabel user = new JLabel("Usuário:");
        user.setBounds(55,40,60,15);

        JLabel password = new JLabel("Senha:");
        password.setBounds(55,100,60,15);

        JTextField userTxt = new JTextField();
        userTxt.setBounds(50,55,220,30);

        JPasswordField passTxt = new JPasswordField();
        passTxt.setBounds(50,115,220,30);

        JButton login = new JButton("Autenticar");
        login.setBounds(180,165,90,30);

        JFrame frame = new JFrame("Autenticação");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(325,235);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.add(wrongInput);
        frame.add(user);
        frame.add(userTxt);
        frame.add(password);
        frame.add(passTxt);
        frame.add(login);


        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                //Exemplo de admin, nome: 4; pass :bolos
                //Exemplo de gestor , nome : 5, pass :vedeta
                //Exemplo de funcionário , nome : 4 , pass : gui
                try {

                    switch (sistema.login(userTxt.getText(), new String(passTxt.getPassword()) )) {

                        case 0:
                            createJFrame(0,new AdminUI( sistema));
                            frame.dispose();
                            break;
                        case 1:
                            createJFrame(1,new ManagerUI(sistema));
                            frame.dispose();
                            break;
                        case 2:
                            createJFrame(2,new ClientUI(sistema));
                            frame.dispose();
                        default:
                            wrongInput.setVisible(true);
                            break;
                    }
                } catch (Exception b) {
                    System.out.println(b);
                }
            }
        });

	}




    private void createJFrame(int i, JPanel panel){
        int w = 0, h = 0;
        String name;

        switch(i){
            case 0:     w = 520;h = 640;name="Admin";
                        break;
            case 1:     w = 500;h = 500;name="Gestor";
                        break;
            case 2:     w = 500;h = 600;name="ConfiguraFácil";
                        break;
            default:    w = 500;h = 600;name="ConfiguraFácil";
                        break;
        }

        notLoginFrame = new JFrame(name);
        notLoginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        notLoginFrame.setSize(w,h);
        notLoginFrame.setResizable(false);
        notLoginFrame.setLocationRelativeTo(null);
        notLoginFrame.setVisible(true);
        notLoginFrame.getContentPane().add(panel);

        if(i==2 || i==0){ClientUI.createMenuBar(notLoginFrame);}
    }

    public static void freeFrame(){
	    notLoginFrame.dispose();
    }
}
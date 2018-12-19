import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginUI{
	public LoginUI(){
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

        JTextField passTxt = new JTextField();
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

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(userTxt.getText().equals("admin")){createJFrame(0, new AdminUI());}
                if(userTxt.getText().equals("gestor")){createJFrame(0, new ManagerUI());}
                if(userTxt.getText().equals("funcionario")){createJFrame(0, new ClientUI());}
                frame.dispose();
                /*         
                switch(login(userTxt.getText(), passTxt.getText())){
                	case 0:     createJFrame(new AdminUI());
                                frame.dispose();
                			    break;
                	case 1:     createJFrame(new ManagerUI());
                                frame.dispose();
                			    break;
                	case 2:     createJFrame(new ClientUI());
                                frame.dispose();
                                break;
                    case -1:    wrongInput.setVisible(true);
                                break;
                	default:    wrongInput.setVisible(true);
                                break;
                }*/
            }
        });

        frame.add(wrongInput);
        frame.add(user);
        frame.add(userTxt);
        frame.add(password);
        frame.add(passTxt);
        frame.add(login);
	}

    private void createJFrame(int i, JPanel panel){
        int w = 0, h = 0;
        String name;

        switch(i){
            case 0:     w = 520;h = 640;name="Admin";
                        break;
            case 1:     w = 0;h = 0;name="Gestor";
                        break;
            case 2:     w = 500;h = 600;name="ConfiguraFácil";
                        break;
            default:    w = 500;h = 600;name="ConfiguraFácil";
                        break;
        }

        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(w,h);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getContentPane().add(panel);

        if(i==2 || i==0){ClientUI.createMenuBar(frame);}
    }
}
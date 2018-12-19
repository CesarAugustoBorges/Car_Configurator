import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/*
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
                if(userTxt.getText().equals("admin")){
                    new ClientUI();
                    frame.dispose();
                }
                else{wrongInput.setVisible(true);}
                /*
                //switch(login(userTxt.getText(), passTxt.getText())){
                //switch(4){
                	case 0:     new AdminUI();
                                frame.dispose();
                			    break;
                	case 1:     new ManagerUI();
                                frame.dispose();
                			    break;
                	case 2:     new ClientUI();
                                frame.dispose();
                                break;
                    case -1:    wrongInput.setVisible(true);
                                break;
                	default:    wrongInput.setVisible(true);
                                break;

            }
        });

        frame.add(wrongInput);
        frame.add(user);
        frame.add(userTxt);
        frame.add(password);
        frame.add(passTxt);
        frame.add(login);
	}	
}
*/

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ClientUI{
    private JFrame frame;

    private JMenuItem newCar;
    private JMenuItem saveCar;
    private JMenuItem openFile;
    private JMenuItem confOtima;
    private JMenuItem order;
    private JMenuItem instructions;
    private JMenuItem about;

    private JTree my;
    private JTree all;
    private JLabel allLabel;
    private JLabel myLabel;
    private JButton partsButton;
    private JButton packsButton;
    private JButton addPartButton;
    private JButton removePartButton;
    private JButton moreButton;
    private JTextField logs;

    public ClientUI(){
        createAllComponents();
        createFrame();    
        createMenuBar();
        createListeners();
        addAll();
    }

    private void createAllComponents(){
        this.partsButton = new JButton("Peças");
        partsButton.setBounds(271,10,60,30);

        this.packsButton = new JButton("Pacotes");
        packsButton.setBounds(335,10,70,30);

        this.addPartButton = new JButton("<-");
        addPartButton.setBounds(233,70,35,35);

        BufferedImage img = null;
        try{
            img = ImageIO.read(new File("./garbage2.png"));

        }
        catch(IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
        }
        Image tmp = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        this.removePartButton = new JButton(new ImageIcon(dimg));
        removePartButton.setBounds(233,110,35,35);

        this.my = new JTree();
        my.setBounds(10,70,220,410);
        my.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        this.all = new JTree();
        all.setBounds(271,70,220,410);
        all.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        this.logs = new JTextField("Logs");
        logs.setEditable(false);
        logs.setBounds(10,490,420,55);

        this.moreButton = new JButton("+");
        moreButton.setFont(new Font("Arial", Font.PLAIN, 25));
        moreButton.setBounds(440,490,55,55);

        this.myLabel = new JLabel("Itens Selecionados:");
        myLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        myLabel.setBounds(15,50,140,15);

        this.allLabel = new JLabel("Todos Itens:");
        allLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        allLabel.setBounds(276,50,90,15);

    }

    private void createListeners(){
    	this.newCar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Stuff here
            }
        });

    	this.saveCar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Stuff here
            }
        });

		this.openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Stuff here
            }
        });

        this.confOtima.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame confFrame = new JFrame("Configuração Ótima");        
		        confFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		        confFrame.setLayout(null);
		        confFrame.setSize(325,175);
		        confFrame.setResizable(false);
		        confFrame.setLocationRelativeTo(null);
		        confFrame.setVisible(true);

		        JLabel label = new JLabel("Orçamento:");
		        JLabel error = new JLabel("Valor errado!");
		        JTextField txtBox = new JTextField();
		        JButton done = new JButton("Feito");

				label.setBounds(55,25,90,30);
				error.setBounds(180,25,90,30);
				error.setForeground(Color.red);
				error.setVisible(false);
		        txtBox.setBounds(50,50,220,30);
		        done.setBounds(180,100,90,30);

		        confFrame.add(label);
		        confFrame.add(error);
		        confFrame.add(txtBox);
		        confFrame.add(done);

		        done.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e){
		            	try{
		            		int value = Integer.parseInt(txtBox.getText());
		            		confFrame.dispose();
		            	}
		            	catch(NumberFormatException ex){
		            		error.setVisible(true);
		            	}
		            }
		        });
            }
        });

        this.order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Stuff here
            }
        });

        this.instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Stuff here
            }
        });

        this.about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Stuff here
            }
        });    	

        this.partsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Stuff here
            }
        });

        this.packsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Stuff here
            }
        });

        this.addPartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Stuff here
            }
        });

        this.removePartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Stuff here
            }
        });

        this.moreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Stuff here
            }
        });        
    }

    private void createFrame(){
        this.frame = new JFrame("Teste");
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setLayout(null);
        this.frame.setSize(500,600);
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    private void createMenuBar(){
        JMenuBar menubar = new JMenuBar();

        JMenu carMenu = new JMenu("Carro");
        this.newCar = new JMenuItem("Novo Carro");
        this.saveCar = new JMenuItem("Guardar Carro");
        this.openFile = new JMenuItem("Abrir Ficheiro");
        this.confOtima = new JMenuItem("Configuração Ótima");
        this.order = new JMenuItem("Encomendar Carro");
        carMenu.add(newCar);
        carMenu.add(saveCar);
        carMenu.add(openFile);
        carMenu.add(confOtima);
        carMenu.add(order);

        JMenu helpMenu = new JMenu("Ajuda");
        this.instructions = new JMenuItem("Instruções");
        this.about = new JMenuItem("Sobre");
        helpMenu.add(instructions);
        helpMenu.add(about);

        menubar.add(carMenu);
        menubar.add(helpMenu);

        this.frame.setJMenuBar(menubar);
    }

    private void addAll(){
        this.frame.add(partsButton);
        this.frame.add(packsButton);
        this.frame.add(my);
        this.frame.add(myLabel);
        this.frame.add(all);
        this.frame.add(allLabel);
        this.frame.add(addPartButton);
        this.frame.add(removePartButton);
        this.frame.add(logs);
        this.frame.add(moreButton);
    }
}

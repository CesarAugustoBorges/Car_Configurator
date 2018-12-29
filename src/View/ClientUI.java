package View;

import Business.Sistema;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class ClientUI extends JPanel{
    private static Sistema s;
    private Map<String, Pair<Integer,String>> pecas;
    private Map<String, Pair<Integer, List<String>>> pacotes;
    private int selectedItem;
    private static List<String> allLogs;

    private JTree my;
    private JScrollPane myScroll;
    private DefaultMutableTreeNode myRoot;
    private JTree all;
    private JScrollPane allScroll;
    private DefaultMutableTreeNode allRoot;
    private JLabel allLabel;
    private JLabel myLabel;
    private JButton partsButton;
    private JButton packsButton;
    private JButton addPartButton;
    private JButton removePartButton;
    private JButton moreButton;
    private JScrollPane logsScroll;
    private static JTextField logs;

    public ClientUI(Sistema s){
        this.s = s;
        this.allLogs = new ArrayList<>();
        this.pecas = new HashMap<>();

        try{
            pecas = s.getAllPecas();
        }
        catch (Exception e){
            logs.setText(e.toString());
            this.allLogs.add(e.toString());
        }

        this.pacotes = new HashMap<>();
        try {
            pacotes = s.getAllPacotes();
        }
        catch (Exception ex){
            logs.setText(ex.toString());
            this.allLogs.add(ex.toString());
        }

        this.selectedItem = 0;
        this.setLayout(null);
        createAllComponents();
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
            img = ImageIO.read(new File("src/View/garbage2.png"));

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

        myRoot = fillMyTree();
        this.my = new JTree(myRoot);
        this.myScroll = new JScrollPane(my);
        myScroll.setBounds(10,70,220,410);
        my.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        allRoot = fillAllTreeParts();
        this.all = new JTree(allRoot);
        this.allScroll = new JScrollPane(all);
        allScroll.setBounds(271,70,220,410);
        all.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));


        this.logs = new JTextField("Logs");
        logs.setEditable(false);
        logs.setAutoscrolls(true);
        this.logsScroll = new JScrollPane(logs);
        logsScroll.setBounds(10,490,420,55);

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

    private DefaultMutableTreeNode fillMyTree(){
        DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode("Carro");
        return newRoot;
    }

    private DefaultMutableTreeNode fillAllTreeParts(){
        DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode("Peças");
        /*
        Map<Categoria, List<Nome>> das peças*/
        Map<String, List<String>> tmp = new HashMap<>();

        for(Map.Entry<String,Pair<Integer, String>> entry : pecas.entrySet()){
            String key = entry.getKey();
            Pair<Integer, String> value = entry.getValue();

            if(tmp.containsKey(value.getValue())){
                tmp.get(value.getValue()).add(key);
            }
            else{
                List<String> list = new ArrayList<>();
                list.add(key);
                tmp.put(value.getValue(), list);
            }
        }

        for(Map.Entry<String, List<String>> entry : tmp.entrySet()){
            String key = entry.getKey();
            List<String> value = entry.getValue();

            DefaultMutableTreeNode node = new DefaultMutableTreeNode(key);
            for(String str : value){
                node.add(new DefaultMutableTreeNode(str));
            }

            newRoot.add(node);
        }

        return newRoot;
    }

    private void createListeners(){
        this.partsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedItem = 0;
                DefaultTreeModel newmodel = new DefaultTreeModel(fillAllTreeParts());
                all.setModel(newmodel);
            }
        });

        this.packsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedItem = 1;
                DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode("Pacotes");
                for(Map.Entry<String, Pair<Integer, List<String>>> entry : pacotes.entrySet()){
                    String key = entry.getKey();
                    List<String> value = entry.getValue().getValue();

                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(key);
                    for(String str : value){
                        node.add(new DefaultMutableTreeNode(str));
                    }
                    newRoot.add(node);
                }

                DefaultTreeModel newmodel = new DefaultTreeModel(newRoot);
                all.setModel(newmodel);
            }
        });

        this.addPartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreeModel model = my.getModel();
                Object[] path = all.getSelectionPath().getPath();
                int i = path.length-1;
                int childs = model.getChildCount(path[i]);
                String nome = path[i].toString();
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) my.getModel().getRoot();


                if(path.length!=1){
                    if(selectedItem==0){
                        if(childs==0){
                            root.add(new DefaultMutableTreeNode(nome));
                            List<Integer> listPecas = new ArrayList<>();
                            int idPeca = pecas.get(nome).getKey();
                            listPecas.add(idPeca);
                            try{
                                s.addPecas(listPecas);
                            }
                            catch(Exception ex){
                                logs.setText(ex.toString());
                                allLogs.add(ex.toString());
                            }
                        }
                        else{
                            System.out.println("Não é uma peça");
                        }
                    }
                    if(selectedItem==1){
                        if(childs!=0){
                            int idPacote = pacotes.get(nome).getKey();
                            try{
                                s.addPacote(idPacote);
                            }
                            catch (Exception exc)
                            {
                                logs.setText(exc.toString());
                                allLogs.add(exc.toString());
                            }
                            DefaultMutableTreeNode node = new DefaultMutableTreeNode(nome);
                            int w=0;
                            while(w<childs){
                                node.add(new DefaultMutableTreeNode(model.getChild(path[i],w).toString()));
                                w++;
                            }

                            root.add(node);
                        }
                        else{
                            System.out.println("Não é um Pacote");
                        }
                    }
                    DefaultTreeModel newmodel = new DefaultTreeModel(root);
                    my.setModel(newmodel);
                }
                else{
                    System.out.println("Não é uma Peça nem um Pacote");
                }
            }
        });

        this.removePartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreeModel model = my.getModel();
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
                Object[] path = my.getSelectionPath().getPath();
                int i = path.length-1;
                int childs = model.getChildCount(path[i]);

                if(path.length==2){
                    if(childs==0) {
                        try {
                            Pair<Integer, String> par = pecas.get(path[1].toString());
                            int idp = par.getKey();
                            s.removePeca(idp,1);
                        } catch (Exception ex){
                            logs.setText(ex.toString());
                            allLogs.add(ex.toString());
                        }
                    } else{
                        try{
                            int id = pacotes.get(path[1].toString()).getKey();
                            s.removePacote(id,1);
                        }
                        catch (Exception ex){
                            logs.setText(ex.toString());
                            allLogs.add(ex.toString());
                        }
                    }
                    root.remove((DefaultMutableTreeNode) path[1]);
                    DefaultTreeModel newmodel = new DefaultTreeModel(root);
                    my.setModel(newmodel);
                }
                else {
                    System.out.println("Nao pode apagar itens de um pacote");
                }
            }
        });

        this.moreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JList l = new JList(allLogs.toArray());
                JScrollPane sp = new JScrollPane(l);
                sp.setBounds(1,1,298,270);

                JFrame f = new JFrame("Logs");
                f.setLayout(null);
                f.setSize(300,300);
                f.setResizable(false);
                f.setLocationRelativeTo(null);
                f.setVisible(true);

                f.add(sp);
            }
        });        
    }

    private void addAll(){
        add(partsButton);
        add(packsButton);
        add(myScroll);
        add(myLabel);
        add(allScroll);
        add(allLabel);
        add(addPartButton);
        add(removePartButton);
        add(logsScroll);
        add(moreButton);
    }

    public static void createMenuBar(JFrame frame){
        JMenuBar menubar = new JMenuBar();

        JMenu carMenu = new JMenu("Carro");
        JMenuItem newCar = new JMenuItem("Novo Carro");
        JMenuItem saveCar = new JMenuItem("Guardar Carro");
        JMenuItem openFile = new JMenuItem("Abrir Ficheiro");
        JMenuItem confOtima = new JMenuItem("Configuração Ótima");
        JMenuItem order = new JMenuItem("Encomendar Carro");

        carMenu.add(newCar);
        carMenu.add(saveCar);
        carMenu.add(openFile);
        carMenu.add(confOtima);
        carMenu.add(order);

        JMenu helpMenu = new JMenu("Ajuda");
        JMenuItem instructions = new JMenuItem("Instruções");
        JMenuItem about = new JMenuItem("Sobre");

        helpMenu.add(instructions);
        helpMenu.add(about);

        menubar.add(carMenu);
        menubar.add(helpMenu);

        frame.setJMenuBar(menubar);

        newCar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Stuff here
            }
        });

        saveCar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Stuff here
            }
        });

        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Stuff here
            }
        });

        confOtima.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configOtima();
            }
        });

        order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel nome = new JLabel("Nome:");
                nome.setBounds(55,40,60,15);

                JLabel nif = new JLabel("NIF:");
                nif.setBounds(55,100,60,15);

                JTextField nomeTxt = new JTextField();
                nomeTxt.setBounds(50,55,220,30);

                JTextField nifTxt = new JTextField();
                nifTxt.setBounds(50,115,220,30);

                JButton print = new JButton("Imprimir");
                print.setBounds(180,165,90,30);

                JFrame frame2 = new JFrame("Faturação");
                frame2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame2.setLayout(null);
                frame2.setSize(325,235);
                frame2.setResizable(false);
                frame2.setLocationRelativeTo(null);
                frame2.setVisible(true);

                frame2.add(nome);
                frame2.add(nomeTxt);
                frame2.add(nif);
                frame2.add(nifTxt);
                frame2.add(print);

                print.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try{
                            s.addEncomenda();
                            //s.imprimirFatura(1, nifTxt.getText());//nomeTxt.getText()
                        }
                        catch (Exception ex){
                            logs.setText(ex.toString());
                            allLogs.add(ex.toString());
                        }
                        frame2.dispose();
                    }
                });
            }
        });

        instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Stuff here
            }
        });

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Stuff here
            }
        });
    }

    private static void configOtima(){
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

        done.addActionListener(new ActionListener(){
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
}

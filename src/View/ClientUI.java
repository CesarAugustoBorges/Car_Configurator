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

    private static JTree my;
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
            int leng = allLogs.size() + 1;
            logs.setText(leng + ". " + e.getMessage());
            this.allLogs.add(leng + ". " + e.getMessage());
        }

        this.pacotes = new HashMap<>();
        try {
            pacotes = s.getAllPacotes();
        }
        catch (Exception ex){
            int len = allLogs.size() + 1;
            logs.setText(len + ". " + ex.getMessage());
            this.allLogs.add(len + ". " + ex.getMessage());
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
                if(all.getSelectionPath()!=null){
                    TreeModel model = my.getModel();
                    Object[] path = all.getSelectionPath().getPath();
                    int i = path.length-1;
                    int childs = model.getChildCount(path[i]);
                    String nome = path[i].toString();
                    DefaultMutableTreeNode root = (DefaultMutableTreeNode) my.getModel().getRoot();
                    //List<Pair<Id, Nome>> (idPeça)
                    //List<Pair<Integer,String>> getLsEIncompativeisComPeca(int id)

                    //List<Pair<Id, Nome>> (idPacote)
                    //List<Pair<Integer,String>> getLsEIncompativeisComPacote(int id)

                    //List<IdPeça> Dependencia
                    //List<Integer> getPecasObrigatorias(int id)

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
                                    int len = allLogs.size() + 1;
                                    logs.setText(len + ". " + ex.getMessage());
                                    allLogs.add(len + ". " + ex.getMessage());
                                }
                            }
                            else{
                                int len = allLogs.size() + 1;
                                logs.setText(len + ". " + "Não é uma peça");
                                allLogs.add(len + ". " + "Não é uma peça");
                            }
                        }
                        if(selectedItem==1){
                            if(childs!=0){
                                int idPacote = pacotes.get(nome).getKey();
                                try{
                                    s.addPacote(idPacote);
                                }
                                catch (Exception exc){
                                    int len = allLogs.size() + 1;
                                    logs.setText(len + ". " + exc.getMessage());
                                    allLogs.add(len + ". " + exc.getMessage());
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
                                int len = allLogs.size() + 1;
                                logs.setText(len + ". " + "Não é um Pacote");
                                allLogs.add(len + ". " + "Não é um Pacote");
                            }
                        }
                        DefaultTreeModel newmodel = new DefaultTreeModel(root);
                        my.setModel(newmodel);
                    }
                    else{
                        int len = allLogs.size() + 1;
                        logs.setText(len + ". " + "Não é uma Peça nem um Pacote");
                        allLogs.add(len + ". " + "Não é uma Peça nem um Pacote");
                    }
                    all.clearSelection();
                }
                else{
                    int len = allLogs.size() + 1;
                    logs.setText(len + ". " + "Nada Selecionado");
                    allLogs.add(len + ". " + "Nada Selecionado");
                }
            }
        });

        this.removePartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(my.getSelectionPath()!=null){
                    TreeModel model = my.getModel();
                    DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
                    Object[] path = my.getSelectionPath().getPath();
                    int i = path.length-1;
                    int childs = model.getChildCount(path[i]);
                    my.clearSelection();

                    if(path.length==2){
                        if(childs==0) {
                            try {
                                Pair<Integer, String> par = pecas.get(path[1].toString());
                                int idp = par.getKey();
                                s.removePeca(idp,1);
                            } catch (Exception ex){
                                int len = allLogs.size() + 1;
                                logs.setText(len + ". " + ex.getMessage());
                                allLogs.add(len + ". " + ex.getMessage());
                            }
                        } else{
                            try{
                                int id = pacotes.get(path[1].toString()).getKey();
                                s.removePacote(id,1);
                            }
                            catch (Exception ex){
                                int len = allLogs.size() + 1;
                                logs.setText(len + ". " + ex.getMessage());
                                allLogs.add(len + ". " + ex.getMessage());
                            }
                        }
                        root.remove((DefaultMutableTreeNode) path[1]);
                        DefaultTreeModel newmodel = new DefaultTreeModel(root);
                        my.setModel(newmodel);
                    }
                    else {
                        if(path.length==1){
                            int len = allLogs.size() + 1;
                            logs.setText(len + ". " + "Nao é uma peça nem pacote");
                            allLogs.add(len + ". " + "Nao é uma peça nem pacote");
                        }
                        else{
                            int len = allLogs.size() + 1;
                            logs.setText(len + ". " + "Nao pode apagar itens de um pacote");
                            allLogs.add(len + ". " + "Nao pode apagar itens de um pacote");
                        }
                    }
                }
                else{
                    int len = allLogs.size() + 1;
                    logs.setText(len + ". " + "Nada Selecionado");
                    allLogs.add(len + ". " + "Nada Selecionado");
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
                            System.out.println(nifTxt.getText() + " " + nomeTxt.getText());
                            s.addEncomenda(nifTxt.getText(), nomeTxt.getText());
                            showBill(s.imprimirFatura(nomeTxt.getText(), nifTxt.getText()));
                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                            int len = allLogs.size() + 1;
                            logs.setText(len + ". " + ex.getMessage());
                            allLogs.add(len + ". " + ex.getMessage());
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

    private static void showBill(String fatura){
        JFrame f = new JFrame("Fatura");
        f.setLayout(null);
        f.setSize(300,400);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        JEditorPane myPane = new JEditorPane();
        myPane.setContentType("text/plain");
        myPane.setText(fatura);
        f.setContentPane(myPane);
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
                List<String> peçasEnc = new ArrayList<>();
                List<String> pacotesEnc = new ArrayList<>();
                try{
                    int value = Integer.parseInt(txtBox.getText());
                    s.configuracaoOtima(value);
                    peçasEnc = s.getPecasEncomenda();
                    pacotesEnc = s.getPacotesEncomenda();
                }
                catch(NumberFormatException ex){
                    error.setVisible(true);
                }
                catch (Exception ex){
                    int len = allLogs.size() + 1;
                    logs.setText(len + ". " + ex.getMessage());
                    allLogs.add(len + ". " + ex.getMessage());
                    confFrame.dispose();
                }

                DefaultMutableTreeNode root = new DefaultMutableTreeNode("Carro");

                for(String str : peçasEnc){
                    root.add(new DefaultMutableTreeNode(str));
                }
                for(String str : pacotesEnc){
                    root.add(new DefaultMutableTreeNode(str));
                }

                DefaultTreeModel newmodel = new DefaultTreeModel(root);
                my.setModel(newmodel);

                confFrame.dispose();
            }
        });
    }
}

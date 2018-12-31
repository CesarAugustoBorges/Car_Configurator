package View;

import Business.Sistema;
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ManagerUI extends JPanel{
	private JLabel seing;
	private JComboBox<String> choose;
	private Sistema s;

	//Lista de Encomendas
	private JLabel filterLabel;
	private JTextField filter;
	private JButton goFilter;
	private JLabel encsLabel;
	private JList encs;
	private JLabel infoEncLabel;
	private JTree infoEncTree;
	private JButton accept;
	private JButton decline;

	private Map<String, Pair<Integer,String>> allEncs;
	private Map<String, Pair<Integer,String>> allPecas;
	private DefaultMutableTreeNode root;

	//Stock
	private JLabel stockLabel;
	private JList stockTree;
	private JLabel infoStockLabel;
	private JList infoStockList;
	private JButton moreStock;

	public ManagerUI(Sistema x) throws Exception{
		this.setLayout(null);
		this.s = x;
		createComp();
		createListeners();
		addAll();
	}

	private void createComp() throws Exception{
		seing = new JLabel("Visualizar:");
		seing.setBounds(20,15,80,15);
		String[] choices = {"Lista de Encomendas", "Stock"};
		choose = new JComboBox<>(choices);
		choose.setBounds(15,30,190,30);

		//Componentes da Lista de Encomendas
		filterLabel = new JLabel("Visualizar por ID do Cliente:");
		filterLabel.setBounds(265,15,200,15);
		filter = new JTextField();
		filter.setBounds(260,30,180,30);
		goFilter = new JButton("->");
		goFilter.setBounds(437,30,30,30);
		
		encsLabel = new JLabel("Encomendas:");
		encsLabel.setBounds(20,70,100,15);
		encs = new JList();
		encs.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		encs.setBounds(15,90,227,365);

		//Map<Descricao, Pair<Id, Categoria>>
		allEncs = s.getAllEncomendas();
		allPecas = s.getAllPecas();

		List<String> x = allEncs.keySet().stream().collect(Collectors.toList());
		DefaultListModel modelo = new DefaultListModel();
		for (String a : x ) {
			modelo.addElement(a);
		}

		encs.setModel(modelo);


		infoEncLabel = new JLabel("Informação da Encomenda:");
		infoEncLabel.setBounds(263,70,190,15);
		root = new DefaultMutableTreeNode("Encomenda");
		infoEncTree = new JTree(root);
		infoEncTree.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		infoEncTree.setBounds(258,90,227,325);

		accept = new JButton("Validar");
		accept.setBounds(345,425,65,30);
		decline = new JButton("Recusar");
		decline.setBounds(415,425,70,30);

		//Componentes do Stock
		stockLabel = new JLabel("Itens do Stock:");
		stockLabel.setBounds(20,70,100,15);
		stockLabel.setVisible(false);
		stockTree = new JList();

		stockTree.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

		List<String> peça = s.getStock().values().stream().collect(Collectors.toList());
		modelo = new DefaultListModel();
		for (String a : peça ) {
			modelo.addElement(a);
		}

		stockTree.setModel(modelo);

		stockTree.setBounds(15,90,227,365);
		stockTree.setVisible(false);

		infoStockLabel = new JLabel("Informação do Item:");
		infoStockLabel.setBounds(263,70,190,15);
		infoStockLabel.setVisible(false);
		infoStockList = new JList();
		infoStockList.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		infoStockList.setBounds(258,90,227,325);
		infoStockList.setVisible(false);

		moreStock = new JButton("Encomendar Mais");
		moreStock.setBounds(315,425,170,30);
		moreStock.setVisible(false);
	}

	private void createListeners(){

		this.encs.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(encs.getSelectedValue() != null){
					DefaultMutableTreeNode newroot = new DefaultMutableTreeNode("Encomenda");
					String enc = encs.getSelectedValue().toString();
					//Map<Nome, Pair<Id, Categoria>>
					Map<String, Pair<Integer, String>> pecas = s.getPecaOfEncomenda(allEncs.get(enc).getKey());
					//Map<Categoria, List<peças>>
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

						newroot.add(node);
					}

					DefaultTreeModel newmodel = new DefaultTreeModel(newroot);
					infoEncTree.setModel(newmodel);
				}
			}
		});


		this.stockTree.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {

				if (stockTree.getSelectedValue() != null) {

					String peça = stockTree.getSelectedValue().toString();

					try {


						int idpeca = allPecas.get(peça).getKey();

						String maximo = ("Máximo = " + s.getInfoOfPeca(idpeca).getKey() + " da mesma.");
						String disponibilidade = "Disponibilidade atual = " + s.getInfoOfPeca(idpeca).getValue();


						DefaultListModel modelo = new DefaultListModel();
						modelo.addElement(maximo);
						modelo.addElement(disponibilidade);
						infoStockList.setModel(modelo);

					}catch (Exception a){
						System.out.println(a);
					}
				}
			}
		});

		this.moreStock.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFrame more = new JFrame("Encomendar mais");
                more.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		        more.setLayout(null);
		        more.setSize(325,175);
		        more.setResizable(false);
		        more.setLocationRelativeTo(null);
		        more.setVisible(true);

		        JLabel label = new JLabel("Quantidade:");
		        JLabel error = new JLabel("Valor errado!");
		        JTextField txtBox = new JTextField();
		        JButton done = new JButton("Feito");

		        label.setBounds(55,25,90,30);
		        error.setBounds(180,25,90,30);
		        error.setForeground(Color.red);
		        error.setVisible(false);
		        txtBox.setBounds(50,50,220,30);
		        done.setBounds(180,100,90,30);

		        more.add(label);
		        more.add(error);
		        more.add(txtBox);
		        more.add(done);

		        done.addActionListener(new ActionListener(){
		            @Override
		            public void actionPerformed(ActionEvent e){
						String peça = stockTree.getSelectedValue().toString();
						int value = Integer.parseInt(txtBox.getText());
						String maximo = new String();
						String disponibilidade = new String();;
		            	try{
		            		int idpeca = allPecas.get(peça).getKey();
							s.encomendarPeca(idpeca,value);
							maximo = ("Máximo = " + s.getInfoOfPeca(idpeca).getKey() + " da mesma.");
							disponibilidade = "Disponibilidade atual = " + s.getInfoOfPeca(idpeca).getValue();
							DefaultListModel mod = new DefaultListModel();
							mod.addElement(maximo);
							mod.addElement(disponibilidade);
							infoStockList.setModel(mod);
							more.dispose();
		                }
		                catch(Exception ex){
		                	error.setVisible(true);
		                	System.out.println(ex);
		                }
		            }
		        });
            }
        });

        this.goFilter.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
				//Stuff here
				System.out.println("Filter");
				DefaultMutableTreeNode r = new DefaultMutableTreeNode("Encomenda");
				DefaultTreeModel newmodel = new DefaultTreeModel(r);
				infoEncTree.setModel(newmodel);
				try {
					if(filter.getText().equals("")){
						List<String> encomendas = s.getAllEncomendas().keySet().stream().collect(Collectors.toList());
						DefaultListModel mod = new DefaultListModel();
						for (String tmp : encomendas) {
							mod.addElement(tmp);
						}

						encs.setModel(mod);
					}else {
						Map<String, Pair<Integer, String>> x = s.getEncomendasDeCliente(Integer.parseInt(filter.getText()));

						DefaultListModel mod = new DefaultListModel();
						for (String tmp : x.keySet()){
							mod.addElement(tmp);
						}

						encs.setModel(mod);
					}

				}catch (Exception a){
					System.out.println(a);
				}
			}
        });

        this.accept.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	try {
            		int idEncomenda = allEncs.get(encs.getSelectedValue().toString()).getKey();
					s.setStatusEncomenda(idEncomenda, "Valida");
					((DefaultListModel) encs.getModel()).remove(encs.getSelectedIndex());

				}catch (Exception a){
            		System.out.println(a);
				}
            }
        });

        this.decline.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	try {
					int idEncomenda = allEncs.get(encs.getSelectedValue().toString()).getKey();
					s.rejeitarEncomenda(idEncomenda);

					((DefaultListModel) encs.getModel()).remove(encs.getSelectedIndex());

				}catch (Exception a){
            		System.out.println(a);
				}
            }
        });

        this.choose.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String selected = (String) choose.getSelectedItem();
                if(selected.equals("Stock")){
                	hideEncs();

                	//adiciona aqui
					//System.out.println(stockTree.getLeadSelectionIndex());

                }
                else{
                	hideStock();
                }
            }
        });
	}

	private void hideEncs(){
		filterLabel.setVisible(false);
		filter.setVisible(false);
		goFilter.setVisible(false);
		encsLabel.setVisible(false);
		encs.setVisible(false);
		infoEncLabel.setVisible(false);
		infoEncTree.setVisible(false);
		accept.setVisible(false);
		decline.setVisible(false);

		stockLabel.setVisible(true);
		stockTree.setVisible(true);
		infoStockLabel.setVisible(true);
		infoStockList.setVisible(true);
		moreStock.setVisible(true);
	}

	private void hideStock(){
		stockLabel.setVisible(false);
		stockTree.setVisible(false);
		infoStockLabel.setVisible(false);
		infoStockList.setVisible(false);
		moreStock.setVisible(false);

		filterLabel.setVisible(true);
		filter.setVisible(true);
		goFilter.setVisible(true);
		encsLabel.setVisible(true);
		encs.setVisible(true);
		infoEncLabel.setVisible(true);
		infoEncTree.setVisible(true);
		accept.setVisible(true);
		decline.setVisible(true);
	}

	private void addAll(){
		add(choose);
		add(seing);
        
        //Lista de Encomendas
		add(filterLabel);
		add(filter);
        add(goFilter);
        add(encsLabel);
        add(encs);
        add(infoEncLabel);
        add(infoEncTree);
        add(accept);
        add(decline);

        //Stock
		add(stockLabel);
		add(stockTree);
		add(infoStockLabel);
		add(infoStockList);
		add(moreStock);
	}
}
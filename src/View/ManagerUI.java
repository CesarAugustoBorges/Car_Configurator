package View;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class ManagerUI extends JPanel{
	private JLabel seing;
	private JComboBox<String> choose;

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

	//Stock
	private JLabel stockLabel;
	private JTree stockTree;
	private JLabel infoStockLabel;
	private JList infoStockList;
	private JButton moreStock;

	public ManagerUI(){
		this.setLayout(null);

		createComp();
		createListeners();
		addAll();
	}

	private void createComp(){
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
		
		infoEncLabel = new JLabel("Informação da Encomenda:");
		infoEncLabel.setBounds(263,70,190,15);
		infoEncTree = new JTree();
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
		stockTree = new JTree();
		stockTree.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
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
		this.choose.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String selected = (String) choose.getSelectedItem();
                if(selected.equals("Stock")){
                	hideEncs();
                }
                else{
                	hideStock();
                }
            }
        });

        this.goFilter.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //Stuff here
            }
        });

        this.accept.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //Stuff here
            }
        });

        this.decline.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //Stuff here
            }
        });

        this.moreStock.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //Stuff here
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
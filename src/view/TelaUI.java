package view;

import dao.LaptopDAO;
import modelo.Laptop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class TelaUI {

    //Declaração dos componentes da tela
    private JPanel panel;
    private JTable laptopTable;
    private JScrollPane scrollPane;
    private JPanel panel_Reg;
    private JTextField proc_Text;
    private JTextField model_Text;
    private JTextField ram_Text;
    private JTextField pol_text;
    private JButton insereReg;
    private DefaultTableModel dadosTabela;
    private LaptopDAO dao;

    public TelaUI() {

        //KeyListeners
        ram_Text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char valida = e.getKeyChar();
                if (Character.isLetter(valida)) {
                    panel_Reg.getToolkit().beep();
                    e.consume();
                    JOptionPane.showMessageDialog(panel_Reg, "Inserir somente números");
                }
            }
        });
        pol_text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char valida = e.getKeyChar();
                if (Character.isLetter(valida)) {
                    panel_Reg.getToolkit().beep();
                    e.consume();
                    JOptionPane.showMessageDialog(panel_Reg, "Inserir somente números");
                }
            }
        });
        proc_Text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char valida = e.getKeyChar();
                if (Character.isDigit(valida)) {
                    panel_Reg.getToolkit().beep();
                    e.consume();
                    JOptionPane.showMessageDialog(panel_Reg, "Não inserir números");
                }
            }
        });

        //Listener de botão para inserir novo registro
        insereReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Converte String em números
                int pol = Integer.parseInt(pol_text.getText()),
                    ram = Integer.parseInt(ram_Text.getText());

                //Pega os dados da janela
                String proc = proc_Text.getText(),
                        model = model_Text.getText();

                //Cria Laptop com os dados da janela
                Laptop l = new Laptop(pol, proc, model, ram);

                //Adiciona o novo registro no DAO
                dao.addLaptop(l);

                //Adiciona nova linha na Tabela
                dadosTabela.addRow(new String[]{
                        proc,
                        model,
                        String.valueOf(pol),
                        String.valueOf(ram)
                });

            }
        });
    }

    //Modelo de criação da tabela e suas colunas
    private void criaTabela() {
        dadosTabela = new DefaultTableModel(
                new String[][]{},
                new String[]{"Processador", "Modelo", "Polegadas", "RAM"});
    }

    //Método para colocar dados na tabela
    public void populaTabela(LaptopDAO dao) {
        for (Laptop laptop_aux : dao.getLaptops()) {
            dadosTabela.addRow(new String[]{
                    laptop_aux.getProcesador().getMarca(),
                    laptop_aux.getProcesador().getModelo(),
                    Integer.toString(laptop_aux.getPolegadas()),
                    Integer.toString(laptop_aux.getRam())
            });
        }
    }

    //Atribui os dados externos de DAO para o local
    public void setDao(LaptopDAO dao) {
        this.dao = dao;
        populaTabela(dao);
    }

    public JPanel getPanel() {
        return panel;
    }

    //Customização da Tela
    private void createUIComponents() {
        criaTabela();
        laptopTable = new JTable(dadosTabela);
        laptopTable.setDefaultEditor(Object.class, null);
//        laptopTable.setCellSelectionEnabled(true);

    }
}

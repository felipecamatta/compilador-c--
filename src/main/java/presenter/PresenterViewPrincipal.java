package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ListIterator;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import model.TabelaDeSimbolo;
import model.Token;
import ufes.Analisador;
import views.ViewPrincipal;

public class PresenterViewPrincipal {

    private ViewPrincipal view;
    private DefaultTableModel tmSimbolos;

    public PresenterViewPrincipal() {
        view = new ViewPrincipal();
        view.setVisible(true);

        tmSimbolos = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Token", "Lexema"}
        );

        this.view.getBtnSelecionar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("c files", "c");
                fileopen.addChoosableFileFilter(filter);

                int ret = fileopen.showDialog(null, "Open file");

                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    if (file.toString().contains(".c")) {
                        view.getTfCaminho().setText(file.toString());

                        FileInputStream fis = null;
                        String texto = "";

                        try {
                            fis = new FileInputStream(file);
                            int content;
                            while ((content = fis.read()) != -1) {
                                texto += (char) content;
                            }
                        } catch (IOException exp) {
                            exp.printStackTrace();
                        } finally {
                            try {
                                if (fis != null) {
                                    fis.close();
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        view.getJtCodigo().setText(texto);

                    } else {
                        JOptionPane.showMessageDialog(view, "Erro! Arquivo precisa ter extens�o .C");
                    }
                }
            }
        });

        this.view.getBtnAnalisar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File arquivo = new File(view.getTfCaminho().getText());
                PrintWriter pw;
                try {
                    pw = new PrintWriter(arquivo);
                    pw.print(view.getJtCodigo().getText());
                    pw.close();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                Analisador analisador = new Analisador();
                TabelaDeSimbolo tabela = analisador.executar(arquivo);
                preencheTabela(tabela);
            }
        });
    }

    private void preencheTabela(TabelaDeSimbolo tabela) {
        tmSimbolos.setNumRows(0);
        ListIterator<Token> it = tabela.getTokens().listIterator();

        while (it.hasNext()) {
            Token token = it.next();
            tmSimbolos.addRow(new Object[]{token.getNome(), token.getValor()});
        }

        this.view.getjTableSaida().setModel(tmSimbolos);
    }

}

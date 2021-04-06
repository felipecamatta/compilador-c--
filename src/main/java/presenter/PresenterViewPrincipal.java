package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import ufes.Analisador;
import views.ViewPrincipal;

/**
 *
 * @author Alcebiades
 */
public class PresenterViewPrincipal {

    private ViewPrincipal vp;

    public PresenterViewPrincipal() {
        vp = new ViewPrincipal();
        vp.setVisible(true);
        selecionarArquivo();
        analisar();
    }

    public void selecionarArquivo() {
        this.vp.getBtnSelecionar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("c files", "c");
                fileopen.addChoosableFileFilter(filter);

                int ret = fileopen.showDialog(null, "Open file");

                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    if (file.toString().contains(".c")) {
                        vp.getTfCaminho().setText(file.toString());

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
                        vp.getJtCodigo().setText(texto);

                    } else {
                        JOptionPane.showMessageDialog(vp, "Erro! Arquivo precisa ter extensão .C");
                    }
                }
            }

        });

    }

    public void analisar() {
        this.vp.getBtnAnalisar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Analisador analisador = new Analisador();
                String tabela = analisador.executar(vp.getTfCaminho().getText()).toString();
                System.out.println(tabela);
//vp.getjTableSaida()
            }
        });
    }
}

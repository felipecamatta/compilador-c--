package ufes;

import cup.sym;
import java_cup.runtime.Symbol;
import jflex.Lexer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class Analisador {

    public TabelaDeSimbolo executar(String arquivo) {
        try {
            String path = arquivo; //Paths.get("").toAbsolutePath().toString() + "\\src\\dados\\teste.txt"

            Lexer lexer = new Lexer( new FileReader(path));

            TabelaDeSimbolo tabela = new TabelaDeSimbolo();

            Symbol s;
            do {
                s = lexer.next_token();
                tabela.add(new Token(s.sym, ""));
            } while (s.sym != sym.EOF);

            //System.out.println(tabela);
            return tabela;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}

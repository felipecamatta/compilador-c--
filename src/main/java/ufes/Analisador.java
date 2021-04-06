package ufes;

import cup.sym;
import java_cup.runtime.Symbol;
import jflex.Lexer;
import model.TabelaDeSimbolo;
import model.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Analisador {

    public TabelaDeSimbolo executar(File arquivo) {
        try {
            //String path = arquivo; //Paths.get("").toAbsolutePath().toString() + "\\src\\dados\\teste.txt"

            Lexer lexer = new Lexer( new FileReader(arquivo));

            TabelaDeSimbolo tabela = new TabelaDeSimbolo();

            Symbol s;
            do {
                s = lexer.next_token();
                tabela.add(new Token(s.sym, lexer.yytext()));
            } while (s.sym != sym.EOF);

            return tabela;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}

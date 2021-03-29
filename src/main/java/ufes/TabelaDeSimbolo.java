package ufes;

import java.util.ArrayList;
import java.util.List;

public class TabelaDeSimbolo {

    List<Token> tokens = new ArrayList<>();

    public TabelaDeSimbolo() {
    }

    public void add(Token token) {
        tokens.add(token);
    }

    @Override
    public String toString() {
        StringBuilder tabela = new StringBuilder();
        for (Token token : tokens) {
            tabela.append(token.getNome() + " : " + token.getValor() + "\n");
        }
        return tabela.toString();
    }
}

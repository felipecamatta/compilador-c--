package model;

import cup.sym;

public class Token {

    private String nome;
    private String valor;

    public Token(int nome, String valor) {
        setNome(nome);
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(int token) {
        nome = getTokenName(token);
    }

    public String getValor() {
        return valor;
    }

    private String getTokenName(int token) {
        try {
            java.lang.reflect.Field[] classFields = sym.class.getFields();
            for (int i = 0; i < classFields.length; i++) {
                if (classFields[i].getInt(null) == token) {
                    return classFields[i].getName();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return "UNKNOWN TOKEN";
    }
}

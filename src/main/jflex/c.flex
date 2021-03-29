package jflex;

import java_cup.runtime.*;

import cup.sym;

%%

%public
%class Lexer
%implements sym

%unicode
%ignorecase

%line
%column

%cup
%cupdebug

%{
    StringBuffer string = new StringBuffer();

    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}


FimLinha = \r|\n|\r\n
InputCaracter = [^\r\n]
EspacoBranco = {FimLinha} | [ \t\f]

/* Comentários */
Comentario = {ComentarioBloco} | {ComentarioLinha}
ComentarioBloco = "/*" [^*] ~"*/"
ComentarioLinha = "//" {InputCaracter}* {FimLinha}?

/* Números */
dig = [0-9]+ \. [0-9]*

/* Caracter */
string = [^\r\n\"\\]

/* Identificador */
Identificador = [A-Za-z_][A-Za-z_0-9]*

%%

<YYINITIAL> {

    /* Palavras-chave */
    "auto"          { return symbol(sym.AUTO); }
    "break"         { return symbol(sym.BREAK); }
    "char"          { return symbol(sym.CHAR); }
    "const"         { return symbol(sym.CONST); }
    "double"        { return symbol(sym.DOUBLE); }
    "else"          { return symbol(sym.ELSE); }
    "enum"          { return symbol(sym.ENUM); }
    "extern"        { return symbol(sym.EXTERN); }
    "float"         { return symbol(sym.FLOAT); }
    "for"           { return symbol(sym.FOR);}
    "if"            { return symbol(sym.IF); }
    "int"           { return symbol(sym.INT); }
    "long"          { return symbol(sym.LONG); }
    "printf"        { return symbol(sym.PRINTF); }
    "return"        { return symbol(sym.RETURN); }
    "scanf"         { return symbol(sym.SCANF); }
    "short"         { return symbol(sym.SHORT); }
    "signed"        { return symbol(sym.SIGNED); }
    "static"        { return symbol(sym.STATIC); }
    "struct"        { return symbol(sym.STRUCT); }
    /* "typedef"    { return symbol(sym.TYPEDEF); } */
    "unsigned"      { return symbol(sym.UNSIGNED); }
    "void"          { return symbol(sym.VOID); }
    "while"         { return symbol(sym.WHILE); }
    "#define"       { return symbol(sym.DEFINE); }

    /* Operadores */
    ">"             { return symbol(sym.MAIOR); }
    "<"             { return symbol(sym.MENOR); }
    "=="            { return symbol(sym.IGUALIGUAL); }
    "<="            { return symbol(sym.MENORIGUAL); }
    "="             { return symbol(sym.IGUAL); }
    ">="            { return symbol(sym.MAIORIGUAL); }
    "!="            { return symbol(sym.DIFERENTE); }
    "&&"            { return symbol(sym.AND); }
    "||"            { return symbol(sym.OR); }
    "+"             { return symbol(sym.MAIS); }
    "-"             { return symbol(sym.MENOS); }
    "*"             { return symbol(sym.MULTIPLICADOR); }
    "/"             { return symbol(sym.DIVISOR); }
    "+="            { return symbol(sym.MAISIGUAL); }
    "-="            { return symbol(sym.MENOSIGUAL); }
    "*="            { return symbol(sym.MULTIPLICADORIGUAL); }
    "/="            { return symbol(sym.DIVISORIGUAL); }
    "%="            { return symbol(sym.MODIGUAL); }

    /* Comentario e espaco em branco */
    {Comentario}    { /* ignorar */ }
    {EspacoBranco}  { /* ignorar */ }

    /* Identificador */
    {Identificador} { return symbol(sym.ID, yytext()); }

    /* Constantes, números e cadeias de literais */
    {dig}           { return symbol(sym.NUM, Integer.valueOf(yytext())); }

    /* Literal */
    {string}      { return symbol(sym.LITERAL); }

    /* Fim e quebra de linha */
    {FimLinha}      { return symbol(sym.CRLF); }

    /* Separadores e Pontuação */
    "("             { return symbol(sym.LPAREN); }
    ")"             { return symbol(sym.RPAREN); }
    "{"             { return symbol(sym.LBRACE); }
    "}"             { return symbol(sym.RBRACE); }
    "["             { return symbol(sym.LBRACK); }
    "]"             { return symbol(sym.RBRACK); }
    ";"             { return symbol(sym.PONTOVIRGULA); }
    ","             { return symbol(sym.VIRGULA); }
    "."             { return symbol(sym.PONTO); }
    "'"             { return symbol(sym.ASPAS); }

}

/* Erro */
[^]                 { throw new RuntimeException("Illegal character \""+yytext()+
                                                    "\" at line "+yyline+", column "+yycolumn); }
<<EOF>>             { return symbol(sym.EOF); }
import java.io.*;

enum TokenType{ NUM,SOMA,MULT,SUB,DIV,APar,FPar, EOF}

class Token{
	//alterando o cosntrutor para aceitar string
	String lexema;
  TokenType token;

	Token(String l, TokenType t)
	{ 
		lexema=l;
		token = t;
	}

 Token (char l, TokenType t)
 	{ 
		lexema=String.valueOf(l);
		token = t;
	}	

}

class AnaliseLexica {
	//alterando para usar o PushbackReader, para poder devolver o caractere lido
	PushbackReader arquivo;

	AnaliseLexica(String a) throws Exception
	{
		
	 	this.arquivo = new PushbackReader(new FileReader(a));
		
	}

	Token getNextToken() throws Exception
	{	
		Token token;
		int eof = -1;
		char currchar;
		int currchar1;

			do{
				currchar1 =  arquivo.read();
				currchar = (char) currchar1;
			} while (currchar == '\n' || currchar == ' ' || currchar =='\t' || currchar == '\r');
			
			if(currchar1 != eof && currchar1 !=10)
			{
				if (currchar >= '0' && currchar <= '9'){
					StringBuilder lexemaString = new StringBuilder();//criando um stringbuilder para armazenar o lexema
					lexemaString.append(currchar); //adicionando o primeiro caractere do lexema

					currchar1 =  arquivo.read(); //lendo o proximo caractere
					currchar = (char) currchar1; //convertendo para char

					while (currchar >= '0' && currchar <= '9'){ //enquanto for um digito
						lexemaString.append(currchar); //adiciona o digito ao lexema
						currchar1 =  arquivo.read(); //lendo o proximo caractere
						currchar = (char) currchar1; //convertendo para char
					}
					//se o caractere lido nao for um digito, devolve ele para o arquivo
					arquivo.unread(currchar); //devolvendo o ultimo caractere lido

					return (new Token (lexemaString.toString(), TokenType.NUM));//retorna o token num
				}
				else
					switch (currchar){
						case '(':
							return (new Token (currchar,TokenType.APar));
						case ')':
							return (new Token (currchar,TokenType.FPar));
						case '+':
							return (new Token (currchar,TokenType.SOMA));
						case '*':
							return (new Token (currchar,TokenType.MULT));
						case '-':
							return (new Token (currchar,TokenType.SUB));
						case '/':
							return (new Token (currchar,TokenType.DIV));
						
						default: throw (new Exception("Caractere inválido: " + ((int) currchar)));
					}
			}

			arquivo.close();
			
		return (new Token(currchar,TokenType.EOF));
		
	}
}

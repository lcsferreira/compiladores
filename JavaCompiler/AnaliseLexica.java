import java.io.*;

enum TokenType{ NUM,SOMA, MULT,APar,FPar, EOF}

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
								
	
				if (currchar >= '0' && currchar <= '9')
					return (new Token (currchar, TokenType.NUM));
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
						
						default: throw (new Exception("Caractere inválido: " + ((int) currchar)));
					}
			}

			arquivo.close();
			
		return (new Token(currchar,TokenType.EOF));
		
	}
}

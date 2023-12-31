/* KarloffParser.java */
/* Generated By:JavaCC: Do not edit this line. KarloffParser.java */
import java.io.*;
import java.util.ArrayList;

class DataType {
  String type;

  DataType(String type){
    this.type = type;
  }

  public String toString() {
    return this.type;
  }
}

class Factor {}

class TrueFactor extends Factor {
  String value = "True";

  public String toString() {
    return this.value;
  }
}

class FalseFactor extends Factor {
  String value = "False";

  public String toString() {
    return this.value;
  }
}

class NumberFactor extends Factor {
  String number;

  NumberFactor(String number) {
    this.number = number;
  }

  public String toString() {
    return this.number;
  }
}

class VariableFactor extends Factor{
  String id;

  VariableFactor(String id){
    this.id = id;
  }

  public String toString() {
    return this.id;
  }
}

class FunctionCall extends Factor{
  String id;
  ArrayList<Expression> argList;

  FunctionCall(String id, ArrayList<Expression> argList){
    this.id = id;
    this.argList = argList;
  }

  public String toString() {
    String block = this.id + "(";

    if (!this.argList.isEmpty()) {

      Expression first = this.argList.remove(0);
      block = block.concat(first.toString());

      for (Expression exp : this.argList) {
        block = block.concat("," + exp.toString());
      }

      argList.add(0, first);
    }

    block = block + ")";

    return block;
  }
}

class Operator {
  String operator;

  Operator(String operator){
    this.operator = operator;
  }

  public String toString() {
    switch (this.operator) {
      case "&":
        return "and";
      case "|":
        return "or";
      default:
        return this.operator;
    }
  }
}

class Expression {}

class Operation extends Expression {
  Operator operator;
  Expression leftExp, rightExp;

  Operation(Operator operator, Expression leftExp, Expression rightExp){
    this.operator = operator;
    this.leftExp = leftExp;
    this.rightExp = rightExp;
  }

  public String toString() {
    return this.leftExp.toString() + " " + this.operator.toString() + " " + this.rightExp.toString();
  }
}

class FinalExpression extends Expression {
  Factor factor;

  FinalExpression(Factor factor){
    this.factor = factor;
  }

  public String toString() {
    return this.factor.toString();
  }
}

class Command {
  int indent = 0;

  public void setIndent(int indent){
    this.indent = indent;
  }

  public int getIndent() {
    return indent;
  }

  public String getTabs() {
    String tabs = "";

    for (int i = 0; i < this.indent; i++){
      tabs = tabs.concat("\t");
    }

    return tabs;
  }
}

class Assignment extends Command {
  String id;
}

class AssignmentExp extends Assignment {
  Expression exp;

  AssignmentExp(String id, Expression exp) {
    this.id = id;
    this.exp = exp;
  }

  public String toString() {
    return this.getTabs() + this.id + " = " + this.exp.toString() + "\n";
  }
}

class AssignmentInput extends Assignment {
  AssignmentInput(String id) {
    this.id = id;
  }

  public String toString() {
    return this.getTabs() + this.id + " = int(input())\n";
  }
}

class IfStatement extends Command {
  Expression condition;
  ArrayList<Command> commands;

  IfStatement(Expression condition, ArrayList<Command> commands){
    this.condition = condition;
    this.commands = commands;
  }

  public String toString() {

    String block = this.getTabs() + "if " + this.condition.toString() + ":\n";

    for (Command command : this.commands) {
      command.setIndent(this.getIndent() + 1);
      block = block.concat(command.toString());
    }

    if (!block.endsWith("\n")){
      block = block + "\n";
    }

    return block;
  }
}

class WhileLoop extends Command {
  Expression condition;
  ArrayList<Command> commands;

  WhileLoop(Expression condition, ArrayList<Command> commands){
    this.condition = condition;
    this.commands = commands;
  }

  public String toString() {
    String block = this.getTabs() + "while " + this.condition.toString() + ":\n";

    for (Command command : this.commands) {
      command.setIndent(this.getIndent() + 1);
      block = block.concat(command.toString());
    }

    if (!block.endsWith("\n")){
      block = block + "\n";
    }

    return block;
  }
}

class RepeatUntilLoop extends Command {
  Expression condition;
  ArrayList<Command> commands;

  RepeatUntilLoop(Expression condition, ArrayList<Command> commands){
    this.condition = condition;
    this.commands = commands;
  }

  public String toString() {
    String block = this.getTabs() + "while not " + this.condition.toString() + ":\n";

    for (Command command : this.commands) {
      command.setIndent(this.getIndent() + 1);
      block = block.concat(command.toString());
    }

    if (!block.endsWith("\n")){
      block = block + "\n";
    }

    return block;
  }
}

class ReturnStatement extends Command {
  Expression exp;

  ReturnStatement(Expression exp){
    this.exp = exp;
  }

  public String toString() {
    return this.getTabs() + "return " + this.exp.toString() + "\n";
  }
}

class PrintStatement extends Command {
  Expression exp;

  PrintStatement(Expression exp){
    this.exp = exp;
  }

  public String toString() {
    return this.getTabs() + "print(" + this.exp.toString() + ")\n";
  }
}

class FunctionCallCommand extends Command {
  FunctionCall functionCall;

  FunctionCallCommand(FunctionCall functionCall){
    this.functionCall = functionCall;
  }

  public String toString() {
    return this.getTabs() + this.functionCall.toString() + "\n";
  }
}

class EntryPoint {
  ArrayList<VariableFactor> variables;
  ArrayList<Command> commands;

  EntryPoint(ArrayList<VariableFactor> variables, ArrayList<Command> commands){
    this.variables = variables;
    this.commands = commands;
  }

  public String toString() {
    String block = "#!/usr/bin python3\n\n";
    block = block + "def main() -> None:\n";

    for (Command command : this.commands) {
      command.setIndent(1);
      block = block.concat(command.toString());
    }

    if (!block.endsWith("\n")){
      block = block + "\n";
    }

    return block;
  }
}

class Argument {
  String name;
  DataType type;

  Argument(String name, DataType type){
    this.name = name;
    this.type = type;
  }

  public String toString() {
    return this.name.toString() + ": " + type.toString();
  }
}

class FunctionDefinition {
  String name;
  DataType return_type;
  ArrayList<Argument> arguments;
  ArrayList<VariableFactor> variables;
  ArrayList<Command> commands;

  FunctionDefinition(String name, DataType return_type, ArrayList<Argument> arguments, ArrayList<VariableFactor> variables, ArrayList<Command> commands){
    this.name = name;
    this.return_type = return_type;
    this.arguments = arguments;
    this.variables = variables;
    this.commands = commands;
  }

  public String toString() {
    String block = "def " + this.name + "(";

    if (!this.arguments.isEmpty()) {

      Argument first = this.arguments.remove(0);
      block = block.concat(first.toString());

      for (Argument argument : this.arguments) {
        block = block.concat("," + argument.toString());
      }

      arguments.add(0, first);
    }

    block = block.concat(") -> " + this.return_type.toString() + ":\n");

    for (Command command : this.commands) {
      command.setIndent(1);
      block = block.concat(command.toString());
    }

    if (!block.endsWith("\n")){
      block = block + "\n";
    }

    return block;
  }
}

class KarloffTree {
  EntryPoint entryPoint;
  ArrayList<FunctionDefinition> functions;

  KarloffTree(EntryPoint entryPoint, ArrayList<FunctionDefinition> functions) {
    this.entryPoint = entryPoint;
    this.functions = functions;
  }

  public String toString() {
    String block = entryPoint.toString() + "\n";

    for (FunctionDefinition function : this.functions) {
      block = block.concat(function.toString() + "\n");
    }

    block = block + "if __name__ == \"__main__\":\n\tmain()\n";

    return block;
  }
}

public class KarloffParser implements KarloffParserConstants {
  public static void main(String args[]) throws Exception {
    FileInputStream fs;
    String filename = "scripts/script.py";

    switch (args.length) {
      case 0:
        throw new Exception("There is no enough params. Template: java KarloffParser <source_filename.kar> <target_filename.py>");
      case 1:
        fs = new FileInputStream(new File(args[0]));
        break;
      case 2:
        fs = new FileInputStream(new File(args[0]));
        filename = new String(args[1]);
        break;
      default:
        throw new Exception("Too much params. Template: java KarloffParser <source_filename.kar> <target_filename.py>");
    }

    KarloffParser parser = new KarloffParser(fs);

    KarloffTree tree = parser.Karloff();

    generateCode(tree, filename);
  }

  public static void generateCode(KarloffTree prog, String filename) throws Exception {
    File file = new File(filename);

    boolean result;

    result = file.createNewFile();

    if(result) {
      System.out.println("file created " + file.getCanonicalPath());
    } else  {
      System.out.println("File already exists at location: " + file.getCanonicalPath());
    }

    FileOutputStream outputStream = new FileOutputStream(file);
    outputStream.write(prog.toString().getBytes());
  }

// Karloff -> MAIN FUNC?
  static final public KarloffTree Karloff() throws ParseException {EntryPoint entryPoint;
  ArrayList<FunctionDefinition> functions = new ArrayList();
    entryPoint = MainC();
    Func(functions);
    jj_consume_token(0);
{if ("" != null) return new KarloffTree(entryPoint, functions);}
    throw new Error("Missing return statement in function");
}

// MainC -> "void" "main" "(" ")" "{" VARDECL SEQCOMANDOS "}"
  static final public EntryPoint MainC() throws ParseException {ArrayList<VariableFactor> variables = new ArrayList();
  ArrayList<Command> commands = new ArrayList();
    jj_consume_token(VOID);
    jj_consume_token(MAIN);
    jj_consume_token(LPAREN);
    jj_consume_token(RPAREN);
    jj_consume_token(LBRACE);
    VarDecl(variables);
    SeqComandos(commands);
    jj_consume_token(RBRACE);
{if ("" != null) return new EntryPoint(variables, commands);}
    throw new Error("Missing return statement in function");
}

// VarDecl -> VarDecl "newVar" TYPE ID ";"
// | ε
  static final public void VarDecl(ArrayList<VariableFactor> variables) throws ParseException {Token t;
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case NEWVAR:{
        ;
        break;
        }
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      jj_consume_token(NEWVAR);
      DataType();
      t = jj_consume_token(ID);
variables.add(new VariableFactor(t.image));
      jj_consume_token(SEMICOLON);
    }
}

// TYPE -> "integer" | "bool"
  static final public DataType DataType() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case BOOL:{
      jj_consume_token(BOOL);
{if ("" != null) return new DataType("bool");}
      break;
      }
    case INT:{
      jj_consume_token(INT);
{if ("" != null) return new DataType("int");}
      break;
      }
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

// SeqComandos -> SeqComandos COMANDO | ε
  static final public void SeqComandos(ArrayList<Command> commands) throws ParseException {Command c;
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case IF:
      case WHILE:
      case REPEAT:
      case RETURN:
      case PRINT:
      case ID:{
        ;
        break;
        }
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      c = Command();
commands.add(c);
      jj_consume_token(SEMICOLON);
    }
}

// Command -> ID "=" EXP ";"
// | ID "(" LISTAEXP? ")" ";"
// | "if" "(" EXP ")" "then" "{" SEQCOMANDOS "}" ";"
// | "while" "(" EXP ")" "{" SEQCOMANDOS "}" ";"
// | "repeat" "{" SEQCOMANDOS "}" "until" "(" EXP ")"
// | "return" EXP ";"
// | "System.output" "(" EXP ")" ";"
// | ID "=" "System.readint" "(" ")" ";"
  static final public Command Command() throws ParseException {Token t;
  Command c;
  Expression test;
  Expression exp;
  ArrayList<Command> commands = new ArrayList();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ID:{
      t = jj_consume_token(ID);
      c = ComID(t);
{if ("" != null) return c;}
      break;
      }
    case IF:{
      jj_consume_token(IF);
      jj_consume_token(LPAREN);
      test = Exp();
      jj_consume_token(RPAREN);
      jj_consume_token(THEN);
      jj_consume_token(LBRACE);
      SeqComandos(commands);
      jj_consume_token(RBRACE);
{if ("" != null) return new IfStatement(test, commands);}
      break;
      }
    case WHILE:{
      jj_consume_token(WHILE);
      jj_consume_token(LPAREN);
      test = Exp();
      jj_consume_token(RPAREN);
      jj_consume_token(LBRACE);
      SeqComandos(commands);
      jj_consume_token(RBRACE);
{if ("" != null) return new WhileLoop(test, commands);}
      break;
      }
    case REPEAT:{
      jj_consume_token(REPEAT);
      jj_consume_token(LBRACE);
      SeqComandos(commands);
      jj_consume_token(RBRACE);
      jj_consume_token(UNTIL);
      jj_consume_token(LPAREN);
      test = Exp();
      jj_consume_token(RPAREN);
{if ("" != null) return new RepeatUntilLoop(test, commands);}
      break;
      }
    case RETURN:{
      jj_consume_token(RETURN);
      exp = Exp();
{if ("" != null) return new ReturnStatement(exp);}
      break;
      }
    case PRINT:{
      jj_consume_token(PRINT);
      jj_consume_token(LPAREN);
      exp = Exp();
      jj_consume_token(RPAREN);
{if ("" != null) return new PrintStatement(exp);}
      break;
      }
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  static final public Command ComID(Token t) throws ParseException {Command c;
  ArrayList<Expression> expressionList = new ArrayList();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ASSIGN:{
      jj_consume_token(ASSIGN);
      c = ComIDAtrib(t);
{if ("" != null) return c;}
      break;
      }
    case LPAREN:{
      jj_consume_token(LPAREN);
      ListExp(expressionList);
      jj_consume_token(RPAREN);
{if ("" != null) return new FunctionCallCommand(new FunctionCall(t.image, expressionList));}
      break;
      }
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  static final public Command ComIDAtrib(Token t) throws ParseException {Expression exp;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LPAREN:
    case TRUE:
    case FALSE:
    case ID:
    case NUM:{
      exp = Exp();
{if ("" != null) return new AssignmentExp(t.image, exp);}
      break;
      }
    case READINT:{
      jj_consume_token(READINT);
      jj_consume_token(LPAREN);
      jj_consume_token(RPAREN);
{if ("" != null) return new AssignmentInput(t.image);}
      break;
      }
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

// EXP -> "(" EXP OP EXP ")" | FACTOR
  static final public Expression Exp() throws ParseException {Expression leftExp, rightExp;
  Operator op;
  Factor f;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LPAREN:{
      jj_consume_token(LPAREN);
      leftExp = Exp();
      op = Op();
      rightExp = Exp();
      jj_consume_token(RPAREN);
{if ("" != null) return new Operation(op, leftExp, rightExp);}
      break;
      }
    case TRUE:
    case FALSE:
    case ID:
    case NUM:{
      f = Factor();
{if ("" != null) return new FinalExpression(f);}
      break;
      }
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

// FACTOR -> ID | ID "(" LISTAEXP? ")"
// | NUM |
// "true" | "false"
  static final public Factor Factor() throws ParseException {Token t;
  ArrayList<Expression> expressionList = new ArrayList();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ID:{
      t = jj_consume_token(ID);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LPAREN:{
        jj_consume_token(LPAREN);
        ListExp(expressionList);
        jj_consume_token(RPAREN);
{if ("" != null) return new FunctionCall(t.image, expressionList);}
        break;
        }
      default:
        jj_la1[7] = jj_gen;
        ;
      }
{if ("" != null) return new VariableFactor(t.image);}
      break;
      }
    case NUM:{
      t = jj_consume_token(NUM);
{if ("" != null) return new NumberFactor(t.image);}
      break;
      }
    case TRUE:{
      jj_consume_token(TRUE);
{if ("" != null) return new TrueFactor();}
      break;
      }
    case FALSE:{
      jj_consume_token(FALSE);
{if ("" != null) return new FalseFactor();}
      break;
      }
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

// OP -> "+" | "-" | "*" | "/" | "&" | "|" | "<" | ">" | "=="
  static final public Operator Op() throws ParseException {Token t;
    t = jj_consume_token(OP);
{if ("" != null) return new Operator(t.image);}
    throw new Error("Missing return statement in function");
}

// ListExp -> Exp | ListExp "," Exp
  static final public void ListExp(ArrayList<Expression> expressionList) throws ParseException {Expression exp;
    exp = Exp();
expressionList.add(exp);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMMA:{
      ListExpL(expressionList);
      break;
      }
    default:
      jj_la1[9] = jj_gen;
      ;
    }
}

  static final public void ListExpL(ArrayList<Expression> expressionList) throws ParseException {Expression exp;
    jj_consume_token(COMMA);
    exp = Exp();
expressionList.add(exp);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMMA:{
      ListExpL(expressionList);
      break;
      }
    default:
      jj_la1[10] = jj_gen;
      ;
    }
}

// FUNC -> FUNC "func" TYPE ID "(" LISTAARG? ")" "{" VARDECL SEQCOMANDOS "}"
// | "func" TYPE ID "(" LISTAARG? ")" "{" VARDECL SEQCOMANDOS "}"
  static final public void Func(ArrayList<FunctionDefinition> functions) throws ParseException {DataType return_type;
  Token t;
  ArrayList<Argument> arguments = new ArrayList();
  ArrayList<VariableFactor> variables = new ArrayList();
  ArrayList<Command> commands = new ArrayList();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case FUNC:{
        ;
        break;
        }
      default:
        jj_la1[11] = jj_gen;
        break label_3;
      }
      jj_consume_token(FUNC);
      return_type = DataType();
      t = jj_consume_token(ID);
      jj_consume_token(LPAREN);
      ListArg(arguments);
      jj_consume_token(RPAREN);
      jj_consume_token(LBRACE);
      VarDecl(variables);
      SeqComandos(commands);
      jj_consume_token(RBRACE);
functions.add(new FunctionDefinition(t.image, return_type, arguments, variables, commands));
    }
}

// LISTAARG -> TIPO TOKEN_id | LISTAARG "," TIPO TOKEN_id
  static final public 
void ListArg(ArrayList<Argument> arguments) throws ParseException {DataType type;
  Token t;
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case INT:
      case BOOL:{
        ;
        break;
        }
      default:
        jj_la1[12] = jj_gen;
        break label_4;
      }
      type = DataType();
      t = jj_consume_token(ID);
arguments.add(new Argument(t.image, type));
      label_5:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case COMMA:{
          ;
          break;
          }
        default:
          jj_la1[13] = jj_gen;
          break label_5;
        }
        jj_consume_token(COMMA);
        type = DataType();
        t = jj_consume_token(ID);
arguments.add(new Argument(t.image, type));
      }
    }
}

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public KarloffParserTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[14];
  static private int[] jj_la1_0;
  static {
	   jj_la1_init_0();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0x800,0xc000,0x20da0000,0x20da0000,0x10200,0x67000200,0x66000200,0x200,0x66000000,0x2000,0x2000,0x8000000,0xc000,0x2000,};
	}

  /** Constructor with InputStream. */
  public KarloffParser(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public KarloffParser(java.io.InputStream stream, String encoding) {
	 if (jj_initialized_once) {
	   System.out.println("ERROR: Second call to constructor of static parser.  ");
	   System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
	   System.out.println("	   during parser generation.");
	   throw new Error();
	 }
	 jj_initialized_once = true;
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new KarloffParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 14; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 14; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public KarloffParser(java.io.Reader stream) {
	 if (jj_initialized_once) {
	   System.out.println("ERROR: Second call to constructor of static parser. ");
	   System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
	   System.out.println("	   during parser generation.");
	   throw new Error();
	 }
	 jj_initialized_once = true;
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new KarloffParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 14; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new KarloffParserTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 14; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public KarloffParser(KarloffParserTokenManager tm) {
	 if (jj_initialized_once) {
	   System.out.println("ERROR: Second call to constructor of static parser. ");
	   System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
	   System.out.println("	   during parser generation.");
	   throw new Error();
	 }
	 jj_initialized_once = true;
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 14; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(KarloffParserTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 14; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	 return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  static private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[31];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 14; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 31; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  static private boolean trace_enabled;

/** Trace enabled. */
  static final public boolean trace_enabled() {
	 return trace_enabled;
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

}

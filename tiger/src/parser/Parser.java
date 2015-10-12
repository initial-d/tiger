package parser;

import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import ast.dec.Dec;
import ast.exp.Add;
import ast.exp.And;
import ast.exp.ArraySelect;
import ast.exp.Call;
import ast.exp.False;
import ast.exp.Id;
import ast.exp.Length;
import ast.exp.Lt;
import ast.exp.NewIntArray;
import ast.exp.NewObject;
import ast.exp.Not;
import ast.exp.Num;
import ast.exp.Sub;
import ast.exp.This;
import ast.exp.Times;
import ast.exp.True;
import ast.mainClass.MainClass;
import ast.method.Method;
import ast.program.Program;
import ast.stm.Assign;
import ast.stm.AssignArray;
import ast.stm.Block;
import ast.stm.Print;
import ast.stm.T;
import ast.stm.While;
import lexer.Lexer;
import lexer.Token;
import lexer.Token.Kind;


public class Parser {
	Lexer lexer;
	Token current;
	Token temp = null; // 用于缓存误被认为表达式声明的标记。
	boolean flag_temp = false;// 对应于被误认为表达式声明的标记的标志位
	HashMap<String, Integer> lineNo = new HashMap<String, Integer>();

	public Parser(String fname, PushbackInputStream fstream) {
		lexer = new Lexer(fname, fstream);
		current = lexer.nextToken();
	}

	// /////////////////////////////////////////////
	// utility methods to connect the lexer
	// and the parser.
	private void advance() {
		current = lexer.nextToken();
	}

	private void eatToken(Kind kind) {
		if (kind == current.kind)
			advance();
		else {
			System.out.println("Expects: " + kind.toString() + " in lines "
					+ Token.lineNum);
			System.out.println("But got: " + current.kind.toString());
		}
	}

	private void error(Token k) {
		System.out.println("Syntax error: compilation aborting...   " + k.kind);
		return;
	}

	

	// ////////////////////////////////////////////////////////////
	// below are method for parsing.

	// A bunch of parsing methods to parse expressions. The messy
	// parts are to deal with precedence and associativity.

	// ExpList -> Exp ExpRest*
	// ->
	// ExpRest -> , Exp
	private java.util.LinkedList<ast.exp.T>  parseExpList() {
		java.util.LinkedList<ast.exp.T> args = new java.util.LinkedList<ast.exp.T>() ;
		if (current.kind == Kind.TOKEN_RPAREN)
			return args;
		args.add(parseExp());
		while (current.kind == Kind.TOKEN_COMMER) {
			advance();
			args.add(parseExp());
		}
		return  args;
	}

	// AtomExp -> (exp)
	// -> INTEGER_LITERAL
	// -> true
	// -> false
	// -> this
	// -> id
	// -> new int [exp]
	// -> new id ()
	private  ast.exp.T parseAtomExp() {
		ast.exp.T exp1 , exp2 = null;
		switch (current.kind) {
		case TOKEN_LPAREN:
			advance();
			ast.exp.T exp3 = this.parseExp();
			eatToken(Kind.TOKEN_RPAREN);
			return new ast.exp.Paren(exp3);  //没有作处理！！！
		case TOKEN_NUM:
			int num = current.lexeme.charAt(0) -'0';
			for(int i = 1 ; i < current.lexeme.length() ; i++)
			{
				num = (current.lexeme.charAt(i) -'0') + num * 10;
			}
			int line = current.lineNum;
			advance();
			return new Num(num , line);
		case TOKEN_TRUE:
			int linet = current.lineNum;
			advance();
			return new True(linet);
		case TOKEN_FALSE:
			int linef = current.lineNum;
			advance();
			return new False(linef);
		case TOKEN_THIS:
			int lineth = current.lineNum;
			advance();
			return new This(lineth);
		case TOKEN_ID:  // 对id的处理没完
			String id1 ;
			id1 = current.lexeme;
			int linei = current.lineNum;
			advance();
			return new Id(id1 , linei);
		case TOKEN_NEW: {
			advance();
			ast.exp.T n;
			switch (current.kind) {
			case TOKEN_INT:
				int lineint = current.lineNum;
				advance();
				eatToken(Kind.TOKEN_LBRACK);
				n = parseExp();
				eatToken(Kind.TOKEN_RBRACK);
				return new NewIntArray(n , lineint);
			case TOKEN_ID:
				String iid = current.lexeme;
				int lineid = current.lineNum;
				advance();
				eatToken(Kind.TOKEN_LPAREN);
				eatToken(Kind.TOKEN_RPAREN);
				return new NewObject(iid , lineid);
			default:
				error(current);
				return null;
			}
		}
		default:
			error(current);
			return null;
		}
	}

	// NotExp -> AtomExp
	// -> AtomExp .id (expList)
	// -> AtomExp [exp]
	// -> AtomExp .length
	// id 此处的id如何处理呢
	private ast.exp.T parseNotExp() {
		ast.exp.T exp1 , exp2 = null;
		exp1 = parseAtomExp();
		while (current.kind == Kind.TOKEN_DOT
				|| current.kind == Kind.TOKEN_LBRACK) {
			if (current.kind == Kind.TOKEN_DOT) {
				advance();
				if (current.kind == Kind.TOKEN_LENGTH) {
					int linelen = current.lineNum;
					advance();
					return new Length(exp1 , linelen);
				}
				String id = null;
				java.util.LinkedList<ast.exp.T> args = new java.util.LinkedList<ast.exp.T>();
				id = current.lexeme;
				int linecall = current.lineNum;
				eatToken(Kind.TOKEN_ID);
				eatToken(Kind.TOKEN_LPAREN);
				args = parseExpList();
				eatToken(Kind.TOKEN_RPAREN);
				return new Call(exp1 , id , args  , linecall);
			} else {
				int lineal = current.lineNum;
				advance();
				exp2 = parseExp();
				eatToken(Kind.TOKEN_RBRACK);
				return new ArraySelect(exp1 , exp2 , lineal);
			}
		}
		return exp1;
	}

	// TimesExp -> ! TimesExp
	// -> NotExp
	private ast.exp.T parseTimesExp() {
		ast.exp.T exp1;
		int  flag = 0;
		int line = 0;
		while (current.kind == Kind.TOKEN_NOT) {  // while用法有目的！！！！！
			line = current.lineNum;
			advance();
			flag++;
		}
		exp1 = parseNotExp();
		if(flag % 2 == 1)
			return new Not(exp1 , line);
		else
			return exp1;
	}

	// AddSubExp -> TimesExp * TimesExp
	// -> TimesExp
	private ast.exp.T parseAddSubExp() {
		ast.exp.T exp1 , exp2 = null;
		boolean flag = false;
		exp1 = parseTimesExp();
		int line = 0;
		while (current.kind == Kind.TOKEN_TIMES) {
			flag = true;
			line = current.lineNum;
			advance();
			exp2 = parseTimesExp();
		}
		if(flag)
			return new Times(exp1 , exp2 , line);
		else
			return exp1;
	}

	// LtExp -> AddSubExp + AddSubExp
	// -> AddSubExp - AddSubExp
	// -> AddSubExp
	private ast.exp.T parseLtExp() {
		ast.exp.T exp1 , exp2 = null;
		int  flag = 1;
		exp1 = parseAddSubExp();
		while (current.kind == Kind.TOKEN_ADD || current.kind == Kind.TOKEN_SUB) {
			if(current.kind == Kind.TOKEN_ADD)
				flag = 2;
			else
				flag = 3;
			advance();
			exp2 = parseAddSubExp();
		}
		if(flag == 1)
			return exp1;
		else if(flag == 2)
			return new Add(exp1 , exp2 , current.lineNum);
		else
			return new Sub(exp1 , exp2 , current.lineNum);
	}
	
	// AndExp -> LtExp < LtExp
	// -> LtExp
	private ast.exp.T parseAndExp() {
		ast.exp.T exp1, exp2 = null;
		boolean flag = false;
		exp1 = parseLtExp();
		while (current.kind == Kind.TOKEN_LT) {
			flag = true;
			advance();
			exp2 = parseLtExp();
		}
		if(flag)
			return new Lt(exp1 , exp2 , current.lineNum);
		else
			return exp1;
	}

	// Exp -> AndExp && AndExp
	// -> AndExp
	private ast.exp.T parseExp() {
		ast.exp.T exp1, exp2 = null;
		boolean flag = false;
		exp1 = parseAndExp();
		while (current.kind == Kind.TOKEN_AND) {
			flag = true;
			advance();
			exp2 = parseAndExp();
		}
		if(flag)
			return new And(exp1 , exp2 , current.lineNum);
		else
			return exp1;
	}

	// Statement -> { Statement* }
	// -> if ( Exp ) Statement else Statement
	// -> while ( Exp ) Statement
	// -> System.out.println ( Exp ) ;
	// -> id = Exp ;
	// -> id [ Exp ]= Exp ;
	private ast.stm.T parseStatement() {
		// Lab1. Exercise 4: Fill in the missing code
		// to parse a statement.
		ast.stm.T stm ;
		switch (current.kind) {
		case TOKEN_LBRACE:
			java.util.LinkedList<T> stms = new java.util.LinkedList<T>();
			advance();
			stms.add(parseStatement());
			while (current.kind != Kind.TOKEN_RBRACE) {
				stms.add(parseStatement());
			}
			eatToken(Kind.TOKEN_RBRACE);
			return new Block(stms);
		case TOKEN_IF:
			int lineif = current.lineNum;
			advance();
			eatToken(Kind.TOKEN_LPAREN);
			ast.exp.T condition = parseExp();
			eatToken(Kind.TOKEN_RPAREN);
			T thenn = parseStatement();
			T elsee = null;
			if (current.kind == Kind.TOKEN_ELSE) {
				advance();
				elsee = parseStatement();
			}
			return new ast.stm.If(condition , thenn , elsee , lineif);
		case TOKEN_ASSIGN:
			int lineas = current.lineNum;
			String id = temp.lexeme;
			eatToken(Kind.TOKEN_ASSIGN);
			ast.exp.T exp = parseExp();
			eatToken(Kind.TOKEN_SEMI);
			return  new ast.stm.Assign(id , exp , lineas);
		case TOKEN_SYSTEM:
			int linesy = current.lineNum;
			ast.exp.T print;
			advance();
			eatToken(Kind.TOKEN_DOT);
			eatToken(Kind.TOKEN_OUT);
			eatToken(Kind.TOKEN_DOT);
			eatToken(Kind.TOKEN_PRINTLN);
			eatToken(Kind.TOKEN_LPAREN);
			print = parseExp();
			eatToken(Kind.TOKEN_RPAREN);
			eatToken(Kind.TOKEN_SEMI);
			return  new Print(print , linesy);
		case TOKEN_WHILE:
			int linewh = current.lineNum;
			ast.exp.T cond;
			advance();
			eatToken(Kind.TOKEN_LPAREN);
			cond = parseExp();
			eatToken(Kind.TOKEN_RPAREN);
			T body = parseStatement();
			return new While(cond , body , linewh);
		case TOKEN_ID:
			int lineid = current.lineNum;
			String idb;
			ast.exp.T  expn = null;
			ast.exp.T index = null;
			idb = current.lexeme;
			advance();
			boolean flag = false;
			if (current.kind == Kind.TOKEN_ASSIGN) {
				flag = true;
				eatToken(Kind.TOKEN_ASSIGN);
				expn = parseExp();
			} else {
				eatToken(Kind.TOKEN_LBRACK);
				index = parseExp();
				eatToken(Kind.TOKEN_RBRACK);
				eatToken(Kind.TOKEN_ASSIGN);
				expn = parseExp();
			}
			eatToken(Kind.TOKEN_SEMI);
			if(flag)
				return new Assign(idb , expn , lineid);
			else
				return new AssignArray(idb , index ,expn , lineid);
		default:
			error(current);
			return null; /// 需要处理！！
		}
	}

	// new util.Todo();

	// Statements -> Statement Statements
	// ->
	private java.util.LinkedList<ast.stm.T>  parseStatements() {
		java.util.LinkedList<ast.stm.T> stms = new java.util.LinkedList<ast.stm.T>();
		while (current.kind == Kind.TOKEN_LBRACE
				|| current.kind == Kind.TOKEN_IF
				|| current.kind == Kind.TOKEN_WHILE
				|| current.kind == Kind.TOKEN_SYSTEM
				|| current.kind == Kind.TOKEN_ID
				|| current.kind == Kind.TOKEN_ASSIGN) {
		//	if(parseStatement() != null)
				stms.add(parseStatement());
		}
		return stms;
	}

	// Type -> int []
	// -> boolean
	// -> int
	// -> id //是否是合理的存在呢？？？
	private ast.type.T  parseType() {
		// Lab1. Exercise 4: Fill in the missing code
		// to parse a type.
		if (current.kind == Kind.TOKEN_INT) {
			advance();
			if (current.kind == Kind.TOKEN_LBRACK) {
				advance();
				eatToken(Kind.TOKEN_RBRACK);
				return 	new ast.type.IntArray(current.lineNum);
			}
			return new ast.type.Int(current.lineNum);
		} else if (current.kind == Kind.TOKEN_BOOLEAN) {
			advance();
			return  new ast.type.Boolean(current.lineNum);
		} else if (current.kind == Kind.TOKEN_ID) {
			temp = current;
			advance();
			return  new ast.type.Class(temp.lexeme , current.lineNum);
		}
		return null;
	}

	// VarDecl -> Type id ;
	private ast.dec.T parseVarDecl() {
		// to parse the "Type" nonterminal in this method, instead of writing
		// a fresh one.
		flag_temp = false;
		ast.type.T type = parseType();
		if (current.kind == Token.Kind.TOKEN_ASSIGN) {
			flag_temp = true;
			Dec hi = new Dec(type , "sorry");
			return  hi;
		}
		int line = current.lineNum;
		String id = current.lexeme;
		eatToken(Kind.TOKEN_ID);// 默认声明一个变量
		eatToken(Kind.TOKEN_SEMI);
		return new Dec(type , id , line);
	}	
	
	// VarDecls -> VarDecl VarDecls
	// ->
	private java.util.LinkedList<ast.dec.T>  parseVarDecls() {
		// System.out.println(current.toString());
		java.util.LinkedList<ast.dec.T> locals = new java.util.LinkedList<ast.dec.T>();
		while (current.kind == Kind.TOKEN_INT
				|| current.kind == Kind.TOKEN_BOOLEAN
				|| current.kind == Kind.TOKEN_ID) {
			if (flag_temp) {
				flag_temp = false;
				break;
			}
			ast.dec.T hi = parseVarDecl();
			if(!flag_temp)
				locals.add(hi);
		}
		return locals;
	}

	// FormalList -> Type id FormalRest*
	// ->
	// FormalRest -> , Type id
	private java.util.LinkedList<ast.dec.T>  parseFormalList() {
		java.util.LinkedList<ast.dec.T> formals = new java.util.LinkedList<ast.dec.T>();
		if (current.kind == Kind.TOKEN_INT
				|| current.kind == Kind.TOKEN_BOOLEAN
				|| current.kind == Kind.TOKEN_ID) {
			ast.type.T type= parseType();
			String id = current.lexeme;
			eatToken(Kind.TOKEN_ID);
			formals.add(new Dec(type , id));
			while (current.kind == Kind.TOKEN_COMMER) {
				advance();
				type= parseType();
				id = current.lexeme;
				eatToken(Kind.TOKEN_ID);
				formals.add(new Dec(type , id));
			}
		}
		return  formals;
	}
	// Method -> public Type id ( FormalList )
	// { VarDecl* Statement* return Exp ;}
	private ast.method.T parseMethod() {
		// Lab1. Exercise 4: Fill in the missing code
		// to parse a method.
		eatToken(Kind.TOKEN_PUBLIC);
		ast.type.T retType = parseType();
		String id = current.lexeme;
		eatToken(Kind.TOKEN_ID);
		eatToken(Kind.TOKEN_LPAREN);
		java.util.LinkedList<ast.dec.T> formals =  new java.util.LinkedList<ast.dec.T>();
		formals = parseFormalList();
		eatToken(Kind.TOKEN_RPAREN);
		eatToken(Kind.TOKEN_LBRACE);
		flag_temp = false;
		java.util.LinkedList<ast.dec.T> locals = new java.util.LinkedList<ast.dec.T>();
		locals = parseVarDecls();
		java.util.LinkedList<ast.stm.T> stms = new java.util.LinkedList<ast.stm.T>(); 
		stms = parseStatements();
		eatToken(Kind.TOKEN_RETURN);
		ast.exp.T retExp = parseExp();
		int line = current.lineNum;
		eatToken(Kind.TOKEN_SEMI);
		eatToken(Kind.TOKEN_RBRACE);
		// new util.Todo();
		return  new Method(retType , id , formals , locals , stms , retExp , line);
	}

	// MethodDecls -> MethodDecl MethodDecls
	// ->
	private java.util.LinkedList<ast.method.T> parseMethodDecls() {
		java.util.LinkedList<ast.method.T> ms = new java.util.LinkedList<ast.method.T>();
		while (current.kind == Kind.TOKEN_PUBLIC) {
			ms.add(parseMethod());
		}
		return ms;
	}

	// ClassDecl -> class id { VarDecl* MethodDecl* }
	// -> class id extends id { VarDecl* MethodDecl* }
	private ast.classs.T parseClassDecl() {
		flag_temp = false;
		eatToken(Kind.TOKEN_CLASS);
		String id = current.lexeme;
		int line = current.lineNum;
		String exid = null;
		eatToken(Kind.TOKEN_ID);
		if (current.kind == Kind.TOKEN_EXTENDS) {
			eatToken(Kind.TOKEN_EXTENDS);
			exid = current.lexeme;
			eatToken(Kind.TOKEN_ID);
		}
		eatToken(Kind.TOKEN_LBRACE);
		java.util.LinkedList<ast.dec.T> decs =  new java.util.LinkedList<ast.dec.T>();
		decs = parseVarDecls();
		java.util.LinkedList<ast.method.T> methods = new java.util.LinkedList<ast.method.T>(); 
		methods = parseMethodDecls();
		eatToken(Kind.TOKEN_RBRACE);
		return new ast.classs.Class(id , exid , decs , methods , line);
	}

	// ClassDecls -> ClassDecl ClassDecls
	// ->
	private java.util.LinkedList<ast.classs.T> parseClassDecls() {
		java.util.LinkedList<ast.classs.T> cla = new java.util.LinkedList<ast.classs.T>();
		while (current.kind == Kind.TOKEN_CLASS) {
			cla.add(parseClassDecl());
		}
		return cla;
	}

	// MainClass -> class id
	// {
	// public static void main ( String [] id )
	// {
	// Statement
	// }
	// }
	private ast.mainClass.T parseMainClass() // 默认一开始是主类，默认主类中的方法就是main
	{
		// Lab1. Exercise 4: Fill in the missing code
		// to parse a main class as described by the
		// grammar above.
		if(current.kind == Kind.TOKEN_PUBLIC)
		{
			advance();
		}
		eatToken(Kind.TOKEN_CLASS);
		int line = current.lineNum;
		String id = current.lexeme;
		eatToken(Kind.TOKEN_ID);
		eatToken(Kind.TOKEN_LBRACE);
		eatToken(Kind.TOKEN_PUBLIC);
		eatToken(Kind.TOKEN_STATIC);
		eatToken(Kind.TOKEN_VOID);
		eatToken(Kind.TOKEN_MAIN);
		eatToken(Kind.TOKEN_LPAREN);
		eatToken(Kind.TOKEN_STRING);
		eatToken(Kind.TOKEN_LBRACK);
		eatToken(Kind.TOKEN_RBRACK);
		String string = current.lexeme;
		eatToken(Kind.TOKEN_ID);
		eatToken(Kind.TOKEN_RPAREN);
		eatToken(Kind.TOKEN_LBRACE);
		ast.stm.T stm = parseStatement();
		eatToken(Kind.TOKEN_RBRACE);
		eatToken(Kind.TOKEN_RBRACE);
		return new MainClass(id , string , stm , line);
		// new util.Todo();
	}

	// Program -> MainClass ClassDecl*
	private ast.program.T parseProgram() {
		return  new Program(parseMainClass(),parseClassDecls()) ;
	}

	public ast.program.T parse() {
		ast.program.T a = parseProgram();;
		return a;
	}
}

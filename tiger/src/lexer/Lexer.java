package lexer;

import java.io.PushbackInputStream;
import java.util.HashMap;
import util.Todo;

import lexer.Token.Kind;

public class Lexer {
	String fname; // the input file name to be compiled
	PushbackInputStream fstream;// input stream for the above file
	HashMap<String, Token.Kind> tab = new HashMap<String, Token.Kind>();

	public Lexer(String fname, PushbackInputStream fstream) {
		this.fname = fname;
		this.fstream = fstream;
		tab.put("boolean", Kind.TOKEN_BOOLEAN);
		tab.put("class", Kind.TOKEN_CLASS);
		tab.put("else", Kind.TOKEN_ELSE);
		tab.put("extends", Kind.TOKEN_EXTENDS);
		tab.put("false", Kind.TOKEN_FALSE);
		tab.put("if", Kind.TOKEN_IF);
		tab.put("int", Kind.TOKEN_INT);
		tab.put("length", Kind.TOKEN_LENGTH);
		tab.put("main", Kind.TOKEN_MAIN);
		tab.put("new", Kind.TOKEN_NEW);
		tab.put("out", Kind.TOKEN_OUT);
		tab.put("print", Kind.TOKEN_PRINT);
		tab.put("println", Kind.TOKEN_PRINTLN);
		tab.put("public", Kind.TOKEN_PUBLIC);
		tab.put("return", Kind.TOKEN_RETURN);
		tab.put("static", Kind.TOKEN_STATIC);
		tab.put("String", Kind.TOKEN_STRING);
		tab.put("System", Kind.TOKEN_SYSTEM);
		tab.put("this", Kind.TOKEN_THIS);
		tab.put("true", Kind.TOKEN_TRUE);
		tab.put("void", Kind.TOKEN_VOID);
		tab.put("while", Kind.TOKEN_WHILE);
	}

	// When called, return the next token (refer to the code "Token.java")
	// from the input stream.
	// Return TOKEN_EOF when reaching the end of the input stream.
	private Token nextTokenInternal() throws Exception {
		int c = this.fstream.read();
		if (-1 == c)
			// The value for "Token.lineNum" is now "null",
			// you should modify this to an appropriate
			// line number for the "EOF" token.
			return new Token(Kind.TOKEN_EOF, Token.lineNum, null);

		// skip all kinds of "blanks"
		while (' ' == c || '\t' == c || '\n' == c) {
			if (c == '\n')
				Token.lineNum++;
			c = this.fstream.read();
		}
		if (-1 == c)
			return new Token(Kind.TOKEN_EOF, Token.lineNum, null);
		while (c == '/') {
			c = this.fstream.read();
			if (c == '/') {
				while (c != '\n') {
					c = fstream.read();
				}
				while (' ' == c || '\t' == c || '\n' == c) {
					if (c == '\n')
						Token.lineNum++;
					c = this.fstream.read();
				}
				if (-1 == c)
					return new Token(Kind.TOKEN_EOF, Token.lineNum, null);
			} else if (c == '*') {
				while (c != '*' || this.fstream.read() != '/')
					c = fstream.read();
				while (' ' == c || '\t' == c || '\n' == c) {
					if (c == '\n')
						Token.lineNum++;
					c = this.fstream.read();
				}
				if (-1 == c)
					return new Token(Kind.TOKEN_EOF, Token.lineNum, null);
			} else
				switch (c) {
				case '*':
					return new Token(Kind.TOKEN_LNOTE, Token.lineNum);
				default:
					fstream.unread(c);
					return new Token(Kind.TOKEN_DIV, Token.lineNum);
				}
		}
		switch (c) {
		case '+':
			return new Token(Kind.TOKEN_ADD, Token.lineNum);
		case '"':
			return new Token(Kind.TOKEN_QUTO, Token.lineNum);
		case '-':
			return new Token(Kind.TOKEN_SUB, Token.lineNum);
		case '*':
			return new Token(Kind.TOKEN_TIMES, Token.lineNum);
		case '\\':
			return new Token(Kind.TOKEN_BSLANT, Token.lineNum);
		case ':':
			return new Token(Kind.TOKEN_COLON, Token.lineNum);
		case ',':
			return new Token(Kind.TOKEN_COMMER, Token.lineNum);
		case '.':
			return new Token(Kind.TOKEN_DOT, Token.lineNum);
		case '{':
			return new Token(Kind.TOKEN_LBRACE, Token.lineNum);
		case '[':
			return new Token(Kind.TOKEN_LBRACK, Token.lineNum);
		case '(':
			return new Token(Kind.TOKEN_LPAREN, Token.lineNum);
		case '}':
			return new Token(Kind.TOKEN_RBRACE, Token.lineNum);
		case ']':
			return new Token(Kind.TOKEN_RBRACK, Token.lineNum);
		case ')':
			return new Token(Kind.TOKEN_RPAREN, Token.lineNum);
		case ';':
			return new Token(Kind.TOKEN_SEMI, Token.lineNum);
		case '!':
			return new Token(Kind.TOKEN_NOT, Token.lineNum);
		case '=': {
			int c2 = this.fstream.read();
			switch (c2) {
			case '=':
				return new Token(Kind.TOKEN_EQ, Token.lineNum);
			default:
				fstream.unread(c2);
				return new Token(Kind.TOKEN_ASSIGN, Token.lineNum);
			}
		}
		case '>': {
			int c2 = this.fstream.read();
			switch (c2) {
			case '=':
				return new Token(Kind.TOKEN_GE, Token.lineNum);
			default:
				fstream.unread(c2);
				return new Token(Kind.TOKEN_GT, Token.lineNum);
			}
		}
		case '<': {
			int c2 = this.fstream.read();
			switch (c2) {
			case '=':
				return new Token(Kind.TOKEN_LE, Token.lineNum);
			case '>':
				return new Token(Kind.TOKEN_NE, Token.lineNum);
			default:
				fstream.unread(c2);
				return new Token(Kind.TOKEN_LT, Token.lineNum);
			}
		}
		case '&': {
			int c2 = this.fstream.read();
			switch (c2) {
			case '&':
				return new Token(Kind.TOKEN_AND, Token.lineNum);
			default:
				fstream.unread(c2);
				return new Token(Kind.TOKEN_CAND, Token.lineNum);
			}
		}
		case '|': {
			int c2 = this.fstream.read();
			switch (c2) {
			case '|':
				return new Token(Kind.TOKEN_OR, Token.lineNum);
			default:
				fstream.unread(c2);
				return new Token(Kind.TOKEN_COR, Token.lineNum);
			}
		}
		default: {
			String tem = "";
			boolean flag = true;
			if (c == '_' || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
					|| (c >= 48 && c <= 57)) {
				while (c == '_' || (c >= 'a' && c <= 'z')
						|| (c >= 'A' && c <= 'Z') || (c >= 48 && c <= 57)) {
					if (c < 48 || c > 57)
						flag = false;
					tem += (char) c;
					c = this.fstream.read();
				}
				fstream.unread(c);
				if (flag) {
					return new Token(Kind.TOKEN_NUM, Token.lineNum, tem);
				} else {
					if (null != tab.get(tem))
						return new Token(tab.get(tem), Token.lineNum, tem);
					else
						return new Token(Kind.TOKEN_ID, Token.lineNum, tem);
				}
			} else
				return new Token(Kind.TOKEN_ERROR, Token.lineNum);
			// new Todo();
			// return null;
		}
		}
	}

	public Token nextToken() {
		Token t = null;
		try {
			t = this.nextTokenInternal();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		if (control.Control.lex)
			System.out.println(t.toString());
		return t;
	}
}
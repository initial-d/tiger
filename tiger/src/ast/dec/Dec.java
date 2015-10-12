package ast.dec;

import ast.Visitor;

public class Dec extends T {
	public ast.type.T type;
	public String id;

	public Dec(ast.type.T type, String id) {
		this.type = type;
		this.id = id;
	}
	public Dec(ast.type.T type, String id ,int line) {
		this.type = type;
		this.id = id;
		this.lineNo = line;
	}
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}

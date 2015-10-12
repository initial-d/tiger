package ast.stm;

public class Assign extends T {
	public String id;
	public ast.exp.T exp;
	public ast.type.T type; // type of the id
	//public boolean isField;

	public Assign(String id, ast.exp.T exp) {
		this.id = id;
		this.exp = exp;
		this.type = null;
	}

	public Assign(String id, ast.exp.T exp , int line) {
		this.id = id;
		this.exp = exp;
		this.type = null;
		//this.isField = false;
		this.lineNo = line;
	}

	@Override
	public void accept(ast.Visitor v) {
		v.visit(this);
	}
}

package ast.stm;

public class If extends T {
	public ast.exp.T condition;
	public T thenn;
	public T elsee;

	public If(ast.exp.T condition, T thenn, T elsee) {
		this.condition = condition;
		this.thenn = thenn;
		this.elsee = elsee;
	}
	public If(ast.exp.T condition, T thenn, T elsee , int line) {
		this.condition = condition;
		this.thenn = thenn;
		this.elsee = elsee;
		this.lineNo = line;
	}

	@Override
	public void accept(ast.Visitor v) {
		v.visit(this);
	}
}

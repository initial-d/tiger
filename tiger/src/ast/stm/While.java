package ast.stm;

public class While extends T {
	public ast.exp.T condition;
	public T body;

	public While(ast.exp.T condition, T body) {
		this.condition = condition;
		this.body = body;
	}
	public While(ast.exp.T condition, T body ,int line) {
		this.condition = condition;
		this.body = body;
		this.lineNo = line;
	}

	@Override
	public void accept(ast.Visitor v) {
		v.visit(this);
	}
}

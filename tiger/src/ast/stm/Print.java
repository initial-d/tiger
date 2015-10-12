package ast.stm;

public class Print extends T {
	public ast.exp.T exp;

	public Print(ast.exp.T exp) {
		this.exp = exp;
	}
	public Print(ast.exp.T exp , int line) {
		this.exp = exp;
		this.lineNo = line;
	}

	@Override
	public void accept(ast.Visitor v) {
		v.visit(this);
	}
}

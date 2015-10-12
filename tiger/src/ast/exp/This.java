package ast.exp;

public class This extends T {
	public This() {
	}
	public This(int line) {
		this.lineNo = line;
	}

	@Override
	public void accept(ast.Visitor v) {
		v.visit(this);
		return;
	}
}

package ast.exp;

public class True extends T {
	public True() {
	}
	public True(int line) {
		this.lineNo = line;
	}

	@Override
	public void accept(ast.Visitor v) {
		v.visit(this);
		return;
	}
}

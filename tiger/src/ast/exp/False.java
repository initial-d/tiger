package ast.exp;

public class False extends T {
	public False() {
	}
	public False(int line) {
		this.lineNo = line;
	}

	@Override
	public void accept(ast.Visitor v) {
		v.visit(this);
		return;
	}
}

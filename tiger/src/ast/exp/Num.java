package ast.exp;

public class Num extends T {
	public int num;

	public Num(int num) {
		this.num = num;
	}
	public Num(int num , int line) {
		this.num = num;
		this.lineNo = line;
	}

	@Override
	public void accept(ast.Visitor v) {
		v.visit(this);
		return;
	}
}

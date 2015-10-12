package ast;

public class Sum {
	static ast.mainClass.MainClass sum = new ast.mainClass.MainClass(
			"Sum", "a", new ast.stm.Print(new ast.exp.Call(
					new ast.exp.NewObject("Doit"), "doit",
					new util.Flist<ast.exp.T>().addAll(new ast.exp.Num(101)))));

	// // class "Fac"
	static ast.classs.Class doit1 = new ast.classs.Class(
			"Doit",
			null,
			new util.Flist<ast.dec.T>().addAll(),
			new util.Flist<ast.method.T>().addAll(new ast.method.Method(
					new ast.type.Int(),
					"doit",
					new util.Flist<ast.dec.T>().addAll(new ast.dec.Dec(
							new ast.type.Int(), "n")),
					new util.Flist<ast.dec.T>().addAll(new ast.dec.Dec(
							new ast.type.Int(), "sum") , new ast.dec.Dec(
									new ast.type.Int(), "i")),
					new util.Flist<ast.stm.T>()
							.addAll(new ast.stm.Assign("i",
											new ast.exp.Num(0)),
									new ast.stm.Assign("sum",
											new ast.exp.Num(0)),
									new ast.stm.While(
									new ast.exp.Lt(new ast.exp.Id("i"),
											new ast.exp.Id("n")),
									new ast.stm.Assign("sum",
											new ast.exp.Add(new ast.exp.Id("sum"),new ast.exp.Id("i")))
									)),
					new ast.exp.Id("sum"))));

	// program
	public static ast.program.Program prog = new ast.program.Program(sum,
			new util.Flist<ast.classs.T>().addAll(doit1));

}

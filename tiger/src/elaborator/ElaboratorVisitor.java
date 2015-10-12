package elaborator;

import java.util.Iterator;

import ast.exp.Paren;
import ast.type.Boolean;
import ast.type.IntArray;

public class ElaboratorVisitor implements ast.Visitor {
	public ClassTable classTable; // symbol table for class
	public MethodTable methodTable; // symbol table for each method
	public String currentClass; // the class name being elaborated
	public ast.type.T type; // type of the expression being elaborated
	java.util.Hashtable<String , Integer> unuse = new java.util.Hashtable<String , Integer>();
	java.util.LinkedList<String> useid = new java.util.LinkedList<String>();

	public ElaboratorVisitor() {
		this.classTable = new ClassTable();
		this.methodTable = new MethodTable();
		this.currentClass = null;
		this.type = null;
	}

	private void error() {
		System.out.println("type mismatch");
	//	System.exit(1);
	}

	// /////////////////////////////////////////////////////
	// expressions
	@Override
	public void visit(ast.exp.Add e) {
		e.left.accept(this);
		ast.type.T leftty = this.type;
		e.right.accept(this);
		if(leftty == null ||this.type == null ||!this.type.toString().equals(leftty.toString())||!leftty.toString().equals("@int[]") && !leftty.toString().equals("@int"))
			System.out.println("line : "+e.lineNo+"  the two types between Add is not int or int[]");
		this.type = new ast.type.Int();
		return;
	}

	@Override
	public void visit(ast.exp.And e) {
		e.left.accept(this);
		ast.type.T leftty = this.type;
		e.right.accept(this);
		if(leftty == null ||this.type == null ||!this.type.toString().equals(leftty.toString())||!leftty.toString().equals("@boolean") && !leftty.toString().equals("@boolean"))
			System.out.println("line : "+e.lineNo+"  the two types between And is not boolean");
		this.type = new ast.type.Boolean();
		return;
	}

	@Override
	public void visit(ast.exp.ArraySelect e) {
		e.array.accept(this);
		e.index.accept(this);
		return;
	}

	@Override
	public void visit(ast.exp.Call e) {
		ast.type.T leftty;
		ast.type.Class ty = null;

		e.exp.accept(this);
		leftty = this.type;
		if (leftty instanceof ast.type.Class) {
			ty = (ast.type.Class) leftty;
			e.type = ty.id;
		} else
			{
				System.out.println("line:  " + e.lineNo +"  illegal object's founction call");
				return;
			}
		MethodType mty = this.classTable.getm(ty.id, e.id);  
		java.util.LinkedList<ast.type.T> argsty = new java.util.LinkedList<ast.type.T>();
		for (ast.exp.T a : e.args) {
			a.accept(this);
			argsty.addLast(this.type);
		}
		if(mty == null)
		{
			return;
		}
		if ( mty != null && mty.argsType.size() != argsty.size())
		{
			error();
			return ;
		}
		for (int i = 0; i < argsty.size(); i++) {
			ast.dec.Dec dec = (ast.dec.Dec) mty.argsType.get(i);
			if(argsty.get(i) == null)
			{
				System.out.println("line:  " + e.lineNo + "  the type of arg in call is not right");
				return;
			}	
			String father = argsty.get(i).toString();
			if(father != null && (father.equals("@boolean") || father.equals("@int") || father.equals("@int[]"))){
			}
			else
			{
				while(father != null && !dec.type.toString().equals(father))
					father = this.classTable.get(father).extendss;
				if(father != null && dec.type.toString().equals(father))
					argsty.set(i, new ast.type.Class(father));
			}
			if(father != null && dec.type.toString().equals(father))
				;
			else
				System.out.println("line:  " + e.lineNo + "  the type of arg in call is not right");
		}
		this.type = mty.retType;
		e.at = argsty;
		e.rt = this.type;
		return;
	}

	@Override
	public void visit(ast.exp.False e) {
		this.type = new ast.type.Boolean();
		return;
	}

	@Override
	public void visit(ast.exp.Id e) {
		// first look up the id in method table
		ast.type.T type = this.methodTable.get(e.id);
		// if search failed, then s.id must be a class field.
		if(type != null)
			if(!useid.contains(e.id))
				useid.add(e.id);
		if (type == null) {
			type = this.classTable.get(this.currentClass, e.id);
			// mark this id as a field id, this fact will be
			// useful in later phase.
			if(!useid.contains(e.id))
				useid.add(e.id);
			e.isField = true;
		}
		if (type == null)
			System.out.println("line : "+e.lineNo+"  the variable is  " +e.id +"  undefined!");
		this.type = type;
		// record this type on this node for future use.
		e.type = type;
		return;
	}

	@Override
	public void visit(ast.exp.Length e) {
		e.array.accept(this);
		if(this.classTable.get(this.currentClass,((ast.exp.Id)e.array).id) == null && this.methodTable.get(((ast.exp.Id)e.array).id) == null)
			System.out.println("line : "+e.lineNo+"  the variable  " +((ast.exp.Id)e.array).id +"  undefined!");   //!!!!!!!!!!!
		this.type = new ast.type.Int();
		return;
	}

	@Override
	public void visit(ast.exp.Lt e) {
		e.left.accept(this);
		ast.type.T ty = this.type;
		e.right.accept(this);
		if(ty == null ||this.type == null||!this.type.toString().equals(ty.toString())|| !ty.toString().equals("@int[]") && !ty.toString().equals("@int"))
			System.out.println("line : "+e.lineNo+"  the two types besides Lt is not int or int[]");
		this.type = new ast.type.Boolean();
		return;
	}

	@Override
	public void visit(ast.exp.NewIntArray e) {
		e.exp.accept(this);
		this.type = new ast.type.IntArray();
		return;
	}

	@Override
	public void visit(ast.exp.NewObject e) {
		this.type = new ast.type.Class(e.id);
		return;
	}

	@Override
	public void visit(ast.exp.Not e) {
		e.exp.accept(this);
		ast.type.T type = this.type;
		if(type == null ||!type.toString().equals(new ast.type.Boolean().toString()))
			System.out.println("line : "+e.lineNo+"  types behind Not is not matched");
		return;
	}

	@Override
	public void visit(ast.exp.Num e) {
		this.type = new ast.type.Int();
		return;
	}

	@Override
	public void visit(ast.exp.Sub e) {
		e.left.accept(this);
		ast.type.T leftty = this.type;
		e.right.accept(this);
		if(leftty == null ||this.type == null ||!this.type.toString().equals(leftty.toString()) || !leftty.toString().equals("@int[]") && !leftty.toString().equals("@int"))
			System.out.println("line : "+e.lineNo+"  the two types besides Sub is not int or int[]");
		this.type = new ast.type.Int();
		return;
	}

	@Override
	public void visit(ast.exp.This e) {
		this.type = new ast.type.Class(this.currentClass);
		return;
	}

	@Override
	public void visit(ast.exp.Times e) {
		e.left.accept(this);
		ast.type.T leftty = this.type;
		e.right.accept(this);
		if(leftty == null ||this.type == null ||!this.type.toString().equals(leftty.toString())|| !leftty.toString().equals("@int[]") && !leftty.toString().equals("@int"))
			System.out.println("line : "+e.lineNo+"  the two types besides Times is not int or int[]");
		this.type = new ast.type.Int();
		return;
	}

	@Override
	public void visit(ast.exp.True e) {
		this.type = new ast.type.Boolean();
		return ;
	}

	// statements
	@Override
	public void visit(ast.stm.Assign s) {
		// first look up the id in method table
		ast.type.T type = this.methodTable.get(s.id);
		// if search failed, then s.id must
		if (type == null)
			type = this.classTable.get(this.currentClass, s.id);
		boolean flag = false;
		if (type == null)
		{
			flag = true;
			System.out.println("line:  " + s.lineNo + "  no such id: "+ s.id);
		}
		s.exp.accept(this);
		if(!useid.contains(s.id))
			useid.add(s.id);
		s.type = type;
		if(!flag && this.type != null && type != null && !this.type.toString().equals(type.toString()))
		{
			System.out.println("line : "+s.lineNo+"  the two types of assign is mismatch!");
		}
		return;
	}

	@Override
	public void visit(ast.stm.AssignArray s) {
		ast.type.T type = this.methodTable.get(s.id);
		// if search failed, then s.id must
		if (type == null)
			type = this.classTable.get(this.currentClass, s.id);
		if (type == null)
			System.out.println("line:  " + s.lineNo + "  no such id: "+ s.id);
		s.exp.accept(this);
		if(!useid.contains(s.id))
			useid.add(s.id);
		s.index.accept(this);
		s.type = type;
		if(this.type != null && type != null && !this.type.toString().equals(type.toString()) && type.toString() != "@int[]")
		{
			System.out.println("line : "+s.lineNo+"  the two types of assign is mismatch!");
		}
		return;
	}

	@Override
	public void visit(ast.stm.Block s) {
		for (ast.stm.T s1 : s.stms)
			s1.accept(this);
	}

	@Override
	public void visit(ast.stm.If s) {
		s.condition.accept(this);
		if (this.type != null && !this.type.toString().equals("@boolean"))
			System.out.println("line:  " + s.lineNo + "  condition is not a boolean value");
		s.thenn.accept(this);
		s.elsee.accept(this);
		return;
	}

	@Override
	public void visit(ast.stm.Print s) {
		s.exp.accept(this);
		if (this.type != null && !this.type.toString().equals("@int"))
			System.out.println("line:  " + s.lineNo + "  can't print a int value");//what kind of error!!!!
		return;
	}

	@Override
	public void visit(ast.stm.While s) {
		s.condition.accept(this);
		if (this.type != null && !this.type.toString().equals("@boolean"))
			System.out.println("line:  " + s.lineNo + "  condition is not a boolean value");
		s.body.accept(this);
		return;
	}

	// type
	@Override
	public void visit(ast.type.Boolean t) {
		if(t.getNum() != -1)
			System.out.println("type is incorrected!");
		return;
	}

	@Override
	public void visit(ast.type.Class t) {
		if(t.getNum() != 2)
			System.out.println("type is incorrected!");
//		if(this.classTable.get(t.id) == null)
//			System.out.println("Class name is undefined!");
		return;
	}

	@Override
	public void visit(ast.type.Int t) {
		//System.out.println("aaaa");
		if(t.getNum() != 0)
			System.out.println("type is incorrected!");
		return;
	}

	@Override
	public void visit(ast.type.IntArray t) {
		if(t.getNum() != 1)
			System.out.println("type is incorrected!");
		return;
	}

	// dec
	@Override
	public void visit(ast.dec.Dec d) {           
		//创建表的时候已经进行了检查，所以此处不用检查变量名，只需检查变量类型。
		d.type.accept(this);
		return ;
	}

	// method
	@Override
	public void visit(ast.method.Method m) {
		// construct the method table
		this.methodTable.put(m.formals, m.locals);
		for (ast.dec.T dec : m.locals) {
			ast.dec.Dec d = (ast.dec.Dec) dec;
			unuse.put(d.id, d.lineNo);
		}
		if (control.Control.elabMethodTable)
		{
			System.out.println("in method :  "+m.id);
			this.methodTable.dump();
			System.out.println();
			System.out.println();
		}
		for (ast.stm.T s : m.stms)
			s.accept(this);
		m.retExp.accept(this);
		this.methodTable.clear();
		return;
	}

	// class
	@Override
	public void visit(ast.classs.Class c) {
		this.currentClass = c.id;
		for(ast.dec.T d : c.decs){
			d.accept(this);
		}
		for (ast.method.T m : c.methods) {
			m.accept(this);
		}
		return;
	}

	// main class
	@Override
	public void visit(ast.mainClass.MainClass c) {
		this.currentClass = c.id;
		// "main" has an argument "arg" of type "String[]", but
		// one has no chance to use it. So it's safe to skip it...

		c.stm.accept(this);
		return;
	}

	// ////////////////////////////////////////////////////////
	// step 1: build class table
	// class table for Main class
	private void buildMainClass(ast.mainClass.MainClass main) {
		this.classTable.put(main.id, new ClassBinding(null , main.lineNo));
	}

	// class table for normal classes
	private void buildClass(ast.classs.Class c) {
		this.classTable.put(c.id, new ClassBinding(c.extendss , c.lineNo));
		for (ast.dec.T dec : c.decs) {
			ast.dec.Dec d = (ast.dec.Dec) dec;
			unuse.put(d.id, d.lineNo);
			this.classTable.put(c.id, d.id, d.type);
		}
		for (ast.method.T method : c.methods) {
			ast.method.Method m = (ast.method.Method) method;
			this.classTable.put(c.id, m.id,
					new MethodType(m.retType, m.formals , m.lineNo));
		}
	}

	// step 1: end
	// ///////////////////////////////////////////////////

	// program
	@Override
	public void visit(ast.program.Program p) {
		// ////////////////////////////////////////////////
		// step 1: build a symbol table for class (the class table)
		// a class table is a mapping from class names to class bindings
		// classTable: className -> ClassBinding{extends, fields, methods}
		buildMainClass((ast.mainClass.MainClass) p.mainClass);
		for (ast.classs.T c : p.classes) {
			buildClass((ast.classs.Class) c);
		}

		// we can double check that the class table is OK!
		if (control.Control.elabClassTable) {
			this.classTable.dump();
	//		this.methodTable.dump();
		}

		// ////////////////////////////////////////////////
		// step 2: elaborate each class in turn, under the class table
		// built above.
		p.mainClass.accept(this);
		for (ast.classs.T c : p.classes) {
			c.accept(this);
		}
		for (int i = 0 ; i < useid.size() ; i++) {
			unuse.remove(useid.get(i));
		}
		String key ;
		for(Iterator it = unuse.keySet().iterator() ; it.hasNext();)
		{
			  key = (String) it.next();
			  System.out.println("Warning: variable "+key+" declared at line "+ unuse.get(key) +" never used");
		}
		System.out.println("Compile  Complete!");
	}

	@Override
	public void visit(Paren paren) {
		// TODO Auto-generated method stub
		paren.exp.accept(this);
	}
}
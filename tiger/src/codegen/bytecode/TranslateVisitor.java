package codegen.bytecode;

import java.util.Hashtable;
import java.util.LinkedList;

import ast.exp.Paren;

import util.Label;

// Given a Java ast, translate it into Java bytecode.

public class TranslateVisitor implements ast.Visitor {
	private String classId;
	private int index;
	boolean flag_f = false;
	private Hashtable<String, Integer> indexTable;
	Hashtable<String, Integer> fieldTable;
	private codegen.bytecode.type.T type; // type after translation
	private codegen.bytecode.dec.T dec;
	private LinkedList<codegen.bytecode.stm.T> stms;
	private codegen.bytecode.method.T method;
	private codegen.bytecode.classs.T classs;
	private codegen.bytecode.mainClass.T mainClass;
	public codegen.bytecode.program.T program;

	public TranslateVisitor() {
		this.classId = null;
		this.indexTable = null;
		this.type = null;
		this.dec = null;
		this.stms = new java.util.LinkedList<codegen.bytecode.stm.T>();
		this.method = null;
		this.classs = null;
		this.mainClass = null;
		this.program = null;
	}

	private void emit(codegen.bytecode.stm.T s) {
		this.stms.add(s);
	}

	// /////////////////////////////////////////////////////
	// expressions
	@Override
	public void visit(ast.exp.Add e) {
		e.left.accept(this);
		e.right.accept(this);
		emit(new codegen.bytecode.stm.Iadd());
		return;
	}

	@Override
	public void visit(ast.exp.And e) {
		e.left.accept(this);
		e.right.accept(this);
		emit(new codegen.bytecode.stm.Iand());
		return;
	}

	@Override
	public void visit(ast.exp.ArraySelect e) {
		e.array.accept(this);
		e.index.accept(this);
		emit(new codegen.bytecode.stm.IAload());
		return;
	}

	@Override
	public void visit(ast.exp.Call e) {
		e.exp.accept(this);
		for (ast.exp.T x : e.args) {
			x.accept(this);
		}
		e.rt.accept(this);
		codegen.bytecode.type.T rt = this.type;
		java.util.LinkedList<codegen.bytecode.type.T> at = new java.util.LinkedList<codegen.bytecode.type.T>();
		for (ast.type.T t : e.at) {
			t.accept(this);
			at.add(this.type);
		}
		emit(new codegen.bytecode.stm.Invokevirtual(e.id, e.type, at, rt));
		return;
	}

	@Override
	public void visit(ast.exp.False e) {
		emit(new codegen.bytecode.stm.Ldc(0));
		return;
	}

	@Override
	public void visit(ast.exp.Id e) {
		if (!this.fieldTable.containsKey(e.id)) {
			if (this.indexTable.get(e.id) != null) {
				int index = this.indexTable.get(e.id);
				ast.type.T type = e.type;
				if (type.getNum() > 0)// a reference
					emit(new codegen.bytecode.stm.Aload(index));
				else
					emit(new codegen.bytecode.stm.Iload(index));
			} else {
				emit(new codegen.bytecode.stm.Aload(0));
				if (e.type.toString().equals("@int"))
					emit(new codegen.bytecode.stm.GetField(this.classId, e.id,
							"I"));
				else if (e.type.toString().equals("@boolean"))
					emit(new codegen.bytecode.stm.GetField(this.classId, e.id,
							"I"));
				else if (e.type.toString().equals("@int[]"))
					emit(new codegen.bytecode.stm.GetField(this.classId, e.id,
							"[I"));
				else
					emit(new codegen.bytecode.stm.GetField(this.classId, e.id,
							"L" + e.type + ";"));
			}
		} else {
			emit(new codegen.bytecode.stm.Aload(0));
			if (e.type.toString().equals("@int"))
				emit(new codegen.bytecode.stm.GetField(this.classId, e.id, "I"));
			else if (e.type.toString().equals("@boolean"))
				emit(new codegen.bytecode.stm.GetField(this.classId, e.id, "I"));
			else if (e.type.toString().equals("@int[]"))
				emit(new codegen.bytecode.stm.GetField(this.classId, e.id, "[I"));
			else
				emit(new codegen.bytecode.stm.GetField(this.classId, e.id, "L"
						+ e.type + ";"));
		}
		return;
	}

	@Override
	public void visit(ast.exp.Length e) {
		e.array.accept(this);
		emit(new codegen.bytecode.stm.ArrayLength());
		return;
	}

	@Override
	public void visit(ast.exp.Lt e) {
		Label tl = new Label(), fl = new Label(), el = new Label();
		e.left.accept(this);
		e.right.accept(this);
		emit(new codegen.bytecode.stm.Ificmplt(tl));
		emit(new codegen.bytecode.stm.Label(fl));
		emit(new codegen.bytecode.stm.Ldc(0));
		emit(new codegen.bytecode.stm.Goto(el));
		emit(new codegen.bytecode.stm.Label(tl));
		emit(new codegen.bytecode.stm.Ldc(1));
		emit(new codegen.bytecode.stm.Goto(el));
		emit(new codegen.bytecode.stm.Label(el));
		return;
	}

	@Override
	public void visit(ast.exp.NewIntArray e) // @_@
	{
		e.exp.accept(this);
		emit(new codegen.bytecode.stm.NewArray());
		return;
	}

	@Override
	public void visit(ast.exp.NewObject e) {
		emit(new codegen.bytecode.stm.New(e.id));
		return;
	}

	@Override
	public void visit(ast.exp.Not e) // @_@
	{
		Label tl = new Label(), fl = new Label(), el = new Label();
		e.exp.accept(this);
		emit(new codegen.bytecode.stm.Ifne(tl));
		emit(new codegen.bytecode.stm.Goto(fl));
		emit(new codegen.bytecode.stm.Label(fl));
		emit(new codegen.bytecode.stm.Ldc(1));
		emit(new codegen.bytecode.stm.Goto(el));
		emit(new codegen.bytecode.stm.Label(tl));
		emit(new codegen.bytecode.stm.Ldc(0));
		emit(new codegen.bytecode.stm.Goto(el));
		emit(new codegen.bytecode.stm.Label(el));
	}

	@Override
	public void visit(ast.exp.Num e) {
		emit(new codegen.bytecode.stm.Ldc(e.num));
		return;
	}

	@Override
	public void visit(ast.exp.Sub e) {
		e.left.accept(this);
		e.right.accept(this);
		emit(new codegen.bytecode.stm.Isub());
		return;
	}

	@Override
	public void visit(ast.exp.This e) {
		emit(new codegen.bytecode.stm.Aload(0));
		return;
	}

	@Override
	public void visit(ast.exp.Times e) {
		e.left.accept(this);
		e.right.accept(this);
		emit(new codegen.bytecode.stm.Imul());
		return;
	}

	@Override
	public void visit(ast.exp.True e) {
		emit(new codegen.bytecode.stm.Ldc(1));
		return;
	}

	// statements
	@Override
	public void visit(ast.stm.Assign s) {
		if (!this.fieldTable.containsKey(s.id)) {
			if (this.indexTable.get(s.id) != null) {
				s.exp.accept(this);
				int index = this.indexTable.get(s.id);
				ast.type.T type = s.type;
				if (type.getNum() > 0)
					emit(new codegen.bytecode.stm.Astore(index));
				else
					emit(new codegen.bytecode.stm.Istore(index));
			} else {
				emit(new codegen.bytecode.stm.Aload(0));
				s.exp.accept(this);
				if (s.type.toString().equals("@int"))
					emit(new codegen.bytecode.stm.PutField(this.classId, s.id,
							"I"));
				else if (s.type.toString().equals("@boolean"))
					emit(new codegen.bytecode.stm.PutField(this.classId, s.id,
							"I"));
				else if (s.type.toString().equals("@int[]"))
					emit(new codegen.bytecode.stm.PutField(this.classId, s.id,
							"[I"));
				else
					emit(new codegen.bytecode.stm.PutField(this.classId, s.id,
							"L" + s.type + ";"));
			}
		} else {
			emit(new codegen.bytecode.stm.Aload(0));
			s.exp.accept(this);
			if (s.type.toString().equals("@int"))
				emit(new codegen.bytecode.stm.PutField(this.classId, s.id, "I"));
			else if (s.type.toString().equals("@boolean"))
				emit(new codegen.bytecode.stm.PutField(this.classId, s.id, "I"));
			else if (s.type.toString().equals("@int[]"))
				emit(new codegen.bytecode.stm.PutField(this.classId, s.id, "[I"));
			else
				emit(new codegen.bytecode.stm.PutField(this.classId, s.id, "L"
						+ s.type + ";"));
		}
		return;
	}

	@Override
	public void visit(ast.stm.AssignArray s) {

		if (!this.fieldTable.containsKey(s.id)) {
			s.index.accept(this);
			s.exp.accept(this);
			int index = this.indexTable.get(s.id);
			ast.type.T type = s.type;
			if (type.getNum() > 0)
				emit(new codegen.bytecode.stm.Astore(index));
			else
				emit(new codegen.bytecode.stm.Istore(index));
		} else {
			emit(new codegen.bytecode.stm.Aload(0));
			emit(new codegen.bytecode.stm.GetField(this.classId, s.id, "[I"));
			s.index.accept(this);
			s.exp.accept(this);
			emit(new codegen.bytecode.stm.IAstore(0));
		}
		return;
	}

	@Override
	public void visit(ast.stm.Block s) {
		for (ast.stm.T m : s.stms)
			m.accept(this);
	}

	@Override
	public void visit(ast.stm.If s) {
		Label tl = new Label(), fl = new Label(), el = new Label();
		s.condition.accept(this);
		emit(new codegen.bytecode.stm.Ifne(tl));
		emit(new codegen.bytecode.stm.Label(fl));
		s.elsee.accept(this);
		emit(new codegen.bytecode.stm.Goto(el));
		emit(new codegen.bytecode.stm.Label(tl));
		s.thenn.accept(this);
		emit(new codegen.bytecode.stm.Goto(el));
		emit(new codegen.bytecode.stm.Label(el));
		return;
	}

	@Override
	public void visit(ast.stm.Print s) {
		s.exp.accept(this);
		emit(new codegen.bytecode.stm.Print());
		return;
	}

	@Override
	public void visit(ast.stm.While s) {
		Label tl = new Label(), fl = new Label(), el = new Label();
		emit(new codegen.bytecode.stm.Label(el));
		s.condition.accept(this);
		emit(new codegen.bytecode.stm.Ifeq(fl));
		emit(new codegen.bytecode.stm.Label(tl));
		s.body.accept(this);
		emit(new codegen.bytecode.stm.Goto(el));
		emit(new codegen.bytecode.stm.Label(fl));
	}

	// type
	@Override
	public void visit(ast.type.Boolean t) {
		this.type = new codegen.bytecode.type.Boolean();
	}

	@Override
	public void visit(ast.type.Class t) {
		this.type = new codegen.bytecode.type.Class(t.id);
	}

	@Override
	public void visit(ast.type.Int t) {
		this.type = new codegen.bytecode.type.Int();
	}

	@Override
	public void visit(ast.type.IntArray t) {
		this.type = new codegen.bytecode.type.IntArray();
	}

	// dec
	@Override
	public void visit(ast.dec.Dec d) {
		if (flag_f) {
			this.fieldTable.put(d.id, 1);
		}
		d.type.accept(this);
		this.dec = new codegen.bytecode.dec.Dec(this.type, d.id);
		this.indexTable.put(d.id, index++);
		return;
	}

	// method
	@Override
	public void visit(ast.method.Method m) {
		// record, in a hash table, each var's index
		// this index will be used in the load store operation
		this.index = 1;
		this.indexTable = new java.util.Hashtable<String, Integer>();

		m.retType.accept(this);
		codegen.bytecode.type.T newRetType = this.type;
		java.util.LinkedList<codegen.bytecode.dec.T> newFormals = new java.util.LinkedList<codegen.bytecode.dec.T>();
		for (ast.dec.T d : m.formals) {
			d.accept(this);
			newFormals.add(this.dec);
		}
		java.util.LinkedList<codegen.bytecode.dec.T> locals = new java.util.LinkedList<codegen.bytecode.dec.T>();
		for (ast.dec.T d : m.locals) {
			d.accept(this);
			locals.add(this.dec);
		}
		this.stms = new java.util.LinkedList<codegen.bytecode.stm.T>();
		for (ast.stm.T s : m.stms) {
			s.accept(this);
		}

		// return statement is specially treated
		m.retExp.accept(this);

		if (m.retType.getNum() > 0)
			emit(new codegen.bytecode.stm.Areturn());
		else
			emit(new codegen.bytecode.stm.Ireturn());

		this.method = new codegen.bytecode.method.Method(newRetType, m.id,
				this.classId, newFormals, locals, this.stms, 0, this.index);

		return;
	}

	// class
	@Override
	public void visit(ast.classs.Class c) {
		this.classId = c.id;

		// this.index = 1;
		this.indexTable = new java.util.Hashtable<String, Integer>();

		java.util.LinkedList<codegen.bytecode.dec.T> newDecs = new java.util.LinkedList<codegen.bytecode.dec.T>();
		flag_f = true;
		this.fieldTable = new Hashtable<String, Integer>();
		for (ast.dec.T dec : c.decs) {
			dec.accept(this);
			newDecs.add(this.dec);
		}
		flag_f = false;
		java.util.LinkedList<codegen.bytecode.method.T> newMethods = new java.util.LinkedList<codegen.bytecode.method.T>();
		for (ast.method.T m : c.methods) {
			m.accept(this);
			newMethods.add(this.method);
		}
		this.classs = new codegen.bytecode.classs.Class(c.id, c.extendss,
				newDecs, newMethods);
		return;
	}

	// main class
	@Override
	public void visit(ast.mainClass.MainClass c) {
		c.stm.accept(this);
		this.mainClass = new codegen.bytecode.mainClass.MainClass(c.id, c.arg,
				this.stms);
		this.stms = new java.util.LinkedList<codegen.bytecode.stm.T>();
		return;
	}

	// program
	@Override
	public void visit(ast.program.Program p) {
		// do translations
		p.mainClass.accept(this);

		java.util.LinkedList<codegen.bytecode.classs.T> newClasses = new java.util.LinkedList<codegen.bytecode.classs.T>();
		for (ast.classs.T classs : p.classes) {
			classs.accept(this);
			newClasses.add(this.classs);
		}
		this.program = new codegen.bytecode.program.Program(this.mainClass,
				newClasses);
		return;
	}

	@Override
	public void visit(Paren paren) {
		// TODO Auto-generated method stub
		paren.exp.accept(this);
	}
}

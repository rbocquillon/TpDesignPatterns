package fr.imtld.ilog;

public class Visitor {
	public void visit(Node nod) {
		System.out.printf("%d, ", nod.getData());
	}
}

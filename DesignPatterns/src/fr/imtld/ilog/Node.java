package fr.imtld.ilog;

/**
 * Un noeud pour un Arbre Binaire de Recherche.
 */
public class Node {
	protected int _iData;
	protected Node _left, _right;

	public Node(int iData) {
		_iData = iData;
		_left = _right = null;
	}

	public int getData() {
		return _iData;
	}

	/**
	 * Ajouter le noeud n dans l'arbre dont ce noeud est racine.
	 * 
	 * @param n Le noeud à ajouter.
	 * @return L'arbre modifié.
	 */
	public Node add(Node n) {
		if (n != null) {
			int iData = n.getData();
			if (iData < _iData) { // insertion à gauche
				if (_left == null)
					_left = n;
				else
					_left = _left.add(n);
			} else if (iData > _iData) { // insertion à droite
				if (_right == null)
					_right = n;
				else
					_right = _right.add(n);
			}
		}
		return this;
	}

	/**
	 * Calculer la hauteur de l'arbre dont ce noeud est racine.
	 * 
	 * @return Hauteur de l'arbre (0 pour une feuille).
	 */
	public int height() {
		if (_left == null && _right == null)
			return 0;
		else if (_left == null)
			return 1 + _right.height();
		else if (_right == null)
			return 1 + _left.height();
		else
			return 1 + Math.max(_left.height(), _right.height());
	}

	/**
	 * Calculer la taille de l'arbre dont ce noeud est racine.
	 * 
	 * @return Taille de l'arbre (1 pour une feuille).
	 */
	public int size() {
		int iSize = 1;
		if (_left != null)
			iSize += _left.size();
		if (_right != null)
			iSize += _right.size();
		return iSize;
	}

	public void accept(Visitor vis) {
		if (_left != null)
			_left.accept(vis);
		vis.visit(this);
		if (_right != null)
			_right.accept(vis);
	}

	protected void add(int n) {
		add(new Node(n));
	}

	public static void main(String[] args) {
		Visitor vis = new Visitor();
//		Node nodRoot = NULL_NODE;
//		nodRoot.accept(vis);
//		System.out.printf("NullNode : h=%d, sz=%d\n",
//				nodRoot.height(),
//				nodRoot.size()
//		);
//		nodRoot.addNode(10);
		Node nodRoot = new Node(10);
		System.out.printf("Leaf : h=%d, sz=%d\n", nodRoot.height(), nodRoot.size());
		nodRoot.add(3);
		nodRoot.add(11);
		nodRoot.add(2);
		nodRoot.add(13);
		nodRoot.add(4);
		nodRoot.add(18);
		nodRoot.add(5);
		nodRoot.add(19);
		nodRoot.add(1);
		nodRoot.add(12);
		nodRoot.add(9);
		nodRoot.add(17);
		nodRoot.add(8);
		nodRoot.add(14);
		nodRoot.add(6);
		nodRoot.add(16);
		nodRoot.add(7);
		nodRoot.add(15);
		System.out.printf("Tree : h=%d, sz=%d\n", nodRoot.height(), nodRoot.size());
		nodRoot.accept(vis);
	}
}

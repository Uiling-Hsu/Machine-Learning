package dataBean;

public class Node {
	private Node leftNode;
	private Node rightNode;
	private Function function;
	
	public Node(){
		leftNode = new Node();
		rightNode = new Node();
		function = new Function();
	}
	public Node getLeftNode() {
		return leftNode;
	}
	public void setLeftNode(Node leftNode) {
		this.leftNode = leftNode;
	}
	public Node getRightNode() {
		return rightNode;
	}
	public void setRightNode(Node rightNode) {
		this.rightNode = rightNode;
	}
	public Function getFunction() {
		return function;
	}
	public void setFunction(Function function) {
		this.function = function;
	}
	
	
	
}

public class Node {
    MultiplicationThread key;
    Node left, right;

    public Node(MultiplicationThread num) {
        key = num;
        left = right = null;
    }
}

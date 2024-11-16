public class BinaryTree {
    Node root;

    public BinaryTree() {
        root = null;
    }

    //insert method that relies on the recursive BST insert method
    public void insert(MultiplicationThread key) throws Exception {
        root = insertRec(root, key);
    }

    private Node insertRec(Node root, MultiplicationThread key) throws Exception {
        if (root == null) {
            root = new Node(key);
            return root;
        }
        if (key.call().length < root.key.call().length) {
            root.left = insertRec(root.left, key);
        } else if (key.call().length > root.key.call().length) {
            root.right = insertRec(root.right, key);
        }
        return root;
    }
}

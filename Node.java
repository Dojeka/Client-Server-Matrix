public class Node {
    MultiplicationThread key;
    Node left, right;

    public Node(MultiplicationThread num){
        key = num;
        left = right = null;
    }

    public class BinaryTree {
        Node root;
        public BinaryTree(){
            root = null;
        }

        public void insert(MultiplicationThread key) throws Exception {
            root = insertRec(root, key);
        }
        private Node insertRec(Node root, MultiplicationThread key) throws Exception {
            if(root == null){
                root =  new Node(key);
                return root;
            }
            if(key.call().length < root.key.call().length){
                root.left = insertRec(root.left, key);
            }else if(key.call().length > root.key.call().length){
                root.right = insertRec(root.right, key);
            }
            return root;
        }
    }
}

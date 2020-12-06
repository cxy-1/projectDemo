package structure.tree;

public class RedBlackTree<T extends Comparable<T>> {




    class Node {
        T t;
        Node parent;
        Node left;
        Node right;
        Color color;
        public Node(T t, Node parent,Node left,Node right,
                    Color color) {
            super();
            this.t = t;
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.color = color;
        }

        public void setColor(Color color) {
            this.color=color;
        }

    }

    private enum Color{
        Red,
        Black,
    }

    Node root;
    Node nil=new Node(null,null,null,null,Color.Black);

    /*
     * 获取祖父节点
     */
    public Node getGrandParent(Node node) {
        if(node.parent==null) {
            return null;
        }
        return node.parent.parent;
    }

    /*
     * 获取当前节点的叔节点
     */
    public Node getUncle(Node node) {
        Node grandParent=getGrandParent(node);
        if(grandParent==null) {
            return null;
        }
        if(node.parent==grandParent.left) {
            return grandParent.right;
        }else {
            return grandParent.left;
        }
    }
    /*                    x
     *
     *               a       y
     *
     *                     b     z
     */
    //左旋
    public Node leftRotate(Node x) {
        Node y=x.right;
        Node b=y.left;
        x.right=b;
        y.left=x;

        x.parent=y;
        if(b!=null) {
            b.parent=x;

        }

        return y;
    }
    /*            x
     *
     *     y        a
     *
     * z     b
     */
    //右旋
    public Node rightRotate(Node x) {
        Node y=x.left;
        Node b=y.right;

        x.left=b;
        y.right=x;

        x.parent=y;
        if(b!=null) {
            b.parent=x;

        }


        return y;
    }

    public void addNode(T t) {
        if(t!=null) {
            addNode(root,t);
        }
    }

    public void addNode(Node current,T t) {

        Node node=current;
        Node parent = null;
        boolean isLeft=true;
        while(node!=null) {
            if(t.compareTo(node.t)<0) {
                isLeft=true;
                parent=node;
                node=node.left;
            }else if(t.compareTo(node.t)>0) {
                isLeft=false;
                parent=node;
                node=node.right;
            }

        }
        if(node==null&&parent==null) {
            root=new Node(t,null,null,null,Color.Black);
        }else if(node==null&&parent!=null) {
            Node newNode=new Node(t,null,null,null,Color.Red);
            newNode.parent=parent;
            if(isLeft) {
                parent.left=newNode;
            }else {
                parent.right=newNode;
            }
            setBalance(newNode);
        }

    }


    //使红黑树保持平衡  新增平衡调整
    public void setBalance(Node node) {
        Node current=node;
        Node grandparent;
        Node uncle;

        while(current.parent!=null&&current.parent.color==Color.Red) {
            grandparent=getGrandParent(current);
            uncle=getUncle(current);
            //父节点是祖父节点的左孩子L
            if(grandparent.left==current.parent) {
                //叔节点存在  且为红
                if(uncle!=null&&uncle.color==Color.Red) {
                    uncle.color=Color.Black;
                    current.parent.color=Color.Black;
                    grandparent.color=Color.Red;
                    current=grandparent;
                }else {//叔节点不存在或者为黑   LL //只旋转一次 要变色
                    if(current.parent.left==current) {
                        Node gpp=grandparent.parent;//祖父节点的父节点
                        grandparent.color=Color.Red;
                        current.color=Color.Red;
                        current.parent.color=Color.Black;
                        if(gpp!=null&&gpp.left==grandparent) {
                            gpp.left=rightRotate(grandparent);

                        }else if(gpp!=null&&gpp.right==grandparent) {

                            gpp.right=rightRotate(grandparent);
                        }else {
                            root= rightRotate(grandparent);

                        }

                    }else {//LR
                        Node gpp=grandparent.parent;//祖父节点的父节点
                        grandparent.left=leftRotate(current.parent);
                        //先变色在旋转
                        grandparent.left.color=Color.Black;
                        current.color=Color.Red;
                        grandparent.color=Color.Red;

                        if(gpp!=null&&gpp.left==grandparent) {

                            gpp.left=rightRotate(grandparent);
                        }else if(gpp!=null&&gpp.right==grandparent) {
                            gpp.right=rightRotate(grandparent);

                        }else {
                            root=rightRotate(grandparent);
                        }
                    }
                }
            }else {//R
                //叔节点存在  且为红
                if(uncle!=null&&uncle.color==Color.Red) {
                    uncle.color=Color.Black;
                    current.parent.color=Color.Black;
                    grandparent.color=Color.Red;
                    current=grandparent;
                }else {//叔节点不存在或者为黑
                    if(current.parent.left==current) {//RL
                        Node gpp=grandparent.parent;//祖父节点的父节点
                        grandparent.right=rightRotate(current.parent);
                        //先变色在旋转
                        grandparent.right.color=Color.Black;
                        current.color=Color.Red;
                        grandparent.color=Color.Red;
                        if(gpp!=null&&gpp.left==grandparent) {
                            gpp.left=leftRotate(grandparent);
                        }else if(gpp!=null&&gpp.right==grandparent) {
                            gpp.right=leftRotate(grandparent);
                        }else {
                            root= leftRotate(grandparent);
                        }

                    }else {//RR
                        Node gpp=grandparent.parent;//祖父节点的父节点
                        grandparent.color=Color.Red;
                        current.color=Color.Red;
                        current.parent.color=Color.Black;
                        if(gpp!=null&&gpp.left==grandparent) {

                            gpp.left=leftRotate(grandparent);

                        }else if(gpp!=null&&gpp.right==grandparent) {

                            gpp.right=leftRotate(grandparent);
                        }else {
                            root= leftRotate(grandparent);

                        }
                    }
                }

            }
        }


        root.color=Color.Black;


    }



    public Node getReplaceNode(Node node) {
        if(node.left!=null&&node.right!=null) {//获取前驱节点
            Node tmp=node.left;
            while(tmp.right!=null) {
                tmp=tmp.right;
            }
            return tmp;
        }

        if(node.left==null&&node.right==null) {
            return null;
        }
        return node.left==null?node.right:node.left;
    }


    public void delete(T t) {
        /*
         * 找到被删除节点
         */
        Node delNode = null,replaceNode = null;//被删节点　　替代节点
        Node tmp=root;
        while(tmp!=null) {
            if(t.compareTo(tmp.t)<0) {
                tmp=tmp.left;
            }else if(t.compareTo(tmp.t)>0) {
                tmp=tmp.right;
            }else {
                delNode=tmp;
                break;
            }
        }

        if(null!=delNode&&delNode.color==Color.Red) {//待删除节点是红色

            if(delNode.left!=null&&delNode.right!=null) {
                replaceNode=getReplaceNode(delNode);
                delBalace(replaceNode);
                if(delNode.left!=replaceNode) {//不等于删除替换节点，等于时，替换掉之后相当于删除
                    deleteNode(delNode.left,replaceNode.t);

                }
                if(delNode==delNode.parent.left) {
                    delNode.parent.left=replaceNode;
                    if(replaceNode!=delNode.left) {
                        replaceNode.left=delNode.left;

                    }
                    replaceNode.right=delNode.right;
                }else {
                    delNode.parent.right=replaceNode;
                    if(replaceNode!=delNode.left) {
                        replaceNode.left=delNode.left;
                    }
                    replaceNode.right=delNode.right;
                }

            }else {
                deleteNode(root,t);
            }
        }else {
            replaceNode=getReplaceNode(delNode);
            if(replaceNode==null) {
                if(delNode!=root) {
                    delBalace(delNode);
                }
                deleteNode(root,delNode.t);

            }else if(replaceNode.color==Color.Black) {
                delBalace(replaceNode);
                deleteNode(delNode,replaceNode.t);
                replaceNode.color=delNode.color;
                if(delNode==root) {
                    return;
                }
                if(delNode==delNode.parent.left) {
                    delNode.parent.left=replaceNode;
                    if(replaceNode!=delNode.left) {
                        replaceNode.left=delNode.left;
                    }
                    replaceNode.right=delNode.right;
                }else {
                    delNode.parent.right=replaceNode;
                    if(replaceNode==delNode.left) {
                        replaceNode.left=null;

                    }else {
                        replaceNode.left=delNode.left;
                    }
                    replaceNode.right=delNode.right;
                }
            }else {//直接执行删除操作

                replaceNode.color=delNode.color;
                deleteNode(delNode.t);
                if(delNode.parent==null) {//ｒｏｏｔ节点
                    replaceNode.left=delNode.left;
                    replaceNode.right=delNode.right;
                }else {
                    if(delNode==delNode.parent.left) {
                        delNode.parent.left=replaceNode;
                        if(replaceNode==delNode.left) {
                            replaceNode.left=null;

                        }else {
                            replaceNode.left=delNode.left;
                        }
                        replaceNode.right=delNode.right;
                    }else {
                        delNode.parent.right=replaceNode;
                        if(replaceNode==delNode.left) {
                            replaceNode.left=null;

                        }else {
                            replaceNode.left=delNode.left;
                        }
                        replaceNode.right=delNode.right;
                    }
                }

            }
        }


    }



    public void deleteNode(T t) {
        if(t!=null) {
            root=deleteNode(root,t);
        }
    }
    //删除节点
    public Node deleteNode(Node node,T t) {

        if(t.compareTo(node.t)<0) {
            node.left=deleteNode(node.left,t);
        }else if(t.compareTo(node.t)>0) {
            node.right=deleteNode(node.right,t);
        }else {
            if(node.left==null&&node.right==null) {
                return null;
            }

            if(node.left==null&&node.right!=null) {
                return node.right;
            }else if(node.left!=null&&node.right==null) {
                return node.left;
            }

            if(node.left!=null&&node.right!=null) {
                Node preNode=getReplaceNode(node);
                preNode.left=deleteNode(node.left,preNode.t);
                preNode.right=node.right;
                return preNode;
            }
        }
        return node;
    }
    //删除平衡操作
    public void delBalace(Node current) {
        Node parent=current.parent;
        Node gp=parent.parent;
        Node s=parent.left==current?parent.right:parent.left;//兄弟节点
        Node ln=s.left;//兄弟节点左孩子
        Node rn=s.right;//兄弟节点右孩子
        //case 1
        if(s.color==Color.Red) {
            if(current==current.parent.left) {
                /*                      ｐ black                                 s black
                 *                                           左旋 变色
                 *              x black       s red        ---->       p   black       rn   black
                 *
                 *                        ln black  rn black       x     ln red
                 */
                //旋转前先变色，
                s.color=Color.Black;
                ln.color=Color.Red;
                if(gp==null) {
                    root=leftRotate(parent);

                }else if(gp.left==parent) {
                    gp.left=leftRotate(parent);
                }else if(gp.right==parent){
                    gp.right=leftRotate(parent);
                }
            }else {
                /*                    p black　　　　　　　　　　　　　　　　　　　　　　　　　　　ｓ  black
                 *                                         右旋变色
                 *             s  red         x  black     --->                     ln  black    p   black
                 *
                 *    ln black   rn black                                                  rn red   x
                 *
                 */
                s.color=Color.Black;
                rn.color=Color.Red;
                if(gp==null) {
                    root=rightRotate(parent);

                }else if(gp.left==parent) {
                    gp.left=rightRotate(parent);
                }else if(gp.right==parent){
                    gp.right=rightRotate(parent);
                }
            }


        }else {
            if((ln==null&&rn==null&&parent.color==Color.Black)
                    ||(ln.color==Color.Black&&rn.color==Color.Black&&parent.color==Color.Black)) {
                s.color=Color.Red;
                current=parent;
                delBalace(current);//递归调用
            }else if(parent.color==Color.Red&&ln==null&&rn==null){
                /*           p red                                        p black
                 *
                 *      x black     s black      --->             x black     s red
                 */
                s.color=Color.Red;
                parent.color=Color.Black;

            }else if(rn!=null&&rn.color==Color.Red) {//  rn  为red
                if(current==parent.left) {//被删节点是左孩子
                    s.color=parent.color;
                    parent.color=Color.Black;
                    rn.color=Color.Black;
                    if(gp==null) {
                        root=leftRotate(parent);
                    }else     if(gp.left==parent) {
                        gp.left=leftRotate(parent);
                    }else if(gp.right==parent) {
                        gp.right=leftRotate(parent);
                    }
                }else {//Lr
                    /*            p                                p                             rn(p 的颜色)
                     *
                     *     s black   x black    --->    rn            x     -->        s black           p black
                     *
                     *        rn red                s                                                       x
                     */
                    rn.color=parent.color;
                    parent.color=Color.Black;
                    parent.left=leftRotate(s);
                    if(gp==null) {
                        root=rightRotate(parent);
                    }else if(gp.left==parent) {
                        gp.left=rightRotate(parent);
                    }else if(gp.right==parent) {
                        gp.right=rightRotate(parent);
                    }
                }
                return ;
            }else if(ln!=null&&ln.color==Color.Red) {
                if(current==parent.left) {
                    parent.right=rightRotate(s);
                    ln.color=parent.color;
                    parent.color=Color.Black;
                    if(gp==null) {
                        root=leftRotate(parent);
                    }else if(gp.left==parent) {
                        gp.left=leftRotate(parent);
                    }else if(gp.right==parent) {
                        gp.right=leftRotate(parent);
                    }
                }else {
                    s.color=parent.color;
                    parent.color=Color.Black;
                    ln.color=Color.Black;
                    if(gp==null) {
                        root=rightRotate(parent);
                    }else     if(gp.left==parent) {
                        gp.left=rightRotate(parent);
                    }else if(gp.right==parent) {
                        gp.right=rightRotate(parent);
                    }

                }
            }


        }
    }



    /*
     * 前序遍历
     */
    public void preTraversal(Node current) {
        if(current!=null) {
            System.out.println(current.t+" "+current.color);
            preTraversal(current.left);
            preTraversal(current.right);
        }
    }



    public static void main(String[] args) {

        RedBlackTree<Integer> rbt=new RedBlackTree<Integer>();
        rbt.addNode(10);
        rbt.addNode(5);
        rbt.addNode(15);
        rbt.addNode(3);
        rbt.addNode(12);
        rbt.addNode(8);
        rbt.addNode(17);
        rbt.addNode(6);
        rbt.addNode(9);
        rbt.addNode(2);
        rbt.delete(5);
        rbt.preTraversal(rbt.root);
    }





}
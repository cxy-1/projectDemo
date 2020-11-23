package structure.link;

class Link {//一个链表类

    public Entry head;//定义链表的头结点         始终表示头结点

    public Link() {//初始化链表时创建一个头结点
        head = new Entry();
    }
    public static void main(String[] args) {

        Link link1 = new Link();
        for (int i = 0; i < 6; i++) {
            link1.insertTail(i);       //尾插法去初始化链表
        }
        link1.deleteNode(3);  //删除索引值为三的 节点
        link1.show();
        System.out.println("***********");
        link1.show();
        link1.head= link1.ReverseIteratively(link1.head.next);// 反转当前节点以后的全部节点
        System.out.println("***********");
        link1.show();
    }


    class Entry {//Entry  节点类
        int data;//数据域        存储当前节点的数据信息
        int da=1;
        Entry next;//地址域  ji记录上一个节点的data的地址信息

        public Entry() {//将头结点初始化为  data=-1   next=null
            data = -1;
            next = null;
        }

        public Entry(int val) {//其他结点所需的构造函数            在节点初始化时 往数据域里存入数据
            data = val;
            next = null;
        }
    }

    //头插法
    public void insertHead(int val) {  // 插在头结点和第一个节点之间
        //有这么一个节点
        Entry cur = new Entry(0);//创建空的节点
        /*
         * 第一种
         */
        cur.next = head.next; // 下一个节点
        head.next = cur; // 相当于记录下了当前节点的地址         对象之间赋值时 是传递的对象的内存地址
        /*
         * 第二种错误的写法
         * 注意：：一定要保证能能找到后面的那个结点！！！！所以不能先cur给head.next 这样就找不到原来的head.next了
         */
		/*head.next = cur;
		cur.next = head.next;*/
    }

    //尾插法
    public void insertTail(int val) {
        Entry tmp;
        Entry cur = new Entry(val);
        tmp = head;
        /*
         * 遍历链表到最后一个结点
         */
        while (tmp.next != null) {
            tmp = tmp.next;
        }
        tmp.next = cur; // 相当于记录下了当前节点的地址         对象之间赋值时 是传递的对象的内存地址
        cur.next = null;
    }
    /**
     *
     * @param index:删除第index个节点
     * @return
     */
    public boolean deleteNode(int index) {
        if (index < 1 || index > getLength()) {
            return false;
        }
        if (index == 1) {
            head = head.next;
            return true;
        }
        int i = 1;
        Entry preNode = head;
        Entry curNode = preNode.next;
        while (curNode != null) {
            if (i == index) {
                preNode.next = curNode.next;
                return true;
            }
            preNode = curNode;
            curNode = curNode.next;
            i++;
        }
        return false;
    }

    /** 根据节点地址去删除节点
     * 在不知道头指针的情况下删除指定节点          即知道有这个对象 但是不知道这个对象在那个位置
     *
     * @param n
     * @return
     */
    public boolean deleteNode11(Entry n) {
        if (n == null || n.next == null) {
            return false;
        }
        System.out.println("删除成功！:"+n.data);
/*        int tmp = n.data;
        n.data = n.next.data;
        n.next.data = tmp;*/ n.data = n.next.data;        // 这三行可以只要一行即可，，  因为被删除的那个节点的数据域没必要再去修改里面的值了 直接丢弃即可
        n.next = n.next.next;
        return true;
    }

    //得到单链表的长度
    public int getLength() {
        int len = 0;//长度标志量
        Entry tmp;
        tmp = head.next;
        while (tmp != null) {//遍历整个链表得到链表长度
            len++;
            tmp = tmp.next;
        }
        return len;
    }
    //插入指定的位置       指定索引的位置上
    public boolean insertPos(int pos, int val) {
        if (pos < 0 || pos > getLength()) {//保证当前索引的节点位置 在列表长度范围内
            return false;
        } else {
            Entry cur = head;
            //找到插入的地方
            for (int i = 0; i <= pos - 1; i++) {
                cur = cur.next;
            }
            Entry entry = new Entry(val);
            //进行插入
            entry.next = cur.next;
            cur.next = entry;
            return true;
        }
    }

    public void show() {
        Entry tmp;
        tmp = head;
        //遍历打印链表
        System.out.println("开始打印");
        while (tmp.next != null) {
            System.out.println("data :" + tmp.next.data);
            //System.out.println("da :" + tmp.next.da);
            if(tmp.next.data==1){
                deleteNode11(tmp.next);//遍历时 删除数据域里值为1的节点
            }
            tmp = tmp.next;
        }
    }

    /**https://blog.csdn.net/weixin_40807247/article/details/91435275
     * 链表反转      反转当前节点以后的全部节点  （从传参传进去的节点开始反转）      ？？？？？？？
     *
     * @param head
     * @return
     */
    public Entry ReverseIteratively(Entry head) {
        Entry pReversedHead = head;//最终
        Entry pNode = head; //  中转站  中间节点 链表首
        Entry pPrev = null;//  中转站  中间节点  链表尾
        while (pNode != null) {
            Entry pNext = pNode.next;
            if (pNext == null) {
                pReversedHead = pNode;
                System.out.println("反转："+pReversedHead.data);
            }
            pNode.next = pPrev;
            pPrev = pNode;
            pNode = pNext;
        }
        this.head = pReversedHead;
        return this.head;
    }
    Entry reverse(Entry head){// 链表反转
        Entry p,q,pr;
        p = head.next;
        q = null;
        head.next = null;
        while(p!=null){
            pr = p.next;
            p.next = q;
            q = p;
            p = pr;
        }
        head.next = q;
        return head;
    }

    /** 采用快慢指针的方式查找单链表的中间节点，快指针一次走两步，慢指针一次走一步，当快指针走完时，慢指针刚好到达中间节点。
     * 查找单链表的中间节点
     *
     * @param head
     * @return
     */
    public Entry SearchMid(Entry head) {
        Entry p = this.head, q = this.head;
        while (p != null && p.next != null && p.next.next != null) {
            p = p.next.next;
            q = q.next;
        }
        System.out.println("Mid:" + q.data);
        return q;
    }

    /**  采用两个指针P1,P2，P1先前移K步，然后P1、P2同时移动，当p1移动到尾部时，P2所指位置的元素即倒数第k个元素 。
     * 查找倒数 第k个元素
     *
     * @param head
     * @param k
     * @return
     */
    public Entry findElem(Entry head, int k) {
        if (k < 1 || k > this.getLength()) {
            return null;
        }
        Entry p1 = head;
        Entry p2 = head;
        for (int i = 0; i < k; i++)// 前移k步
            p1 = p1.next;
        while (p1 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p2;
    }
    /**
     * 排序  正序
     *
     * @return
     */
    public Entry orderList() {
        Entry nextNode = null;
        int tmp = 0;
        Entry curNode = head;
        while (curNode.next != null) {
            nextNode = curNode.next;
            while (nextNode != null) {
                if (curNode.data > nextNode.data) {
                    tmp = curNode.data;
                    curNode.data = nextNode.data;
                    nextNode.data = tmp;
                }
                nextNode = nextNode.next;
            }
            curNode = curNode.next;
        }
        return head;
    }
    /**
     * 删除重复节点
     */
    public void deleteDuplecate(Entry head) {
        Entry p = head;
        while (p != null) {
            Entry q = p;
            while (q.next != null) {
                if (p.data == q.next.data) {
                    q.next = q.next.next;
                } else
                    q = q.next;
            }
            p = p.next;
        }

    }
    /**
     * 从尾到头输出单链表，采用递归方式实现
     *
     * @param pListHead
     */
    public void printListReversely(Entry pListHead) {
        if (pListHead != null) {
            printListReversely(pListHead.next);
            System.out.println("printListReversely:" + pListHead.data);
        }
    }
    /**
     * 判断链表是否有环，单向链表有环时，尾节点相同    有环  是 true
     *
     * @param head
     * @return
     */
    public boolean IsLoop(Entry head) {
        Entry fast = head, slow = head;
        if (fast == null) {
            return false;
        }
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                System.out.println("该链表有环");
                return true;
            }
        }
        return !(fast == null || fast.next == null);
    }

    /**
     * 找出链表环的入口        ？？      出口 是 链表头            入口 是链表尾
     *
     * @param head
     * @return
     */
    public Entry FindLoopPort(Entry head) {
        Entry fast = head, slow = head;
        while (fast != null && fast.next != null) {   //？？  感觉这个 没意义       因为就单纯的遍历
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)// 循环两次追上时跳过本次
                break;
        }
        if (fast == null || fast.next == null)
            return null;
        slow = head;
        while (slow != fast) {//  即 找到链表末尾
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}

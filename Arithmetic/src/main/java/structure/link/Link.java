package structure.link;

class Link {//һ��������

    public Entry head;//���������ͷ���         ʼ�ձ�ʾͷ���

    public Link() {//��ʼ������ʱ����һ��ͷ���
        head = new Entry();
    }
    public static void main(String[] args) {

        Link link1 = new Link();
        for (int i = 0; i < 6; i++) {
            link1.insertTail(i);       //β�巨ȥ��ʼ������
        }
        link1.deleteNode(3);  //ɾ������ֵΪ���� �ڵ�
        link1.show();
        System.out.println("***********");
        link1.show();
        link1.head= link1.ReverseIteratively(link1.head.next);// ��ת��ǰ�ڵ��Ժ��ȫ���ڵ�
        System.out.println("***********");
        link1.show();
    }


    class Entry {//Entry  �ڵ���
        int data;//������        �洢��ǰ�ڵ��������Ϣ
        int da=1;
        Entry next;//��ַ��  ji��¼��һ���ڵ��data�ĵ�ַ��Ϣ

        public Entry() {//��ͷ����ʼ��Ϊ  data=-1   next=null
            data = -1;
            next = null;
        }

        public Entry(int val) {//�����������Ĺ��캯��            �ڽڵ��ʼ��ʱ �����������������
            data = val;
            next = null;
        }
    }

    //ͷ�巨
    public void insertHead(int val) {  // ����ͷ���͵�һ���ڵ�֮��
        //����ôһ���ڵ�
        Entry cur = new Entry(0);//�����յĽڵ�
        /*
         * ��һ��
         */
        cur.next = head.next; // ��һ���ڵ�
        head.next = cur; // �൱�ڼ�¼���˵�ǰ�ڵ�ĵ�ַ         ����֮�丳ֵʱ �Ǵ��ݵĶ�����ڴ��ַ
        /*
         * �ڶ��ִ����д��
         * ע�⣺��һ��Ҫ��֤�����ҵ�������Ǹ���㣡���������Բ�����cur��head.next �������Ҳ���ԭ����head.next��
         */
		/*head.next = cur;
		cur.next = head.next;*/
    }

    //β�巨
    public void insertTail(int val) {
        Entry tmp;
        Entry cur = new Entry(val);
        tmp = head;
        /*
         * �����������һ�����
         */
        while (tmp.next != null) {
            tmp = tmp.next;
        }
        tmp.next = cur; // �൱�ڼ�¼���˵�ǰ�ڵ�ĵ�ַ         ����֮�丳ֵʱ �Ǵ��ݵĶ�����ڴ��ַ
        cur.next = null;
    }
    /**
     *
     * @param index:ɾ����index���ڵ�
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

    /** ���ݽڵ��ַȥɾ���ڵ�
     * �ڲ�֪��ͷָ��������ɾ��ָ���ڵ�          ��֪����������� ���ǲ�֪������������Ǹ�λ��
     *
     * @param n
     * @return
     */
    public boolean deleteNode11(Entry n) {
        if (n == null || n.next == null) {
            return false;
        }
        System.out.println("ɾ���ɹ���:"+n.data);
/*        int tmp = n.data;
        n.data = n.next.data;
        n.next.data = tmp;*/ n.data = n.next.data;        // �����п���ֻҪһ�м��ɣ���  ��Ϊ��ɾ�����Ǹ��ڵ��������û��Ҫ��ȥ�޸������ֵ�� ֱ�Ӷ�������
        n.next = n.next.next;
        return true;
    }

    //�õ�������ĳ���
    public int getLength() {
        int len = 0;//���ȱ�־��
        Entry tmp;
        tmp = head.next;
        while (tmp != null) {//������������õ�������
            len++;
            tmp = tmp.next;
        }
        return len;
    }
    //����ָ����λ��       ָ��������λ����
    public boolean insertPos(int pos, int val) {
        if (pos < 0 || pos > getLength()) {//��֤��ǰ�����Ľڵ�λ�� ���б��ȷ�Χ��
            return false;
        } else {
            Entry cur = head;
            //�ҵ�����ĵط�
            for (int i = 0; i <= pos - 1; i++) {
                cur = cur.next;
            }
            Entry entry = new Entry(val);
            //���в���
            entry.next = cur.next;
            cur.next = entry;
            return true;
        }
    }

    public void show() {
        Entry tmp;
        tmp = head;
        //������ӡ����
        System.out.println("��ʼ��ӡ");
        while (tmp.next != null) {
            System.out.println("data :" + tmp.next.data);
            //System.out.println("da :" + tmp.next.da);
            if(tmp.next.data==1){
                deleteNode11(tmp.next);//����ʱ ɾ����������ֵΪ1�Ľڵ�
            }
            tmp = tmp.next;
        }
    }

    /**https://blog.csdn.net/weixin_40807247/article/details/91435275
     * ����ת      ��ת��ǰ�ڵ��Ժ��ȫ���ڵ�  ���Ӵ��δ���ȥ�Ľڵ㿪ʼ��ת��      ��������������
     *
     * @param head
     * @return
     */
    public Entry ReverseIteratively(Entry head) {
        Entry pReversedHead = head;//����
        Entry pNode = head; //  ��תվ  �м�ڵ� ������
        Entry pPrev = null;//  ��תվ  �м�ڵ�  ����β
        while (pNode != null) {
            Entry pNext = pNode.next;
            if (pNext == null) {
                pReversedHead = pNode;
                System.out.println("��ת��"+pReversedHead.data);
            }
            pNode.next = pPrev;
            pPrev = pNode;
            pNode = pNext;
        }
        this.head = pReversedHead;
        return this.head;
    }
    Entry reverse(Entry head){// ����ת
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

    /** ���ÿ���ָ��ķ�ʽ���ҵ�������м�ڵ㣬��ָ��һ������������ָ��һ����һ��������ָ������ʱ����ָ��պõ����м�ڵ㡣
     * ���ҵ�������м�ڵ�
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

    /**  ��������ָ��P1,P2��P1��ǰ��K����Ȼ��P1��P2ͬʱ�ƶ�����p1�ƶ���β��ʱ��P2��ָλ�õ�Ԫ�ؼ�������k��Ԫ�� ��
     * ���ҵ��� ��k��Ԫ��
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
        for (int i = 0; i < k; i++)// ǰ��k��
            p1 = p1.next;
        while (p1 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p2;
    }
    /**
     * ����  ����
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
     * ɾ���ظ��ڵ�
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
     * ��β��ͷ������������õݹ鷽ʽʵ��
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
     * �ж������Ƿ��л������������л�ʱ��β�ڵ���ͬ    �л�  �� true
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
                System.out.println("�������л�");
                return true;
            }
        }
        return !(fast == null || fast.next == null);
    }

    /**
     * �ҳ����������        ����      ���� �� ����ͷ            ��� ������β
     *
     * @param head
     * @return
     */
    public Entry FindLoopPort(Entry head) {
        Entry fast = head, slow = head;
        while (fast != null && fast.next != null) {   //����  �о���� û����       ��Ϊ�͵����ı���
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)// ѭ������׷��ʱ��������
                break;
        }
        if (fast == null || fast.next == null)
            return null;
        slow = head;
        while (slow != fast) {//  �� �ҵ�����ĩβ
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}

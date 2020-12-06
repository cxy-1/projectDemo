package structure.Stack;

public class ArrayStack{//  ������ȥģ��ջ

    private static final String TAG = "ArrayStack";
    private Object[] contents;//��������
    private int top = -1;//ҵ�������Ǵ�0��ʼ������ ������-1
    private int bottom = -1;
    private int SIZE = 10;//��һ����ʼֵ��С  zhanջ������

    public ArrayStack(){
        contents = new Object[SIZE];//������ʼ����ջ
        top = -1;
    }

    public int push(Object obj) throws Exception {
        if (top > SIZE) throw new Exception("С��ȣ�ջ�Ѿ����ˣ�");
        top++;
        contents[top] = obj;
        return top;
    }

    public Object pop() throws Exception{
        if (top == bottom) throw new Exception("С��ȣ�ջ�Ѿ����ˣ�");
        Object obj = contents[top];
        contents[top] = null;
        top--;
        return obj;
    }

    public boolean isEmpty(){//����ֻ�������ϵĿ�     ʵ������Щ���ݿ��ܻ���������
        return top == bottom;
    }

    public int getSize(){
        return top + 1;
    }

    public void display() throws Exception{//�����൱���Ǳ�������     ûɶ����
        if (getSize() == 0) throw new Exception("��ջ!");
        for (int i=getSize()-1;i>=0;i--){
            System.out.print(contents[i].toString() + "->");
        }
        System.out.println("");
    }
    public void test(){
        ArrayStack as = new ArrayStack();
        //as.display();
        try {
            as.push("С���");
            as.push("���潣��");
            as.push("yc");
            as.push("����");
            as.push("aaa");
            as.push("ertte");
            as.push("hello");
            as.display();
            as.pop();
            System.out.println(as.getSize());
            as.pop();
            as.display();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ArrayStack().test();
    }
}



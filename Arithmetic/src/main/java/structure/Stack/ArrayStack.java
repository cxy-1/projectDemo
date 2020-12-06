package structure.Stack;

public class ArrayStack{//  用数组去模拟栈

    private static final String TAG = "ArrayStack";
    private Object[] contents;//对象数组
    private int top = -1;//业务数组是从0开始索引的 所以是-1
    private int bottom = -1;
    private int SIZE = 10;//有一个初始值大小  zhan栈的容量

    public ArrayStack(){
        contents = new Object[SIZE];//建立初始化空栈
        top = -1;
    }

    public int push(Object obj) throws Exception {
        if (top > SIZE) throw new Exception("小杨逗比，栈已经满了！");
        top++;
        contents[top] = obj;
        return top;
    }

    public Object pop() throws Exception{
        if (top == bottom) throw new Exception("小杨逗比，栈已经空了！");
        Object obj = contents[top];
        contents[top] = null;
        top--;
        return obj;
    }

    public boolean isEmpty(){//仅仅只是名义上的空     实际上那些数据可能还在数组里
        return top == bottom;
    }

    public int getSize(){
        return top + 1;
    }

    public void display() throws Exception{//仅仅相当于是遍历数组     没啥意义
        if (getSize() == 0) throw new Exception("空栈!");
        for (int i=getSize()-1;i>=0;i--){
            System.out.print(contents[i].toString() + "->");
        }
        System.out.println("");
    }
    public void test(){
        ArrayStack as = new ArrayStack();
        //as.display();
        try {
            as.push("小杨逗比");
            as.push("潇湘剑雨");
            as.push("yc");
            as.push("逗比");
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



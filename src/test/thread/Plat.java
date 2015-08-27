package test.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaron on 15-8-18.
 */
public class Plat {
    private List<Object> plat = new ArrayList<Object>();
    private static int size = 1;
    private static int count = 0;

    public synchronized void putEgg(String name, Object egg) {
        while (plat.size() >= size) {
            System.out.println(name + " 框子满了，等一下呗！");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        plat.add(egg);
        System.out.println(name + ": 添加新的鸡蛋 框子还有" + plat.size());
        System.out.println("count: " + count);
        if (count++ > 300) {
            System.exit(0);
        }
        notifyAll();
    }

    public synchronized Object getEgg(String name) {
        while (plat.size() == 0) {
            System.out.println(name + " 已经没了，猴急个啥！等等吧");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Object egg = plat.remove(0);
        System.out.println(name + " 获取鸡蛋 1 剩余:" + plat.size());
        notifyAll();
        return egg;
    }
}



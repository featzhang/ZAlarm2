package test.thread;

/**
 * Created by aaron on 15-8-18.
 */
public class CustormRunnable implements Runnable {
    private Plat plat;
    private String name;

    public CustormRunnable(String name, Plat plat) {
        this.plat = plat;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {

            Object egg = plat.getEgg(name);
//            System.out.println(name + ":  抢到了，哈哈哈");
        }
    }
}

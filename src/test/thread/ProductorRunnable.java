package test.thread;

/**
 * Created by aaron on 15-8-18.
 */
public class ProductorRunnable implements Runnable {
    private final String name;
    private Plat plat;


    public ProductorRunnable(String name, Plat plat) {
        this.name = name;
        this.plat = plat;
    }

    @Override
    public void run() {
        while (true) {
//            System.out.println(name + ": 等待添加鸡蛋 ");
            plat.putEgg(name, new Object());
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

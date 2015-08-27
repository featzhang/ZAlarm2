package test.thread;

/**
 * Created by aaron on 15-8-18.
 */
public class Main {
    public static void main(String[] args) {
        Plat plat = new Plat();
        for (int i = 0;i < 10;i++) {
            new Thread(new CustormRunnable("Custorm" + (i + 1) + "", plat)).start();
        }
        for (int i = 0; i < 20; i++) {
            new Thread(new ProductorRunnable("Productor " + (i + 1), plat)).start();
        }
    }
}

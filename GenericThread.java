import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class GenericThread extends Thread {
    private final List<String> baseDeDados;
    private final ReentrantLock lock;

    public GenericThread(List<String> baseDeDados, ReentrantLock lock) {
        this.baseDeDados = baseDeDados;
        this.lock = lock;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int pos = random.nextInt(baseDeDados.size());
            lock.lock();
            try {
                // Aleatoriamente, faz uma leitura ou escrita
                if (random.nextBoolean()) {
                    String palavra = baseDeDados.get(pos);  // Leitura
                } else {
                    baseDeDados.set(pos, "MODIFICADO");  // Escrita
                }
            } finally {
                lock.unlock();
            }
        }
        try {
            Thread.sleep(1);  // Simula tempo de processamento
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

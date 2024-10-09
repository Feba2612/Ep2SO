import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Writer extends Thread {
    private final List<String> baseDeDados;
    private final ReentrantReadWriteLock lock;

    public Writer(List<String> baseDeDados, ReentrantReadWriteLock lock) {
        this.baseDeDados = baseDeDados;
        this.lock = lock;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int pos = random.nextInt(baseDeDados.size());
            lock.writeLock().lock();
            try {
                baseDeDados.set(pos, "MODIFICADO");  // Modificação da palavra
            } finally {
                lock.writeLock().unlock();
            }
        }
        try {
            Thread.sleep(1);  // Simula tempo de processamento
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

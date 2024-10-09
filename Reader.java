import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Reader extends Thread {
    private final List<String> baseDeDados;
    private final ReentrantReadWriteLock lock;

    public Reader(List<String> baseDeDados, ReentrantReadWriteLock lock) {
        this.baseDeDados = baseDeDados;
        this.lock = lock;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int pos = random.nextInt(baseDeDados.size());
            lock.readLock().lock();
            try {
                String palavra = baseDeDados.get(pos);  // Leitura da palavra
                // Simula alguma operação com a palavra lida
            } finally {
                lock.readLock().unlock();
            }
        }
        try {
            Thread.sleep(1);  // Simula tempo de processamento
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

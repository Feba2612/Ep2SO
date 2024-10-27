import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final String[] data;
    private final boolean controlled;

    public Database(String filename, boolean controlled) throws IOException {
        this.controlled = controlled;
        this.data = loadDataFromFile(filename);
    }

    private String[] loadDataFromFile(String filename) throws IOException {
        String[] data = new String[36242];
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null && i < data.length) {
                data[i++] = line.trim();
            }
        }
        return data;
    }

    public int getSize() {
        return data.length;
    }

    public void read(int index) {
        if (controlled) {
            lock.readLock().lock();
            try {
                String value = data[index];
                // Simulação de leitura (valor não usado externamente)
            } finally {
                lock.readLock().unlock();
            }
        } else {
            synchronized (this) {
                String value = data[index];
            }
        }
    }

    public void write(int index) {
        if (controlled) {
            lock.writeLock().lock();
            try {
                data[index] = "MODIFICADO";
            } finally {
                lock.writeLock().unlock();
            }
        } else {
            synchronized (this) {
                data[index] = "MODIFICADO";
            }
        }
    }
}

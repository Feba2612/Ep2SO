import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final String[] data;
    private final boolean controlled;

    // Variáveis para medir o tempo de overhead de sincronização
    private long syncTimeRead = 0;  // Tempo total de sincronização para leituras
    private long syncTimeWrite = 0; // Tempo total de sincronização para escritas

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
        long startSyncTime = System.nanoTime(); // Início da medição de tempo de sincronização

        if (controlled) {
            lock.readLock().lock();
            try {
                syncTimeRead += System.nanoTime() - startSyncTime; // Tempo de bloqueio de leitura

                String value = data[index];
                // Simulação de leitura (valor não usado externamente)
            } finally {
                startSyncTime = System.nanoTime(); // Início da medição de tempo de desbloqueio
                lock.readLock().unlock();
                syncTimeRead += System.nanoTime() - startSyncTime; // Tempo de desbloqueio de leitura
            }
        } else {
            synchronized (this) {
                syncTimeRead += System.nanoTime() - startSyncTime; // Tempo de bloqueio de leitura (sem lock explícito)
                String value = data[index];
            }
        }
    }

    public void write(int index) {
        long startSyncTime = System.nanoTime(); // Início da medição de tempo de sincronização

        if (controlled) {
            lock.writeLock().lock();
            try {
                syncTimeWrite += System.nanoTime() - startSyncTime; // Tempo de bloqueio de escrita

                data[index] = "MODIFICADO";
            } finally {
                startSyncTime = System.nanoTime(); // Início da medição de tempo de desbloqueio
                lock.writeLock().unlock();
                syncTimeWrite += System.nanoTime() - startSyncTime; // Tempo de desbloqueio de escrita
            }
        } else {
            synchronized (this) {
                syncTimeWrite += System.nanoTime() - startSyncTime; // Tempo de bloqueio de escrita (sem lock explícito)
                data[index] = "MODIFICADO";
            }
        }
    }

    // Método para exibir os tempos de sincronização
    public void printSyncOverhead() {
        System.out.println("Tempo total de sincronização para operações de leitura: " + syncTimeRead/1_000_000 + " ms");
        System.out.println("Tempo total de sincronização para operações de escrita: " + syncTimeWrite/1_000_000 + " ms");
    }
}

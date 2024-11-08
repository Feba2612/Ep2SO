import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ReaderWriterSimulator {

    private static final int TOTAL_THREADS = 100;
    private static final int TOTAL_RUNS = 50;
    private static final int ACCESS_COUNT = 100;
    private static final int SLEEP_TIME_MS = 1;
    private static final String FILE_PATH = "bd.txt";

    public static void main(String[] args) {
        try {
            System.out.println("Simulação com controle de leitores/escritores:");
            runSimulation(true);

            System.out.println("\nSimulação sem controle de leitores/escritores:");
            runSimulation(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runSimulation(boolean controlled) throws IOException {
        Database db = new Database(FILE_PATH, controlled);

        for (int readerCount = 0; readerCount <= TOTAL_THREADS; readerCount++) {
            int writerCount = TOTAL_THREADS - readerCount;
            long totalTime = 0;

            for (int run = 0; run < TOTAL_RUNS; run++) {
                List<Thread> threads = createThreads(readerCount, writerCount, db);
                long startTime = System.currentTimeMillis();

                // Start all threads
                for (Thread thread : threads) {
                    thread.start();
                }

                // Wait for all threads to finish
                for (Thread thread : threads) {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                long endTime = System.currentTimeMillis();
                totalTime += (endTime - startTime);
            }

            double averageTime = totalTime / (double) TOTAL_RUNS;
            System.out.printf("Proporção: %d Readers, %d Writers - Tempo médio: %.2f ms\n", readerCount, writerCount, averageTime);
        }

        // Imprime o overhead de sincronização após todas as execuções para cada configuração de controle
        System.out.println("\nOverhead de sincronização para " + (controlled ? "execução com controle:" : "execução sem controle:"));
        db.printSyncOverhead();
    }

    private static List<Thread> createThreads(int readerCount, int writerCount, Database db) {
        List<Thread> threads = new ArrayList<>();
        Random random = new Random();

        // Criar threads de leitores
        for (int i = 0; i < readerCount; i++) {
            threads.add(new Thread(new Reader(db, ACCESS_COUNT, SLEEP_TIME_MS)));
        }

        // Criar threads de escritores
        for (int i = 0; i < writerCount; i++) {
            threads.add(new Thread(new Writer(db, ACCESS_COUNT, SLEEP_TIME_MS)));
        }

        // Embaralha a lista de threads para garantir aleatoriedade
        Collections.shuffle(threads, random);
        return threads;
    }
}

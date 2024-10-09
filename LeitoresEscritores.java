import java.io.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LeitoresEscritores {
    // Base de dados compartilhada
    public static final List<String> baseDeDados = new ArrayList<>();

    // Função para carregar o arquivo "bd.txt"
    public static void carregarArquivo(String nomeArquivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo));
        String linha;
        while ((linha = reader.readLine()) != null) {
            baseDeDados.add(linha);
        }
        reader.close();
    }

    // Função principal que inicializa a execução
    public static void main(String[] args) throws IOException, InterruptedException {
        carregarArquivo("bd.txt"); // Carrega o arquivo com as palavras
        System.out.println("Base de dados carregada com " + baseDeDados.size() + " palavras.");

        // Instancia o lock de leitura/escrita
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        // Array de threads
        Thread[] threads = new Thread[100];
        Random random = new Random();

        // Criação aleatória de threads de leitores e escritores
        for (int i = 0; i < 100; i++) {
            if (random.nextBoolean()) {
                threads[i] = new Reader(baseDeDados, lock); // Cria um leitor
            } else {
                threads[i] = new Writer(baseDeDados, lock); // Cria um escritor
            }
        }

        // Embaralhar as threads (opcional)
        List<Thread> threadList = Arrays.asList(threads);
        Collections.shuffle(threadList);
        threadList.toArray(threads);

        long startTime = System.currentTimeMillis();

        // Inicia todas as threads
        for (Thread thread : threads) {
            thread.start();
        }

        // Espera que todas as threads terminem
        for (Thread thread : threads) {
            thread.join();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Tempo total de execução: " + (endTime - startTime) + "ms");
    }
}

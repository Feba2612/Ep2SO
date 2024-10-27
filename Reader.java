import java.util.Random;

public class Reader implements Runnable {
    private final Database db;
    private final int accessCount;
    private final int sleepTimeMs;
    private final Random random = new Random();

    public Reader(Database db, int accessCount, int sleepTimeMs) {
        this.db = db;
        this.accessCount = accessCount;
        this.sleepTimeMs = sleepTimeMs;
    }

    @Override
    public void run() {
        for (int i = 0; i < accessCount; i++) {
            int index = random.nextInt(db.getSize());
            db.read(index); // Acesso de leitura
        }
        try {
            Thread.sleep(sleepTimeMs); // Validação (simulação de espera)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package bandeau;
import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Classe utilitaire pour représenter la classe-association UML
 */
class ScenarioElement {

    Effect effect;
    int repeats;

    ScenarioElement(Effect e, int r) {
        effect = e;
        repeats = r;
    }
}
/**
 * Un scenario mémorise une liste d'effets, et le nombre de repetitions pour chaque effet
 * Un scenario sait se jouer sur un bandeau.
 */
public class Scenario {

    private final List<ScenarioElement> myElements = new LinkedList<>();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    /**
     * Ajouter un effect au scenario.
     *
     * @param e l'effet à ajouter
     * @param repeats le nombre de répétitions pour cet effet
     */
    public void addEffect(Effect e, int repeats) {
        readWriteLock.writeLock().lock();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        myElements.add(new ScenarioElement(e, repeats));
        readWriteLock.writeLock().unlock();


    }

    /**
     * Jouer ce scenario sur un bandeau
     *
     * @param b le bandeau ou s'afficher.
     */
    public void playOn(BandeauLock b) {
        Thread t = new Thread(() -> {
            readWriteLock.readLock().lock();
            b.lock();
            for (ScenarioElement element : myElements) {
                for (int repeats = 0; repeats < element.repeats; repeats++) {
                    element.effect.playOn(b);
                }
            }
            b.unlock();
            readWriteLock.readLock().unlock();
        });
        t.start();
    }
}

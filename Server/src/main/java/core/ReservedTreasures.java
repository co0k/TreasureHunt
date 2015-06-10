package core;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by nebios on 10.06.15.
 */
public class ReservedTreasures {
    /**
     * in seconds
     */
    private long timeout;
    private Map<Map.Entry<Integer, Integer>, Long> chestTimeoutMap;
    private final ScheduledExecutorService scheduler;

    public ReservedTreasures(int timeout) {
        this.timeout = timeout;
        chestTimeoutMap = new HashMap<>();
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new TimeoutTimerTask(), 0, 1, TimeUnit.SECONDS);
    }

    /**
     * checks if the treasure chest is reserved for the user, whos id equals token
     *
     * @param tid   id of the treasure chest
     * @param token the users id
     * @return true if the chest is reserved <br />
     * false otherwise
     */
    public boolean isReservedfor(int tid, int token) {
        synchronized (chestTimeoutMap) {
            return chestTimeoutMap.containsKey(new AbstractMap.SimpleEntry<>(token, tid));
        }
    }

    /**
     * checks if the treasure chest is reserved
     *
     * @param tid id of the treasure chest
     * @return true if the chest is reserved <br />
     * false otherwise
     */
    public boolean isReserved(int tid) {
        synchronized (chestTimeoutMap) {
            for (Map.Entry<Map.Entry<Integer, Integer>, Long> entry : chestTimeoutMap.entrySet()) {
                if (entry.getKey().getValue() == tid)
                    return true;
            }
        }
        return false;
    }

    /**
     * Checks if the treasure is not reserved already
     * and adds a reservation for the user with the uid token if so
     *
     * @param token the users id
     * @param tid   the treasures id
     */
    public void reserveTreasure(int token, int tid) {
        synchronized (chestTimeoutMap) {
            if (!isReserved(tid))
                chestTimeoutMap.put(new AbstractMap.SimpleEntry<>(token, tid), timeout);
        }
    }

    /**
     * If the user finishes the quiz the reservation is not needed anymore
     *
     * @param token the users id
     * @param tid   the treasures id
     */
    public void deleteReservationFor(int token, int tid) {
        AbstractMap.SimpleEntry<Integer, Integer> pairToDelete = new AbstractMap.SimpleEntry<>(token, tid);
        synchronized (chestTimeoutMap) {
            if (chestTimeoutMap.containsKey(pairToDelete))
                chestTimeoutMap.remove(pairToDelete);
        }
    }

    /**
     * decrements the timeout every second
     */
    private class TimeoutTimerTask implements Runnable {

        @Override
        public void run() {
            synchronized (chestTimeoutMap) {
                for (Map.Entry<Map.Entry<Integer, Integer>, Long> entry : chestTimeoutMap.entrySet()) {
                    if (entry.getValue() <= 0)
                        chestTimeoutMap.remove(entry.getKey());
                    else
                        chestTimeoutMap.put(entry.getKey(), entry.getValue() - 1);
                }
            }
        }
    }

}

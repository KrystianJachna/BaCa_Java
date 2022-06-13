import java.util.concurrent.Semaphore;


interface SeaportManager {

    private Semaphore channelSem;
    private Semaphore docksSem;

    private Semaphore usingArray;
    private Ship [] shipArray;


    void init(int numberOfDocks, int seawayCapacity) {
        channelSem = new Semaphore(seawayCapacity, true);
        docksSem = new Semaphore(numberOfDocks, true);
        docksSem = new boolean[numberOfDocks];
        shipArray = new Ship[numberOfDocks];
        usingArray = new Semaphore(1);
    }

    void requestSeawayEntrance(Ship s) throws InterruptedException {
        while (!insertShip(s));
        channelSem.acquire();
    }

    private boolean insertShip(Ship s) throws InterruptedException {
        usingArray.acquire();

        int first = 0;
        int freePlaces = 0;
        boolean flag = false;

        for (int i = 0; i < shipArray.length; ++i) {
            if (shipArray[i] == null)
                freePlaces++;
            else{
                freePlaces = 0;
                first = i + 1;
            }
            if (freePlaces == s.getDockingSize()) {
                flag = true;
                break;
            }
        }
        if (flag) {
            for (int i = first; i < freePlaces; ++i)
                shipArray[i] = s;
        }
        usingArray.release();
        return flag;
    }

    int requestPortEntrance(Ship s);

    void signalPortEntered(Ship s);

    void requestPortExit(Ship s);

    void signalPortExited(Ship s);

    void signalShipSailedAway(Ship s);
}

interface Ship {

    int getDockingSize();

    Integer getAssignedDock();
}

abstract class SeaportManagerImpl implements SeaportManager{

}
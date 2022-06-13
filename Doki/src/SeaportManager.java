// Krystian Jachna

import java.util.concurrent.Semaphore;

/*
interface SeaportManager {
    void init(int numberOfDocks, int seawayCapacity);
    void requestSeawayEntrance(Ship s);
    int requestPortEntrance(Ship s);
    void signalPortEntered(Ship s);
    void requestPortExit(Ship s);
    void signalPortExited(Ship s);
    void signalShipSailedAway(Ship s);
}
*/

interface Ship {
    int getDockingSize();
    Integer getAssignedDock();
}

public class SeaportManagerImpl /* implements SeaportManager */ {

    private Semaphore channelSem;

    private Semaphore usingArray;
    private Ship docksArray[];

    private boolean fillDocks(Ship s) throws InterruptedException {
        usingArray.acquire();

        int first = 0;
        int freePlaces = 0;
        boolean flag = false;

        for (int i = 0; i < docksArray.length; ++i) {
            if (docksArray[i] == null)
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
                docksArray[i] = s;
        }
        usingArray.release();
        return flag;
    }

    public void init (int numberOfDocks, int seawayCapacity) {
        usingArray = new Semaphore( 1 );
        channelSem = new Semaphore( seawayCapacity, true );
        docksArray = new Ship[numberOfDocks];
    }

    public void requestSeawayEntrance (Ship s) throws InterruptedException {
        while ( !fillDocks(s) );
        channelSem.acquire();
    }

    public int requestPortEntrance (Ship s) throws InterruptedException {
        usingArray.acquire();

        int i = 0;
        while (i < docksArray.length && docksArray[i] != s)  i++;

        if (i == docksArray.length)  return -1;

        usingArray.release();
        return i;
    }

    public void signalPortEntered(Ship s) throws InterruptedException{
        channelSem.release();
    }

    void requestPortExit(Ship s) throws InterruptedException {
        channelSem.acquire();
    }

    void signalPortExited(Ship s) throws InterruptedException {
        usingArray.acquire();

        int index = s.getAssignedDock();
        for (int i = index; i < index + s.getDockingSize(); i++)
            docksArray[i] = null;

        usingArray.release();
    }

    void signalShipSailedAway(Ship s) throws InterruptedException {
        channelSem.release();
    }

}
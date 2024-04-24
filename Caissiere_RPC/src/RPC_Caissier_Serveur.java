import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class MonServiceRPCServeur extends UnicastRemoteObject implements MonServiceRPCInterface {
    private int currentID = 0;
    private HashMap<String, Boolean> checkoutCounterStatus;

    protected MonServiceRPCServeur() throws RemoteException {
        super();
        checkoutCounterStatus = new HashMap<>();
        // Initialize checkout counters as available
        checkoutCounterStatus.put("caisse1", false);
        checkoutCounterStatus.put("caisse2", false);
    }

    @Override
    public int getCurrentID() throws RemoteException {
        return currentID;
    }

    @Override
    public void incrementID() throws RemoteException {
        currentID++;
    }

    @Override
    public synchronized int requestClient(String caisseCode) throws RemoteException {
        if (checkoutCounterStatus.containsKey(caisseCode) && !checkoutCounterStatus.get(caisseCode)) {
            checkoutCounterStatus.put(caisseCode, true); 
            return ++currentID;
        } else {
            return 0; 
        }
        
        @Override
        public synchronized void clientProcessed(String caisseCode) throws RemoteException {
            if (checkoutCounterStatus.containsKey(caisseCode)) {
                checkoutCounterStatus.put(caisseCode, false); 
            }
        }

         public boolean isCaisseOccupee(String caisseCode) throws RemoteException {
            return checkoutCounterStatus.containsKey(caisseCode) && checkoutCounterStatus.get(caisseCode);
        }

        public static void main(String[] args) {
            try {
                MonServiceRPCServeur monService = new MonServiceRPCServeur();
                Registry registry = LocateRegistry.createRegistry(1099);
                registry.rebind("MonServiceRPC", monService);
                System.out.println("Serveur RMI prêt.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    }
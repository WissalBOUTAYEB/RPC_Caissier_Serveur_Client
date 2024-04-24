import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MonServiceRPCInterface extends Remote {
    int getCurrentID() throws RemoteException;
    void incrementID() throws RemoteException;
    int requestClient(String caisseCode) throws RemoteException;
    void clientProcessed(String caisseCode) throws RemoteException;
    boolean isCaisseOccupee(String caisseCode) throws RemoteException; 
}
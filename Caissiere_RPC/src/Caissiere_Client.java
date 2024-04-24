import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class CaisseClient {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: CaisseClient <adresse_ip_serveur>");
            System.exit(1);
        }

        String serverAddress = args[0];
        int clientID = 0; 
        try {
            Registry registry = LocateRegistry.getRegistry(serverAddress, 1099);
            MonServiceRPCInterface monService = (MonServiceRPCInterface) registry.lookup("MonServiceRPC");


            Scanner scanner = new Scanner(System.in);
            System.out.println("Nom de la caisse : ");
            String caisseCode = scanner.nextLine();


            boolean caisseOccupee = monService.isCaisseOccupee(caisseCode);

            if (caisseOccupee) {
                System.out.println("La caisse " + caisseCode + " est déjà occupée par un client.");
            } else {

                clientID = monService.requestClient(caisseCode); 
                System.out.println("L'id de client est: " + clientID);

                if (clientID != 0) {
                    System.out.println("Client " + clientID + " assigné à la caisse " + caisseCode);
                    Thread.sleep(5000); 


                    monService.clientProcessed(caisseCode);
                    System.out.println("Client " + clientID + " traité à la caisse " + caisseCode);
                } else {
                    System.out.println("Aucun client disponible pour la caisse " + caisseCode);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
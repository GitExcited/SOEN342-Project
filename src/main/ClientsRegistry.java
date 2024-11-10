package main;


import java.util.ArrayList;
import java.util.List;

public class ClientsRegistry {
    private List<Client> clients;

    // Constructor
    public ClientsRegistry() {
        this.clients = new ArrayList<>();
    }

    /**
     * Adds a new client to the collection.
     * 
     * @param client The client to be added.
     */
    public void addClient(Client client) {
        clients.add(client);
    }

    /**
     * Removes a client from the collection.
     * 
     * @param client The client to be removed.
     */
    public void removeClient(Client client) {
        clients.remove(client);
    }

    /**
     * Retrieves the list of clients.
     * 
     * @return The list of clients.
     */
    public List<Client> getClients() {
        return clients;
    }

    public Client getClientbyName(String name){
        for (Client client : clients) {
            //System.out.println("Debug client name: "+client.getName());
            if (client.getName().trim().equals(name.trim())){
                return client;
            }
        }
        return null;
    }

    public Client getClientbyPhoneNumber(String phoneNumber){
        for (Client client : clients) {
            if (client.getPhoneNumber() == phoneNumber){
                return client;
            }
        }
        return null;
    }

    public String getAllClientsDescriptions(){
        StringBuilder description = new StringBuilder("");
        for (Client client : clients) {
            description.append(client.toString()+ " \n");
        }
        return description.toString();
    }

    public boolean deleteClient(String id) {
        Client clientToRemove = null;
        for (Client client : clients) {
            if(client.getID().trim().equals(id.trim())){
                clientToRemove = client;
                break;
            }
        }
        if (clientToRemove == null){
            return false;
        }else{
            removeClient(clientToRemove);
            return true;
        }
    }

    public Client getClientbyId(String Id) {
        for (Client client : clients) {
            if (client.getID().trim().equals(Id.trim())){
                return client;
            }
        }
        return null;
    }

    public String getAllResponsibleChildrenByGuardianId(String id) {
        StringBuilder sb = new StringBuilder();
        for (Client c: clients) {
            if(c instanceof UnderAgeClient){
                UnderAgeClient uClient = (UnderAgeClient) c;
                if(uClient.getGuardian().trim().equals(id.trim())){
                    sb.append(c.toString()).append("\n");
                    break;
                }
            }
        }
        return sb.toString();
    }
}
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

    public Client getClientbyUsername(String username){
        for (Client client : clients) {
            if (client.getName() == username){
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
            if(client.getID() == id){
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
}
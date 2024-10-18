package main;


import java.util.ArrayList;
import java.util.List;

public class Clients {
    private List<Client> clients;

    // Constructor
    public Clients() {
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
}
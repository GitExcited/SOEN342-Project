package main;


import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tdg.ClientTDG;

public class ClientsRegistry {
    private List<Client> clientCollection = new ArrayList<>();
    private ClientTDG clientTDG;

    // Constructor
    public ClientsRegistry() {
        this.clientTDG= new ClientTDG();
        try {
            clientTDG.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Adds a new client to the collection.
     * 
     * @param client The client to be added.
     */
    public void addClient(Client client) {
        clientCollection.add(client);
        try {
            clientTDG.insert(client.toParams());
        } catch (NoSuchAlgorithmException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a client from the collection.
     * 
     * @param client The client to be removed.
     */
    public void removeClient(Client client) {
        clientCollection.remove(client);
        try {
            clientTDG.delete(client.getID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateClient(int ClientId, Client updatedClient ){
        Client oldClient = clientCollection.get(ClientId);
        
        updatedClient.setID(oldClient.getID());
        clientCollection.set(ClientId, updatedClient);

        try {
            clientTDG.update(updatedClient.toParams());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Retrieves the list of clients.
     * 
     * @return The list of clients.
     */
    public List<Client> getClientCollection() {
        return clientCollection;
    }

    public Client getClientbyName(String name){
        for (Client client : clientCollection) {
            //System.out.println("Debug client name: "+client.getName());
            if (client.getName().trim().equals(name.trim())){
                return client;
            }
        }
        return null;
    }

    public Client getClientbyPhoneNumber(String phoneNumber){
        for (Client client : clientCollection) {
            if (client.getPhoneNumber() == phoneNumber){
                return client;
            }
        }
        return null;
    }

    public String getAllClientsDescriptions(){
        StringBuilder description = new StringBuilder("");
        for (Client client : clientCollection) {
            description.append(client.toString()+ " \n");
        }
        return description.toString();
    }

    public boolean deleteClient(String id) {
        Client clientToRemove = null;
        for (Client client : clientCollection) {
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
        for (Client client : clientCollection) {
            if (client.getID().trim().equals(Id.trim())){
                return client;
            }
        }
        return null;
    }

    public String getAllResponsibleChildrenByGuardianId(String id) {
        StringBuilder sb = new StringBuilder();
        for (Client c: clientCollection) {
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
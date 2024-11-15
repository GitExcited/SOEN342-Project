package main;


import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import tdg.ClientTDG;

public class ClientsRegistry {
    private List<Client> clientCollection = new ArrayList<>();
    private ClientTDG clientTDG;

    //* Constructor
    public ClientsRegistry() {
        this.clientTDG= new ClientTDG();
        try {
            clientTDG.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //* INITIALIZE operation */

    /**Only called by AppSystem to bring a client from the Database to the collection
     * @param client The client to be initialized from db
     */
    public void initializeClient(Client client){
        if (!clientCollection.contains(client)) {
            clientCollection.add(client);
        }
    }

    //* CREATE, UPDATE and DELETE Operations

    /**
     * Adds a new client to the collection.
     * 
     * @param client The client to be added.
     */
    public void createClient(Client client) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();

        clientCollection.add(client);
        try {
            clientTDG.insert(client.toParams());
        } catch (NoSuchAlgorithmException | SQLException e) {
            e.printStackTrace();
        }finally {
            //? Unlock
            writeLock.unlock();
        }
    }

    /**
     * Removes a client from the collection.
     * 
     * @param client The client to be removed.
     */
    public void deleteClient(Client client) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();

        clientCollection.remove(client);
        try {
            clientTDG.delete(client.getID());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //? Unlock
            writeLock.unlock();
        }
    }

    public void updateClient(int ClientId, Client updatedClient ){
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();

        Client oldClient = clientCollection.get(ClientId);
        
        updatedClient.setID(oldClient.getID());
        clientCollection.set(ClientId, updatedClient);

        try {
            clientTDG.update(updatedClient.toParams());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //? Unlock 
            writeLock.unlock();
        }

    }

    public boolean deleteClient(String id) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();

        try{
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
                deleteClient(clientToRemove);
                return true;
            }
        }finally {
            //? Unlock 
            writeLock.unlock();
        }
    }
    //* READ operations
    /**
     * Retrieves the list of clients.
     * 
     * @return The list of clients.
     */
    public List<Client> getClientCollection() {
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try {
        return clientCollection;
        }finally {
            //? Unlock
            readLock.unlock();
        }
    }

    public Client getClientbyName(String name){
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try {
        for (Client client : clientCollection) {
            //System.out.println("Debug client name: "+client.getName());
            if (client.getName().trim().equals(name.trim())){
                return client;
            }
        }
        return null;}finally {
            //? Unlock
            readLock.unlock();
        }
    }

    public Client getClientbyPhoneNumber(String phoneNumber){
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try {
        for (Client client : clientCollection) {
            if (client.getPhoneNumber() == phoneNumber){
                return client;
            }
        }
        return null;}finally {
            //? Unlock
            readLock.unlock();
        }
    }

    public String getAllClientsDescriptions(){
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try {
        StringBuilder description = new StringBuilder("");
        for (Client client : clientCollection) {
            description.append(client.toString()+ " \n");
        }
        return description.toString();}finally {
            //? Unlock
            readLock.unlock();
        }
    }

    public Client getClientbyId(String Id) {
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try {
        for (Client client : clientCollection) {
            if (client.getID().trim().equals(Id.trim())){
                return client;
            }
        }
        return null;}finally {
            //? Unlock
            readLock.unlock();
        }
    }

    public String getAllResponsibleChildrenByGuardianId(String id) {
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try {
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
        return sb.toString();}finally {
            //? Unlock
            readLock.unlock();
        }
    }

    public byte[] getSaltByClientId(String clientId) throws SQLException {
         //? Readers operate in mutual exclusion with writers
         ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
         readLock.lock();
         try {
        return clientTDG.getSaltByClientId(clientId);}finally {
            //? Unlock
            readLock.unlock();
        }
    }

    public String getHashedPasswordByClientId(String clientId) throws SQLException {
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try {
        return clientTDG.getHashedPasswordByClientId(clientId);}finally {
            //? Unlock
            readLock.unlock();
        }
    }
    
}
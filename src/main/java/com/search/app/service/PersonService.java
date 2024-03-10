package com.search.app.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import org.springframework.context.annotation.Configuration;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;
import com.search.app.client.TableClientProvider;
import com.search.app.dto.PersonAzure;

@Configuration
public class PersonService {

    protected static CloudTableClient tableClientPerson = null;
    protected static CloudTable tablePerson = null;
    protected final static String tableNamePrefix = "Person";

    public void insertPersonRecord(PersonAzure person) throws Exception {
        tableClientPerson = TableClientProvider.getTableClientReference();
        tablePerson = createTableIfNotExist(tableClientPerson,tableNamePrefix);
        System.out.println("\tSuccessfully created the table.");
        PersonAzure personEntity = new PersonAzure(person.getEmail(),person.getPassword());
        System.err.println("PartionKey "+personEntity.getPartitionKey());
        System.err.println("RowKey "+personEntity.getRowKey());
        tablePerson.execute(TableOperation.insert(personEntity));
        System.out.println("\tSuccessfully inserted the new entities.");
    }

     private static CloudTable createTableIfNotExist(CloudTableClient tableClient, String tableName) throws StorageException, RuntimeException, IOException, InvalidKeyException, IllegalArgumentException, URISyntaxException, IllegalStateException {

        // Create a new table
        CloudTable table = tableClient.getTableReference(tableName);
        try {
            if(table.exists() == true){
                System.err.println("table exist with name "+table.getName());
                return table;
            }
            if(table.exists() == false){
                throw new IllegalStateException(String.format("Table with name \"%s\" doesnot exists.", tableName));
            }
            if (table.createIfNotExists() == false) {
                
                throw new IllegalStateException(String.format("Table with name \"%s\" already exists.", tableName));
            }
        }
        catch (StorageException s) {
            if (s.getCause() instanceof java.net.ConnectException) {
                System.out.println("Caught connection exception from the client. If running with the default configuration please make sure you have started the storage emulator.");
            }
            throw s;
        }

        return table;
    }
}

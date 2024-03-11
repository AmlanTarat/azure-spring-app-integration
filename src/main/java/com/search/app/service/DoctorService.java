package com.search.app.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;
import com.microsoft.azure.storage.table.TableQuery;
import com.microsoft.azure.storage.table.TableQuery.QueryComparisons;
import com.search.app.client.TableClientProvider;
import com.search.app.dto.DoctorAzureEntity;

@Configuration
public class DoctorService {

    protected static CloudTableClient tableClientDoctor = null;
    protected static CloudTable tableDoctor = null;
    protected final static String tableNamePrefix = "Doctor";

    public void insertDoctorRecord(DoctorAzureEntity doctor) throws Exception {
        tableClientDoctor = TableClientProvider.getTableClientReference();
        tableDoctor = createTableIfNotExist(tableClientDoctor,tableNamePrefix);
        System.out.println("\tSuccessfully created the table.");
        DoctorAzureEntity doctorEntity = new DoctorAzureEntity(doctor.getEmail(),doctor.getPassword());
        doctorEntity.setCity(doctor.getCity());
        doctorEntity.setDegree(doctor.getDegree());
        doctorEntity.setEmail(doctor.getEmail());
        doctorEntity.setName(doctor.getName());
        doctorEntity.setPassword(doctor.getPassword());
        doctorEntity.setSpecialization(doctor.getSpecialization());
        doctorEntity.setState(doctor.getState());
        System.err.println("PartionKey "+doctorEntity.getPartitionKey());
        System.err.println("RowKey "+doctorEntity.getRowKey());
        tableDoctor.execute(TableOperation.insert(doctorEntity));
        System.out.println("\tSuccessfully inserted the new entities.");
    }

    public boolean searchDoctorRecordbyId(DoctorAzureEntity doctor) throws Exception {
        tableClientDoctor = TableClientProvider.getTableClientReference();
        tableDoctor = createTableIfNotExist(tableClientDoctor,tableNamePrefix);
        System.out.println("\tSuccessfully logged in to the table.");
        DoctorAzureEntity doctorEntity = new DoctorAzureEntity(doctor.getEmail(), doctor.getPassword());
        TableOperation retrieveDoctor = TableOperation.retrieve(doctorEntity.getPartitionKey(), doctorEntity.getRowKey(), DoctorAzureEntity.class);
        DoctorAzureEntity doctorValid = tableDoctor.execute(retrieveDoctor).getResultAsType();
        if(doctorValid != null){
            System.out.println("Retrieved Doctor details from table storage with email id "+doctorValid.getPartitionKey());
            System.out.println("\tSuccessfully retreived the patient entities.");
            return true;
        }
        
        return false;
    }

    public List<DoctorAzureEntity> searchAllDoctorRecord() throws Exception {
        tableClientDoctor = TableClientProvider.getTableClientReference();
        tableDoctor = createTableIfNotExist(tableClientDoctor,tableNamePrefix);
        System.out.println("\tSuccessfully logged in to the table.");
        String partitionFilter =TableQuery.generateFilterCondition("Timestamp", QueryComparisons.LESS_THAN, new Timestamp(System.currentTimeMillis()));
        TableQuery<DoctorAzureEntity> partitionQuery = TableQuery.from(DoctorAzureEntity.class).where(partitionFilter);
        List<DoctorAzureEntity> doctorEntityList = new ArrayList<>();
        for (DoctorAzureEntity entity : tableDoctor.execute(partitionQuery)) {
        doctorEntityList.add(entity);
        System.out.println(entity.getPartitionKey() +
            " " + entity.getRowKey() +
            "\t" + entity.getName() +
            "\t" + entity.getSpecialization());
        }
        System.out.println("\tSuccessfully retreived the doctor entities.");
        return doctorEntityList;
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

package com.search.app.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.context.annotation.Configuration;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;
import com.microsoft.azure.storage.table.TableQuery;
import com.microsoft.azure.storage.table.TableQuery.Operators;
import com.microsoft.azure.storage.table.TableQuery.QueryComparisons;
import com.search.app.client.TableClientProvider;
import com.search.app.dto.AppointmentAzureDelete;
import com.search.app.dto.AppointmentAzureEntity;

@Configuration
public class AppointmentService {

    protected static CloudTableClient tableClientAppointment = null;
    protected static CloudTable tableAppointment = null;
    protected final static String tableNamePrefix = "Appointment";

    public void insertAppointmentRecord(AppointmentAzureEntity appointment) throws Exception {
        tableClientAppointment = TableClientProvider.getTableClientReference();
        tableAppointment = createTableIfNotExist(tableClientAppointment,tableNamePrefix);
        System.out.println("\tSuccessfully created the table.");
        Integer randomNumber = ThreadLocalRandom.current().nextInt(100, 1000);
        AppointmentAzureEntity appointmentEntity = new AppointmentAzureEntity(randomNumber.toString(),"Active");
        appointmentEntity.setEmail(appointment.getEmail());
        appointmentEntity.setDate(appointment.getDate());
        appointmentEntity.setDocId(appointment.getDocId());
        appointmentEntity.setDocName(appointment.getDocName());
        appointmentEntity.setDocSpecial(appointment.getDocSpecial());
        System.err.println("Random PartionKey "+appointmentEntity.getPartitionKey());
        System.err.println("RowKey "+appointmentEntity.getRowKey());
        tableAppointment.execute(TableOperation.insert(appointmentEntity));
        System.out.println("\tSuccessfully inserted the new appointment entities.");
    }

    public List<AppointmentAzureEntity> findPatientListByDoctorId(String docId) throws Exception {
        tableClientAppointment = TableClientProvider.getTableClientReference();
        tableAppointment = createTableIfNotExist(tableClientAppointment,tableNamePrefix);
        System.out.println("\tSuccessfully logged in to the table.");
        String partitionFilter =TableQuery.generateFilterCondition("DocId", QueryComparisons.EQUAL, docId);
        TableQuery<AppointmentAzureEntity> partitionQuery = TableQuery.from(AppointmentAzureEntity.class).where(partitionFilter);
        List<AppointmentAzureEntity> appointmentEntityList = new ArrayList<>();
        for (AppointmentAzureEntity entity : tableAppointment.execute(partitionQuery)) {
            appointmentEntityList.add(entity);
        System.out.println(entity.getPartitionKey() +
            " " + entity.getRowKey() +
            "\t" + entity.getAppId() +
            "\t" + entity.getEmail());
        }
        System.out.println("\tSuccessfully retreived the patient list entities.");
        return appointmentEntityList;
    }

    public List<AppointmentAzureEntity> findAppointmentListByEmailId(String emailId) throws Exception {
        tableClientAppointment = TableClientProvider.getTableClientReference();
        tableAppointment = createTableIfNotExist(tableClientAppointment,tableNamePrefix);
        System.out.println("\tSuccessfully logged in to the table.");
        String partitionFilter =TableQuery.generateFilterCondition("Email", QueryComparisons.EQUAL, emailId);
        TableQuery<AppointmentAzureEntity> partitionQuery = TableQuery.from(AppointmentAzureEntity.class).where(partitionFilter);
        List<AppointmentAzureEntity> appointmentEntityList = new ArrayList<>();
        for (AppointmentAzureEntity entity : tableAppointment.execute(partitionQuery)) {
            appointmentEntityList.add(entity);
        System.out.println(entity.getPartitionKey() +
            " " + entity.getRowKey() +
            "\t" + entity.getAppId() +
            "\t" + entity.getEmail());
        }
        System.out.println("\tSuccessfully retreived the patient list entities.");
        return appointmentEntityList;
    }

    public void deleteAppointmentListByEmailIdAndDate(AppointmentAzureDelete appDelete) throws Exception {
        tableClientAppointment = TableClientProvider.getTableClientReference();
        tableAppointment = createTableIfNotExist(tableClientAppointment,tableNamePrefix);
        System.out.println("\tSuccessfully logged in to the table.");
        String partitionFilterEmail =TableQuery.generateFilterCondition("Email", QueryComparisons.EQUAL, appDelete.getEmail());
        String partitionFilterDate =TableQuery.generateFilterCondition("Date", QueryComparisons.EQUAL, appDelete.getDate());
        String combinedPartitionFilter = TableQuery.combineFilters(partitionFilterEmail, Operators.AND, partitionFilterDate);
        TableQuery<AppointmentAzureEntity> partitionQuery = TableQuery.from(AppointmentAzureEntity.class).where(combinedPartitionFilter);
        for (AppointmentAzureEntity entity : tableAppointment.execute(partitionQuery)) {
            TableOperation deleteEntity = TableOperation.delete(entity);
            tableAppointment.execute(deleteEntity);
            System.out.println("\tSuccessfully deleted the appointment list entities.");
        }
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

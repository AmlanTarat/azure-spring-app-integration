package com.search.app.client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.table.CloudTableClient;

@Component
public class TableClientProvider {

    @Value("${storageConnectionString}")
    public static String connectionString;

    public static String conString = "DefaultEndpointsProtocol=https;AccountName=52065057atula9be;AccountKey=EiiAQPg3dYG/MkhB8/NjWJeZkAbDtj5A0DC0RFiHYu4XSr/qSaDMZMxgLuIfJ1un/kJQiq3CHFhE+ASt/1sZzA==;EndpointSuffix=core.windows.net";

    public static CloudTableClient getTableClientReference() throws RuntimeException, IOException, URISyntaxException, InvalidKeyException {
        CloudStorageAccount storageAccount;
        try {
            System.err.println("ConnectionString"+connectionString);
            storageAccount = CloudStorageAccount.parse(conString);
        } catch (IllegalArgumentException | URISyntaxException e) {
            System.out.println("\nConnection string specifies an invalid URI.");
            System.out.println("Please confirm the connection string is in the Azure connection string format.");
            throw e;
        } catch (InvalidKeyException e) {
            System.out.println("\nConnection string specifies an invalid key.");
            System.out.println("Please confirm the AccountName and AccountKey in the connection string are valid.");
            throw e;
        }

        return storageAccount.createCloudTableClient();
    }
}

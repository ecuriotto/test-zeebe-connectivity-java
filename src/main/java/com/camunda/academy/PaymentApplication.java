package com.camunda.academy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.BrokerInfo;
import io.camunda.zeebe.client.api.response.PartitionInfo;

import java.util.List;

@SpringBootApplication
public class PaymentApplication implements CommandLineRunner {

    @Autowired
    private ZeebeClient client;

    public static void main(final String... args) {
        SpringApplication.run(PaymentApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Retrieve brokers information
        List<BrokerInfo> brokers = client.newTopologyRequest()
            .send()
            .join()
            .getBrokers();
        
        // Iterate through brokers and print information
        for (BrokerInfo broker : brokers) {
            System.out.println("Broker ID: " + broker.getNodeId());
            System.out.println("Broker Address: " + broker.getAddress());

            List<PartitionInfo> partitions = broker.getPartitions();
            for (PartitionInfo partition : partitions) {
                System.out.println("Partition ID: " + partition.getPartitionId());
                System.out.println("Is Leader: " + partition.isLeader());
            }
        }
    }
}

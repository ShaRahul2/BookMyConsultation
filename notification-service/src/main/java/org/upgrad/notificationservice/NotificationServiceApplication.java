package org.upgrad.notificationservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.upgrad.notificationservice.consumer.ConsumerRun;

public class NotificationServiceApplication {

	public static void main(String[] args) {
		{
			int numConsumers = 1;
			String groupId = "upgrad-group";
			List<String> topics = Arrays.asList("newRahul");
			ExecutorService executor = Executors.newFixedThreadPool(numConsumers);

			final List<ConsumerRun> consumers = new ArrayList<>();
			for (int i = 0; i < numConsumers; i++) {
				ConsumerRun consumer = new ConsumerRun(i, groupId, topics);
				consumers.add(consumer);
				executor.submit(consumer);
			}

			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					for (ConsumerRun consumer : consumers) {
						consumer.shutdown();
					}
					executor.shutdown();
					try {
						executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
					} catch (InterruptedException e) {
						System.out.println(e.getMessage());
						e.getMessage();
					}
				}
			});
		}
	}
}

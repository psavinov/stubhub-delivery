package com.stubhub.delivery.simulation;

import com.stubhub.delivery.model.Postwoman;
import com.stubhub.delivery.queue.DeliveryQueue;
import com.stubhub.delivery.util.DeliveryQueueProvider;
import com.stubhub.delivery.util.PostwomanProviderUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Simulation app entry point.
 *
 * @author Pavel Savinov
 */
public class Simulation {

	public static void main(String args[]) throws Exception {
		DeliveryQueue deliveryQueue =
			DeliveryQueueProvider.getInstance().createDeliveryQueue(System.in);

		// Initialize worker threads

		List<Thread> threads = _initializeSimulationWorkers(deliveryQueue);

		// Keep processing the parcels while delivery queue is not empty and
		// worker threads are alive.

		while (!threads.isEmpty()) {
			for (Thread thread : threads) {
				if (!thread.isAlive()) {
					threads.remove(thread);
				}
			}
		}

		boolean allProcessed = deliveryQueue.allParcelsProcessed();

		while (!allProcessed) {
			allProcessed = deliveryQueue.allParcelsProcessed();
		}

		// Stop parcels arrival

		deliveryQueue.stopArrival();
	}

	private static List<Thread> _initializeSimulationWorkers(
		DeliveryQueue deliveryQueue)
		throws Exception {

		List<Thread> threads = new CopyOnWriteArrayList<>();

		Postwoman postwoman = PostwomanProviderUtil.getPostwoman();

		// Available simulation parameters, as described in the HOW-TO-RUN file

		int threadCount = Integer.parseInt(
			System.getProperty("delivery.simulation.thread.count", "1"));

		long simulationTime = Long.parseLong(
			System.getProperty("delivery.simulation.time.seconds", "15"));

		System.out.println(simulationTime);

		int destinationCount = Integer.parseInt(
			System.getProperty("delivery.simulation.destination.count", "5"));

		// Launch worker threads with appropriate parameters

		for (int k = 0; k < threadCount; k++) {
			Thread workerThread = new Thread(
				new SimulationWorker(
					deliveryQueue, TimeUnit.SECONDS.toMillis(simulationTime),
					destinationCount));

			workerThread.start();

			threads.add(workerThread);
		}

		deliveryQueue.deliverParcels(postwoman);

		return threads;
	}

}
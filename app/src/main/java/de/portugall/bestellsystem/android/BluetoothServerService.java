package de.portugall.bestellsystem.android;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import de.portugall.bestellsystem.android.data.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BluetoothServerService extends Service {

	private static final String TAG = "BluetoothServerService";
	private final ExecutorService executorService = Executors.newFixedThreadPool(2);
	private VerkaufRepository verkaufRepo;
	private final Queue<String> messageQueue = new LinkedList<>();
	private BluetoothServerSocket serverSocket;

	@Override
	public void onCreate() {
		BluetoothServerSocket tmp = null;
		try {
			UUID uuid = UUID.fromString("01712bb4-aea4-4d50-bdcc-32bee3d5cfe3");
			tmp = BluetoothAdapter.getDefaultAdapter().listenUsingRfcommWithServiceRecord("BestellSystem", uuid);
		} catch (IOException e) {
			Log.e(TAG, "onCreate() failed while open the ServerSocket: ", e);
		}
		serverSocket = tmp;

		verkaufRepo = new VerkaufRepository(this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		executorService.execute(() -> {
			BluetoothSocket socket;
			while (true) {
				try {
					socket = serverSocket.accept();
					if (null != socket) {
						InputStream inputStream = socket.getInputStream();
						while (true) {
							try {
								byte[] buffer = new byte[1024];
								inputStream.read(buffer);
								String message = new String(buffer, StandardCharsets.UTF_8);

								verkaufRepo.insert(VerkaufWithPositionen.fromJson(message));
								// TODO Notification, wenn die Activity nicht sichtbar ist?

//								if (null != boundListener) {
//									// Da gerade eine Activity an diesen Service gebunden ist, kann sie direkt über die
//									// neue Message informiert werden
//									boundListener.accept(message);
//								} else {
//									// Gerade ist keine Activity an diesen Service gebunden. Der Listener ist null.
//									// Einkommende Nachrichten in ihrere Reihenfolge speichern, damit sie bei einem bound
//									// alle zu Verfügung gestellt werden können.
//									messageQueue.add(message);
//								}
							} catch (IOException e) {
								Log.e(TAG, "Background Task failed while reading the InputStream: ", e);
								break;
							}

							if (Thread.interrupted()) {
								inputStream.close();
								return;
							}
						}
					}
				} catch (IOException e) {
					Log.e(TAG, "Background Task failed while accept() from ServerSocket: ", e);
					break;
				}
			}
		});
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		try {
			executorService.shutdownNow();
			serverSocket.close();
		} catch (IOException e) {
			Log.e(TAG, "onDestroy() failed while closing the ServerSocket: ", e);
		}
	}

}

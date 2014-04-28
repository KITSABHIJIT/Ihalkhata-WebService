package com.exp.cemk.AndroidNotification;

import java.util.ArrayList;
import java.util.List;

import com.exp.cemk.constants.GCMBroadCastConstants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

/**
 * Servlet implementation class GCMBroadcast
 */

public class GCMBroadcast {
	private static GCMBroadcast _instance = new GCMBroadcast();

	public static GCMBroadcast getInstance() {
		// log.debug("AutoFillDemo::getInstance ");

		return _instance;
	}

	// The SENDER_ID here is the "Browser Key" that was generated when I
	// created the API keys for my Google APIs project.

	public void sendBroadCastMessage(String userMessage,
			List<String> androidTargets) {

		String collapseKey = "CollapseKey";

		// Instance of com.android.gcm.server.Sender, that does the
		// transmission of a Message to the Google Cloud Messaging service.
		Sender sender = new Sender(GCMBroadCastConstants.SENDER_ID);

		// This Message object will hold the data that is being transmitted
		// to the Android client devices. For this demo, it is a simple text
		// string, but could certainly be a JSON object.
		Message message = new Message.Builder()

				// If multiple messages are sent using the same .collapseKey()
				// the android target device, if it was offline during earlier
				// message
				// transmissions, will only receive the latest message for that
				// key when
				// it goes back on-line.
				.collapseKey(collapseKey).timeToLive(30).delayWhileIdle(true)
				.addData("message", userMessage).build();
		System.out.println("Broadcast Message: " + userMessage);
		System.out.println("SENDER_ID: " + GCMBroadCastConstants.SENDER_ID);
		for (String s : androidTargets)
			System.out.println("androidTargets: " + s);
		try {
			// use this for multicast messages. The second parameter
			// of sender.send() will need to be an array of register ids.
			MulticastResult result = sender.send(message, androidTargets, 1);

			if (result.getResults() != null) {
				int canonicalRegId = result.getCanonicalIds();
				if (canonicalRegId != 0) {

				}
				System.out.println("Broadcast Succesfull: " + canonicalRegId);
			} else {
				int error = result.getFailure();
				System.out.println("Broadcast failure: " + error);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		List<String> androidTargets = new ArrayList<String>();
		//ABHIJIT
		androidTargets.add("APA91bHz41JYp3jHXLdBCw88DOnWQDk2yaaeyoe5Fthdr0IFd_51jbWd9GaVKo8f6K1rKNJm5a28qrJGG4TIh2U24SUVdzng3YgARuiVTOlbYqNpR2YoZXA4F2dBmLIpWME7g2nFENEs6pOxI7cb0KTBkap7clTbf1Hw7LaLDpQfAvf2yO1Urdg");
		//ARANI
		//androidTargets.add("APA91bHfuJ27Z36tzQN-VSQwBxQJxwYO6OfO3hj7MlneMRLM1g_LTZxNWG5rlT1p0lh0tYluhEy9jpfvI7VUPJzW9Eayjv-2Z88e9XjN2qUeylfwdKUX0_uAO6h_2pxBA-o0Ua0oxzCek75Kkxf2UExAaBO3a0qP28rKdLIcStupa123JRFQOOY");
		//PABITRA
		//androidTargets.add("APA91bFIoPUtn121pz1jjJOhgjOF1Dv3z8M2xbDbcWB-oJo9Ot9PxIukxA9WRxUXZ74Ve_DX3WuofgAXxYZJQzXEC3uFaFhYyLpaFLSEyKig5Jos7iIrLNxm6PPZocEZs9tG5JbOtN4ga_vGbGxyVErP05V3WvdP6v04xRbKDBt57riEnQYF_bU");
		//SANDIPAN
		//androidTargets.add("APA91bGH5TbNPwVx6bsM9rRC7qhWwGZJf7r3pdigegHFM_SBMUtijmBpEuGnZOFsX6mkMsyG_GQl5-y19anGqTgCCThauIf-9V1T_hFn-V8_fz2E6JA6BwTfx5uyrz9N6W2Y7DGxaVQV1F1MScAkUV9kN1VBdGhGLg");
		//SENA
		//androidTargets.add("APA91bE-_NPiaGB5SeT31vK0HNCvXA30RcMTcBsWn3AgLJtkAZjOHYogpqD3FrVfNeX3lAi3X8ywnA5JQ6BwgJmUpj_pMw8lviRUi6P7wMoFvrJxvAQK2TFjBlebS-aLVBAVc7Wpbkmnel11c5L9vrhO-x_Wo0Zk9yMDQP6Yp1tRVh_jfIx6poo");
		//androidTargets.add(GCMBroadCastConstants.ANDROID_DEVICE);
		//androidTargets.add(GCMBroadCastConstants.ANDROID_DEVICE);
		//androidTargets.add(GCMBroadCastConstants.ANDROID_DEVICE);
		//String msg = "{\"date\":\"22/11/2013\",\"item\":\"WEEKLY MARKETTING\",\"desc\":\"jfdnkkc\",\"amount\":\"5887.50\",\"perHead\":\"1177.50\",\"shareholderCount\":\"5\",\"paidBy\":\"Sandipan\",\"paidById\":\"sandipan\",\"shareHolders\":\"Abhijit,Arani,Pabitra,Sandipan,Sayan\",\"notiUsers\":\"abhijit,arani,pabitra,sandipan,sayan\"}";
		String msg = "{\"msg\":\"Enjoy the new features by updating your Ihalkhata App from www.ihalkhata.com.\"}";
		GCMBroadcast.getInstance().sendBroadCastMessage(msg, androidTargets);
	}

}

package com.exp.cemk.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import com.exp.cemk.controller.UserDataProcessor;

import test.Test;
import domainmodel.Notification;
import domainmodel.Person;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CommonUtil {
	private static final int IMG_WIDTH = 128;
	private static final int IMG_HEIGHT = 128;
	private static final Logger logger = Logger.getLogger(CommonUtil.class);

	public static String getURIPrefix(HttpServletRequest request) {
		String url = "http://";
		if (request.isSecure()) {
			url = "https://";
		}
		String host = request.getServerName();

		url = url + host + ":" + request.getServerPort()
				+ request.getContextPath();
		return url;
	}

	public static File getFileFromBytes(File file, byte[] byteArray)
			throws IOException {
		BufferedOutputStream bos = null;
		try {
			// create an object of FileOutputStream
			FileOutputStream fos = new FileOutputStream(file);
			// create an object of BufferedOutputStream
			bos = new BufferedOutputStream(fos);
			bos.write(byteArray);
			// System.out.println("File written");

		} catch (FileNotFoundException fnfe) {
			System.out.println("Specified file not found" + fnfe);
		} catch (IOException ioe) {
			System.out.println("Error while writing file" + ioe);
		} finally {
			if (bos != null) {
				try {

					// flush the BufferedOutputStream
					bos.flush();
					// close the BufferedOutputStream
					bos.close();
				} catch (Exception e) {
				}
			}
		}
		return file;

	}

	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	public static BufferedImage resizeImage(BufferedImage originalImage,
			int type) {
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT,
				type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();

		return resizedImage;
	}

	public static File resizeImageFile(File image) {
		try {
			BufferedImage originalImage = ImageIO.read(image);
			int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB
					: originalImage.getType();
			BufferedImage resizeImage = CommonUtil.resizeImage(originalImage,
					type);
			ImageIO.write(resizeImage, "png", image);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return image;
	}

	public static String getFromEnvironment(final String name) {
		if (name == null)
			return null;
		try {
			final Object object = ((Context) (new InitialContext()
					.lookup("java:comp/env"))).lookup(name);
			if (object != null)
				return object.toString();
		} catch (final Exception e) {
		}
		return System.getenv(name);
	}

	public static String getRoundedValue(double value) {
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(value);
	}

	public static String constructNotificationMsg(long itemId) {

		logger.info("Util-->constructNotificationMsg-->Spring Data Access-->getUserData");
		try {

			JSONObject notiMsg = new JSONObject();
			String notiUsers = "";
			String shareholders = "";
			List<Notification> notificationList = new ArrayList<Notification>();
			Test notificationListData = new Test();
			notificationList = notificationListData.getNotification(itemId);
			if (!notificationList.isEmpty()) {
				for (Notification noti : notificationList) {
					notiUsers = (notiUsers == "") ? notiUsers = noti
							.getShareholderId() : notiUsers + ","
							+ noti.getShareholderId();
					shareholders = (shareholders == "") ? shareholders = noti
							.getShareholderName() : shareholders + ","
							+ noti.getShareholderName();
					notiMsg.put("date", noti.getDate());
					notiMsg.put("item", noti.getItemList());
					notiMsg.put("desc", noti.getDesc());
					notiMsg.put("amount", noti.getPrice());
					notiMsg.put("perHead", noti.getPerHead());
					notiMsg.put("shareholderCount", noti.getCount());
					notiMsg.put("paidBy", noti.getPaidByName());
					notiMsg.put("paidById", noti.getPaidById());
				}
				notiMsg.put("shareHolders", shareholders);
				if (!notiUsers.contains(notiMsg.getString("paidById")))
					notiUsers += "," + notiMsg.getString("paidById");
				notiMsg.put("notiUsers", notiUsers);
			}
			return notiMsg.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static boolean isNullorEmpty(String testString) {
		if (testString == null || testString.trim().equals("") || testString.trim().equals("null"))
			return true;
		else
			return false;
	}
}

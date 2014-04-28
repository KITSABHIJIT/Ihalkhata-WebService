package com.exp.webMethods;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WebMethods {

	public static final String dataFileDirectory = System
			.getProperty("user.home")
			+ System.getProperty("file.separator")
			+ "USER_DATA";
	public static final String dataFileName = dataFileDirectory
			+ System.getProperty("file.separator") + "userData.txt";

	public static ReturnJson writeData(String data) {
		ReturnJson todo = new ReturnJson();
		try {
			File fileDirectory = new File(dataFileDirectory);
			if (!fileDirectory.exists()) {
				if (fileDirectory.mkdirs()) {
					System.out.println("Directory is created!");
				} else {
					System.out.println("Failed to create directory!");
				}
			}
			File file = new File(dataFileName);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("File is created!");
				} else {
					System.out.println("Failed to create File!");
				}
			}

			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(dataFileName, true)));
			out.println(data);
			out.close();
			todo.setSuccess(true);
			todo.setMsg("File updated successfully");

		} catch (Exception e) {
			todo.setSuccess(false);
			todo.setMsg("Failed to write on file");
			e.printStackTrace();
		}
		return todo;
	}

	public static ReturnJson readData() {
		ReturnJson todo = new ReturnJson();
		BufferedReader br = null;

		try {

			File file = new File(dataFileName);
			if (!file.exists()) {
				todo.setSuccess(false);
				todo.setMsg("File not present");
			} else {
				try {
					String FinalString = "";

					String sCurrentLine;

					br = new BufferedReader(new FileReader(dataFileName));

					while ((sCurrentLine = br.readLine()) != null) {
						System.out.println(sCurrentLine);
						FinalString = (FinalString.equals("")) ? sCurrentLine
								: FinalString + "\n" + sCurrentLine;
					}
					todo.setSuccess(true);
					todo.setMsg(FinalString);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (br != null)
							br.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			todo.setSuccess(false);
			todo.setMsg("Failed to write on file");
			e.printStackTrace();
		}
		return todo;
	}
}

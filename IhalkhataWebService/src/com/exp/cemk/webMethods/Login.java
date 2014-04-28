package com.exp.cemk.webMethods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.exp.cemk.controller.UserDataProcessor;
import com.exp.cemk.util.CryptoUtil;
import com.exp.cemk.util.ReturnJson;

import domainmodel.Person;

public class Login {
	private static final Logger logger = Logger.getLogger(Login.class);

	public static ReturnJson userLogin(String userName, String password) {
		password = CryptoUtil.getInstance().encrypt(password);
		List<Person> data = new ArrayList<Person>();
		ReturnJson todo = new ReturnJson();
		try {
			data = UserDataProcessor.getInstance().getLoginUser(userName,
					password);
			if (data.size() > 0 && !data.isEmpty()) {
				for (Person user : data) {
					if (userName.equals(user.getUserId())
							&& password.equals(user.getPassword())) {
						if ("Y".equals(user.getActiveFlag())) {

							if (user.getLastLoginTime() == null)
								user.setLastLoginTime(new SimpleDateFormat(
										"EE, dd MMM yyyy hh:mm aa")
										.format(new Date()));
							if (user.getFirstLoginFlag().equals("Y")) {
								todo.setSuccess(false);
								todo.setMsg("First login must be done in the Web Portal");

							} else {
								String loginTime = new SimpleDateFormat(
										"EE, dd MMM yyyy hh:mm aa")
										.format(new Date());
								int rowsAffected = UserDataProcessor
										.getInstance().updateLoginTime(
												loginTime, userName);
								if (rowsAffected > 0)
									logger.info("## " + userName
											+ ": Login time updated to "
											+ loginTime);
								else
									logger.info(userName
											+ ": Login time updaion failed");
								todo.setSuccess(true);
								JSONObject returnObj = new JSONObject();
								returnObj.put("userName", user.getUserName());
								returnObj.put("userId", user.getUserId());
								returnObj.put("email", user.getEmail());
								returnObj.put("company", user.getCompany());
								returnObj.put("groupId", user.getGroupId());
								todo.setMsg(returnObj.toString());

							}
						} else {
							todo.setSuccess(false);
							todo.setMsg("You are no longer a valid User");
						}
						break;
					} else {
						todo.setSuccess(false);
						todo.setMsg("Invalid User Name or Password");
					}
				}
			} else {
				todo.setSuccess(false);
				todo.setMsg("Invalid User Name or Password");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return todo;
	}
}

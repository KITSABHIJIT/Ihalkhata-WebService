package dao;

import dao.mapper.ExpenditureRowMapper;
import dao.mapper.ExpenseRowMapper;
import dao.mapper.IndividualDateExpenditureRowMapper;
import dao.mapper.IndividualExpenditureRowMapper;
import dao.mapper.IndividualExpenseRowMapper;
import dao.mapper.ItemsRowMapper;
import dao.mapper.LoginUserRowMapper;
import dao.mapper.NotificationRowMapper;
import dao.mapper.PaymentsRowMapper;
import dao.mapper.PersonRowMapper;
import domainmodel.Expenditure;
import domainmodel.ExpenseData;
import domainmodel.IndividualDateExpenditure;
import domainmodel.IndividualExpenditure;
import domainmodel.IndividualExpense;
import domainmodel.Notification;
import domainmodel.Payments;
import domainmodel.Person;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class DataRetrieveDao {

	private DataSource dataSource;

	public void setDataSource(DataSource ds) {
		dataSource = ds;
	}

	public String GET_TOTAL_RECORDS = new StringBuffer(
			"select distinct a.item_type as items,a.EXP_DATE as date,c.USER_NAME as giver,a.no_of_shareholder,d.USER_NAME,a.exp_amount,b.cost_to_individual,a.description")
			.append(" from RECORD_ENTRY_TABLE a,ITEM_SHAREHOLDER_RECORD b,USER_RECORD c,USER_RECORD d")
			.append(" where a.id=b.item_id and a.create_user_id=c.USER_ID  and b.user_id=d.USER_ID and c.ACTIVE_STATUS='Y' and c.GROUP_ID=? order by a.EXP_DATE desc")
			.toString();

	public String GET_TOTAL_EXPENDITURE_RECORDS = new StringBuffer(
			"SELECT SUM(COST_TO_INDIVIDUAL) as cost, c.USER_NAME")
			.append(" FROM ITEM_SHAREHOLDER_RECORD I, RECORD_ENTRY_TABLE R,USER_RECORD c")
			.append(" WHERE I.ITEM_ID = R.ID  and I.USER_ID=c.USER_ID AND R.EXP_DATE BETWEEN ? AND ?  "
					+ " and c.GROUP_ID=? and c.ACTIVE_STATUS='Y' group by I.USER_ID order by cost desc")
			.toString();

	public String GET_TOTAL_OFFERINGS_RECORDS = new StringBuffer(
			"SELECT SUM(EXP_AMOUNT) as cost, c.USER_NAME")
			.append(" FROM RECORD_ENTRY_TABLE R,USER_RECORD c")
			.append(" WHERE R.CREATE_USER_ID=c.USER_ID AND R.EXP_DATE BETWEEN ? AND ? "
					+ " and c.GROUP_ID=?  and c.ACTIVE_STATUS='Y' group by R.CREATE_USER_ID order by cost desc")
			.toString();

	public String GET_TOTAL_INDIVIDUAL_EXPENDITURE_RECORDS = new StringBuffer(
			"select sum(a.cost_to_individual),b.item_type")
			.append(" from ITEM_SHAREHOLDER_RECORD a,RECORD_ENTRY_TABLE b,USER_RECORD c ")
			.append(" where a.item_id=b.id  AND a.user_id=?  AND a.user_id=c.user_id and c.ACTIVE_STATUS='Y' AND b.EXP_DATE BETWEEN ? AND ? group by b.item_type")
			.toString();

	public String GET_TOTAL_INDIVIDUAL_DATE_EXPENDITURE_RECORDS = new StringBuffer(
			"select sum(a.cost_to_individual) as sum,b.EXP_DATE as date")
			.append(" from ITEM_SHAREHOLDER_RECORD a,RECORD_ENTRY_TABLE b,USER_RECORD c ")
			.append(" where a.item_id=b.id  AND a.user_id=? AND a.user_id=c.user_id and c.ACTIVE_STATUS='Y' AND b.EXP_DATE BETWEEN ? AND ? group by b.EXP_DATE asc")
			.toString();

	public String GET_HOME_EXPENDITURE_RECORDS = new StringBuffer(
			"select sum(a.cost_to_individual) as sum,b.EXP_DATE as date")
			.append(" from ITEM_SHAREHOLDER_RECORD a,RECORD_ENTRY_TABLE b,USER_RECORD c ")
			.append(" where a.item_id=b.id  AND a.user_id=? AND a.user_id=c.user_id and c.ACTIVE_STATUS='Y' group by b.EXP_DATE order by b.EXP_DATE desc limit 30")
			.toString();
	
	public String GET_HOME_GAINER_LOSER_RECORDS = new StringBuffer(
			"select a.balance ,b.user_name from NEW_BALANCE_RECORD a,USER_RECORD b where a.user_id=b.user_id and b.ACTIVE_STATUS='Y' and a.balance in")
			.append(" (select max(balance) from NEW_BALANCE_RECORD a,USER_RECORD b where a.user_id=b.user_id and b.ACTIVE_STATUS='Y' and b.group_id=? ")
			.append(" union")
			.append(" select min(balance) from NEW_BALANCE_RECORD a,USER_RECORD b where a.user_id=b.user_id and b.ACTIVE_STATUS='Y' and b.group_id=?) order by a.balance desc")
			.toString();

	public String GET_HOME_CREDIT_DEBIT_RECORDS = new StringBuffer(
			"select a.balance,b.user_name from NEW_BALANCE_RECORD a,USER_RECORD b ")
			.append(" where a.user_id=b.user_id and b.ACTIVE_STATUS='Y' ")
			.append(" and b.group_id=? and b.user_id=?")
			.toString();
	
	public String GET_PAID_PAYMENTS_RECORDS = new StringBuffer(
			"select c.payment_date,a.user_name,b.user_name,c.payment_amount")
			.append(" from USER_RECORD a,USER_RECORD b,ADJUSTMENT_RECORD c")
			.append(" where a.user_id=c.payee_user_id")
			.append(" and b.user_id=c.receiver_user_id")
			.append(" and b.group_id=? and a.group_id=?")
			.append(" and a.ACTIVE_STATUS='Y' and b.ACTIVE_STATUS='Y'")
			.append(" order by c.payment_date desc").toString();

	public String GET_TOTAL_HIGHCHART_DATE_EXPENDITURE_RECORDS = new StringBuffer(
			"select sum(a.cost_to_individual) as sum,b.EXP_DATE as date")
			.append(" from ITEM_SHAREHOLDER_RECORD a,RECORD_ENTRY_TABLE b,USER_RECORD c ")
			.append(" where a.item_id=b.id  AND a.user_id=? AND a.user_id=c.user_id and c.ACTIVE_STATUS='Y' AND b.EXP_DATE BETWEEN ? AND ? group by b.EXP_DATE asc")
			.toString();

	public String GET_TOTAL_INDIVIDUAL_EXPENSE_RECORDS = new StringBuffer(
			"select b.exp_date,b.item_type,b.description, a.cost_to_individual ")
			.append(" from ITEM_SHAREHOLDER_RECORD a,RECORD_ENTRY_TABLE b,USER_RECORD c  ")
			.append(" where a.item_id=b.id  AND a.user_id=? AND a.user_id=c.user_id and c.ACTIVE_STATUS='Y' AND b.EXP_DATE BETWEEN ? AND ? order by b.EXP_DATE desc")
			.toString();

	public String GET_TOTAL_INDIVIDUAL_SELECTED_EXPENSE_RECORDS = new StringBuffer(
			"select b.exp_date,b.item_type,b.description, a.cost_to_individual ")
			.append(" from ITEM_SHAREHOLDER_RECORD a,RECORD_ENTRY_TABLE b,USER_RECORD c  ")
			.append(" where a.item_id=b.id  AND a.user_id=? AND a.user_id=c.user_id and c.ACTIVE_STATUS='Y' AND b.EXP_DATE BETWEEN ? AND ? AND b.item_type = ? order by b.EXP_DATE desc")
			.toString();

	public String ADD_USER = new StringBuffer(
			"insert into USER_RECORD(USER_NAME,USER_ID,GROUP_ID,PASSWORD,EMAIL,PHONE,COMPANY,FIRST_LOGIN_STATUS,ACTIVE_STATUS,SEQ_QUES,SEQ_ANS,IMAGE) values")
			.append(" (?,?,?,?,?,?,?,?,?,?,?,?)").toString();
	public String UPDATE_USER = new StringBuffer(
			"update USER_RECORD set USER_NAME=?,EMAIL=?,COMPANY=?,PHONE=?,IMAGE=? where USER_ID=?")
			.toString();

	public String ADD_ITEM = new StringBuffer(
			"insert into ITEM_RECORD(select ? as a,? as b from dual where not exists ")
			.append(" (Select items from ITEM_RECORD where (items = ? and user_id =?) or (items = ? and user_id is null)) )")
			.toString();

	public String ADD_USER_BALANCE = new StringBuffer(
			"insert into NEW_BALANCE_RECORD(USER_ID,BALANCE) values").append(
			" (?,0)").toString();

	public String UPDATE_LOGIN_TIME = new StringBuffer("update USER_RECORD")
			.append(" set LAST_LOGIN_TIME = ? where USER_ID=?").toString();

	public String UPDATE_ANDROID_REG_ID = new StringBuffer("update USER_RECORD")
			.append(" set ANDROID_ID = ? where USER_ID=?").toString();

	public String UPDATE_SESSION_ID = new StringBuffer("update USER_RECORD")
			.append(" set USER_SESSION_ID = ? where USER_ID=?").toString();

	public String UPDATE_DEFAULT_PASSWORD = new StringBuffer(
			"update USER_RECORD")
			.append(" set PASSWORD = ? ,FIRST_LOGIN_STATUS=? where USER_ID=? and SEQ_ANS=?")
			.toString();

	public String GET_USER_RECORDS = new StringBuffer(
			"select user_name,user_id,password,email")
			.append(" from USER_RECORD").append(" where GROUP_ID=? and ACTIVE_STATUS='Y'").toString();

	public String GET_GROUP_USER_RECORDS = new StringBuffer(
			"select a.* from USER_RECORD a ,USER_RECORD b")
			.append(" where b.USER_ID=?").append(" and a.GROUP_ID=b.GROUP_ID")
			.append(" and a.ACTIVE_STATUS='Y' and b.ACTIVE_STATUS='Y'")
			.toString();

	public String GET_LOGIN_USER_RECORDS = new StringBuffer(
			" select * from USER_RECORD").append(
			" where USER_ID=? and PASSWORD=?").toString();

	public String GET_LOGIN_USER_IMAGE = new StringBuffer(
			" select * from USER_RECORD").append(" where USER_ID=?").toString();

	public String GET_ITEM_RECORDS = new StringBuffer(
			" select items from ITEM_RECORD")
			.append(" where <start>items like upper(?) and<end> (user_id is null or user_id=?)")
			.toString();

	public String IS_USER_PRESENT = new StringBuffer(
			" select * from USER_RECORD").append(" where USER_ID=? and ACTIVE_STATUS='Y'").toString();

	public String GET_NOTIFICATION = new StringBuffer(
			"select a.USER_ID as shareholder_id")
			.append(" ,c.USER_NAME as shareholder_name")
			.append(" ,a.cost_to_individual as cost_per_head")
			.append(" ,d.USER_NAME as paid_by_name")
			.append(" ,b.CREATE_USER_ID as paid_by_id")
			.append(" ,b.exp_amount as total_amount")
			.append(" ,b.NO_OF_SHAREHOLDER as shareholder_count")
			.append(" ,b.item_type as item_type")
			.append(" ,b.DESCRIPTION as description")
			.append(" ,DATE_FORMAT(b.EXP_DATE, '%d/%m/%Y')  as expense_date")
			.append(" from ITEM_SHAREHOLDER_RECORD a,RECORD_ENTRY_TABLE b,USER_RECORD c,USER_RECORD d")
			.append(" where b.ID=a.ITEM_ID").append(" and c.USER_ID=a.USER_ID")
			.append(" and d.USER_ID=b.CREATE_USER_ID").append(" and b.ID=?")
			.toString();

	public List<Notification> getNotification(long itemId) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select.query(GET_NOTIFICATION, new Object[] { itemId },
				new NotificationRowMapper());

	}

	public List<Person> getLoginUser(String userId, String password) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select.query(GET_LOGIN_USER_RECORDS, new Object[] { userId,
				password }, new LoginUserRowMapper());

	}

	public List<Person> getUserImage(String userId) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select.query(GET_LOGIN_USER_IMAGE, new Object[] { userId },
				new LoginUserRowMapper());

	}

	public int addItems(String item, String userId) {
		JdbcTemplate addItems = new JdbcTemplate(dataSource);
		return addItems.update(ADD_ITEM, new Object[] { item, userId, item,
				userId, item });

	}

	public List<String> getMatchItems(String params, String userId) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		if (params == null || params.equals("")) {
			GET_ITEM_RECORDS = GET_ITEM_RECORDS.replaceAll("<start>", "/*");
			GET_ITEM_RECORDS = GET_ITEM_RECORDS.replaceAll("<end>", "*/");
			return select.query(GET_ITEM_RECORDS, new Object[] { userId },
					new ItemsRowMapper());
		} else {
			GET_ITEM_RECORDS = GET_ITEM_RECORDS.replaceAll("<start>", " ");
			GET_ITEM_RECORDS = GET_ITEM_RECORDS.replaceAll("<end>", " ");
			return select.query(GET_ITEM_RECORDS,
					new Object[] { params, userId }, new ItemsRowMapper());
		}

	}

	public List<Person> isUserPresent(String userId) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select.query(IS_USER_PRESENT, new Object[] { userId },
				new LoginUserRowMapper());

	}

	public int updateDefaultPassword(String userId, String secAns,
			String password, String flag) {
		JdbcTemplate update = new JdbcTemplate(dataSource);
		return update.update(UPDATE_DEFAULT_PASSWORD, new Object[] { password,
				flag, userId, secAns });

	}

	public int addUser(Person user) {
		JdbcTemplate insert = new JdbcTemplate(dataSource);
		FileInputStream fis = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			fis = new FileInputStream(user.getImage());
			pstmt = insert.getDataSource().getConnection()
					.prepareStatement(ADD_USER);
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getUserId());
			pstmt.setString(3, user.getGroupId());
			pstmt.setString(4, user.getPassword());
			pstmt.setString(5, user.getEmail());
			pstmt.setString(6, user.getPhone());
			pstmt.setString(7, user.getCompany());
			pstmt.setString(8, user.getFirstLoginFlag());
			pstmt.setString(9, user.getActiveFlag());
			pstmt.setString(10, user.getSecQues());
			pstmt.setString(11, user.getSecAns());
			pstmt.setBinaryStream(12, (InputStream) fis,
					(int) (user.getImage().length()));

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// close all the connections.
				insert.getDataSource().getConnection().close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public int updateUser(Person user) {
		JdbcTemplate update = new JdbcTemplate(dataSource);
		FileInputStream fis = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			fis = new FileInputStream(user.getImage());
			pstmt = update.getDataSource().getConnection()
					.prepareStatement(UPDATE_USER);
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getCompany());
			pstmt.setString(4, user.getPhone());
			pstmt.setBinaryStream(5, (InputStream) fis,
					(int) (user.getImage().length()));
			pstmt.setString(6, user.getUserId());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// close all the connections.
				update.getDataSource().getConnection().close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public int addUserBalanceDetails(String userId) {
		JdbcTemplate insert = new JdbcTemplate(dataSource);
		return insert.update(ADD_USER_BALANCE, new Object[] { userId });

	}

	public int updateUserLoginTime(String time, String userId) {
		JdbcTemplate insert = new JdbcTemplate(dataSource);
		return insert.update(UPDATE_LOGIN_TIME, new Object[] { time, userId });

	}

	public int updateAndroidRegId(String regId, String userId) {
		JdbcTemplate insert = new JdbcTemplate(dataSource);
		return insert.update(UPDATE_ANDROID_REG_ID, new Object[] { regId,
				userId });

	}

	public int updateUserSessionId(String sessionId, String userId) {
		JdbcTemplate insert = new JdbcTemplate(dataSource);
		return insert.update(UPDATE_SESSION_ID, new Object[] { sessionId,
				userId });

	}

	public List<Person> selectAllUsers(String groupId) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select.query(GET_USER_RECORDS, new Object[] { groupId },
				new PersonRowMapper());
	}

	public List<Person> selectAllGroupUsers(String userId) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select.query(GET_GROUP_USER_RECORDS, new Object[] { userId },
				new LoginUserRowMapper());
	}

	public List<ExpenseData> selectAll(String groupId) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select.query(GET_TOTAL_RECORDS, new Object[] { groupId },
				new ExpenseRowMapper());

	}

	public List<Expenditure> selectAllExpenditure(String fromDate,
			String toDate, String type, String groupId) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		if (type.equals("1"))
			return select.query(GET_TOTAL_EXPENDITURE_RECORDS, new Object[] {
					fromDate, toDate, groupId }, new ExpenditureRowMapper());
		else
			return select.query(GET_TOTAL_OFFERINGS_RECORDS, new Object[] {
					fromDate, toDate, groupId }, new ExpenditureRowMapper());

	}

	public List<IndividualExpenditure> selectAllIndividualExpenditure(
			String userId, String fromDate, String toDate) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select.query(GET_TOTAL_INDIVIDUAL_EXPENDITURE_RECORDS,
				new Object[] { userId, fromDate, toDate },
				new IndividualExpenditureRowMapper());

	}

	public List<IndividualDateExpenditure> selectAllIndividualDateExpenditure(
			String userId, String fromDate, String toDate) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select.query(GET_TOTAL_INDIVIDUAL_DATE_EXPENDITURE_RECORDS,
				new Object[] { userId, fromDate, toDate },
				new IndividualDateExpenditureRowMapper());

	}

	public List<IndividualDateExpenditure> selectAllHomeExpenditure(
			String userId) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select.query(GET_HOME_EXPENDITURE_RECORDS,
				new Object[] { userId },
				new IndividualDateExpenditureRowMapper());

	}

	public List<Expenditure> getGainerLoserData(String groupId) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select.query(GET_HOME_GAINER_LOSER_RECORDS, new Object[] {
				groupId, groupId }, new ExpenditureRowMapper());

	}
	
	public List<Expenditure> getCreditDebitData(String groupId,String userId) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select.query(GET_HOME_CREDIT_DEBIT_RECORDS, new Object[] {
				groupId, userId }, new ExpenditureRowMapper());

	}
	public List<Payments> getPaidPaymentsData(String groupId) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select.query(GET_PAID_PAYMENTS_RECORDS, new Object[] { groupId,
				groupId }, new PaymentsRowMapper());

	}

	public List<IndividualDateExpenditure> selectAllHighChartDateExpenditure(
			String userId, String fromDate, String toDate) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select.query(GET_TOTAL_HIGHCHART_DATE_EXPENDITURE_RECORDS,
				new Object[] { userId, fromDate, toDate },
				new IndividualDateExpenditureRowMapper());

	}

	public List<IndividualExpense> selectAllIndividualExpense(String userId,
			String fromDate, String toDate) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select.query(GET_TOTAL_INDIVIDUAL_EXPENSE_RECORDS, new Object[] {
				userId, fromDate, toDate }, new IndividualExpenseRowMapper());

	}

	public List<IndividualExpense> selectAllSelectedExpense(String userId,
			String fromDate, String toDate, String itemType) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select.query(GET_TOTAL_INDIVIDUAL_SELECTED_EXPENSE_RECORDS,
				new Object[] { userId, fromDate, toDate, itemType },
				new IndividualExpenseRowMapper());

	}

}

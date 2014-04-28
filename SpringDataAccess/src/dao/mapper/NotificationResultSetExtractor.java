package dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.ResultSetExtractor;

import domainmodel.Notification;

public class NotificationResultSetExtractor implements ResultSetExtractor {

	@Override
	public Object extractData(ResultSet rs) throws SQLException {
		Notification noti = new Notification();

		noti.setShareholderId(rs.getString(1));
		noti.setShareholderName(rs.getString(2));
		noti.setPerHead(rs.getString(3));
		noti.setPaidByName(rs.getString(4));
		noti.setPaidById(rs.getString(5));
		noti.setPrice(rs.getString(6));
		noti.setCount(rs.getString(7));
		noti.setItemList(rs.getString(8));
		noti.setDesc(rs.getString(9));
		noti.setDate(rs.getString(10));

		return noti;
	}
}
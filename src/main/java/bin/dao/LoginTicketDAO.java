package bin.dao;

import org.apache.ibatis.annotations.Param;

import bin.model.LoginTicket;

public interface LoginTicketDAO {
	int addTicket(LoginTicket ticket);
	LoginTicket selectByTicket(String ticket);
	void updateStatus(@Param("ticket") String ticket,@Param("status")int status);
}

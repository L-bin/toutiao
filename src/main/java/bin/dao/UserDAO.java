package bin.dao;

import bin.model.User;

public interface UserDAO {
	int addUser(User user);
	
	User selectById(int id);
	
	User selectByName(String name);
	
	void updatePassword(User user);
	
	void deleteById(int id);
}

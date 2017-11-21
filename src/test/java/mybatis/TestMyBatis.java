package mybatis;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bin.dao.NewsDAO;
import bin.dao.UserDAO;
import bin.model.News;
import bin.model.User;
@RunWith(SpringJUnit4ClassRunner.class)	
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class TestMyBatis {
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private NewsDAO newsDAO;

	@Test
	public void test() {
		Random random=new Random();
		for(int i=0;i<11;i++){
			User user=new User();
			user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
			user.setName(String.format("USER%d", i));
			user.setPassword("");
			user.setSalt("");
			userDAO.addUser(user);
			
			News news = new News();
            news.setCommentCount(i);
			Date date=new Date();
			date.setTime(date.getTime()+1000*3600*i);
			news.setCreatedDate(date);
            news.setImage(String.format("http://images.nowcoder.com/head/%dm.png", random.nextInt(1000)));
            news.setLikeCount(i+1);
            news.setUserId(i+1);
            news.setTitle(String.format("TITLE{%d}", i));
            news.setLink(String.format("http://www.nowcoder.com/%d.html", i));
            newsDAO.addNews(news);
            
			user.setId(i+1);
			user.setPassword("newpassword");
			userDAO.updatePassword(user);
			
		}
		
		Assert.assertEquals("newpassword", userDAO.selectById(1).getPassword());
		//System.out.println(userDAO.selectById(1).getPassword());
        userDAO.deleteById(1);
        Assert.assertNull(userDAO.selectById(1));
        
	}
}

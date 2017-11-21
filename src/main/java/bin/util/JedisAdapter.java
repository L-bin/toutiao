package bin.util;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class JedisAdapter implements InitializingBean{
	
	//private Jedis jedis=null;
	
	private JedisPool pool=null; 
	
	public void afterPropertiesSet() throws Exception {
		pool=new JedisPool("localhost",6379);
	}
	
	public long sadd(String key,String value){
		Jedis jedis=null;
		try{
			jedis=pool.getResource();
			return jedis.sadd(key, value);
		}catch(Exception e){
			return 0;
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public long srem(String key,String value){
		Jedis jedis=null;
		try{
			jedis=pool.getResource();
			return jedis.srem(key, value);
		}catch(Exception e){
			return 0;
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public long scard(String key){
		Jedis jedis=null;
		try{
			jedis=pool.getResource();
			return jedis.scard(key);
		}catch(Exception e){
			return 0;
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public boolean sismember(String key,String value){
		Jedis jedis=null;
		try{
			jedis=pool.getResource();
			return jedis.sismember(key, value);
		}catch(Exception e){
			return false;
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public long lpush(String key,String value){
		Jedis jedis=null;
		try{
			jedis=pool.getResource();
			return jedis.lpush(key, value);
		}catch(Exception e){
			return 0;
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public List<String> brpop(int timeout,String key){
		Jedis jedis=null;
		try{
			jedis=pool.getResource();
			return jedis.brpop(timeout,key);
		}catch(Exception e){
			return null;
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
}

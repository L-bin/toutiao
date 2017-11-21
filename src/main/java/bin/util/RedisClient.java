package bin.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisClient {
	public static void main(String[] args){
		Jedis jedis=new Jedis("127.0.0.1",6379);
		jedis.set("name", "bin");
		String name = jedis.get("name");
		System.out.println(name);
		jedis.hset("myhash", "username", "bin");
		jedis.close();
//		JedisPoolConfig config=new JedisPoolConfig();
//		config.setMaxTotal(30);
//		config.setMaxIdle(10);
//		JedisPool pool=new JedisPool(config, "127.0.0.1",6379);
//		Jedis jedis=null;
//		try{
//			jedis=pool.getResource();
//			jedis.set("name", "hh");
//			String name = jedis.get("name");
//			System.out.println(name);
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			if(jedis!=null){
//				jedis.close();
//			}
//			if(pool!=null){
//				pool.close();
//			}
//		}
	}
}

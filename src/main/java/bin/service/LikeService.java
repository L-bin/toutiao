package bin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bin.util.JedisAdapter;
import bin.util.RedisKeyUtil;
@Service
public class LikeService {

	@Autowired
	private JedisAdapter jedisAdapter;
	
	public int getLikeStatus(int userId,int entityId,int entityType){
		String likeKey=RedisKeyUtil.getLikeKey(entityId, entityType);
		if(jedisAdapter.sismember(likeKey, String.valueOf(userId))){
			return 1;
		}
		String disLikeyKey=RedisKeyUtil.getDisLikeKey(entityId, entityType);
		return jedisAdapter.sismember(disLikeyKey, String.valueOf(userId))?-1:0 ;
	}
	
	
	public long like(int userId,int entityId,int entityType){
		String likeKey=RedisKeyUtil.getLikeKey(entityId, entityType);
		jedisAdapter.sadd(likeKey, String.valueOf(userId));
		
		String disLikeyKey=RedisKeyUtil.getDisLikeKey(entityId, entityType);
		jedisAdapter.srem(disLikeyKey, String.valueOf(userId));
		return jedisAdapter.scard(likeKey);
	}
	
	public long disLike(int userId,int entityId,int entityType){
		String disLikeyKey=RedisKeyUtil.getDisLikeKey(entityId, entityType);
		jedisAdapter.sadd(disLikeyKey, String.valueOf(userId));
		
		String likeKey=RedisKeyUtil.getLikeKey(entityId, entityType);
		jedisAdapter.srem(likeKey, String.valueOf(userId));
		return jedisAdapter.scard(likeKey);
	}
}

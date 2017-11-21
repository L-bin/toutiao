package com.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import bin.util.JedisAdapter;
import bin.util.RedisKeyUtil;

@Service
public class EventProducer {

	@Autowired
	private JedisAdapter jedisAdapter;
	
	public boolean fireEvent(EventModel model){
		try{
			String json=JSONObject.toJSONString(model);
			String key=RedisKeyUtil.getEventKey();
			jedisAdapter.lpush(key, json);
			return true;
		}catch(Exception e){
			return false;
		}
	}
}

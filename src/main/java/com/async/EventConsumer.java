package com.async;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import bin.util.JedisAdapter;
import bin.util.RedisKeyUtil;

@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware{
	private Map<EventType,List<EventHandler>> config=new HashMap<EventType,List<EventHandler>>();
	private ApplicationContext applicationContext;
	
	@Autowired
	private JedisAdapter jedisAdapter;
	
	public void afterPropertiesSet() throws Exception {
		Map<String,EventHandler> beans=applicationContext.getBeansOfType(EventHandler.class);
		if(beans!=null){
			for(Map.Entry<String, EventHandler> entry:beans.entrySet()){
				List<EventType> eventTypes=entry.getValue().getSupportEventTypes();
				for(EventType type:eventTypes){
					if(!config.containsKey(type)){
						config.put(type, new ArrayList<EventHandler>());
					}
					config.get(type).add(entry.getValue());
				}
			}
		}
		
		Thread thread=new Thread(new Runnable(){
			public void run() {
				while(true){
					String key=RedisKeyUtil.getEventKey();
					List<String> messages=jedisAdapter.brpop(0, key);
					for(String message:messages){
						if(message.equals(key)){
							continue;
						}
						EventModel eventModel=JSON.parseObject(message,EventModel.class);
						if(!config.containsKey(eventModel.getType())){
							continue;
						}
						for(EventHandler handler:config.get(eventModel.getType())){
							handler.doHandler(eventModel);
						}
					}
				}
			}
		});
		thread.start();
	}
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}
}

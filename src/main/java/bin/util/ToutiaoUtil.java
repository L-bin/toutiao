package bin.util;

import java.security.MessageDigest;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;

public class ToutiaoUtil {
	
	public static String getJSONString(int code){
		JSONObject json=new JSONObject();
		json.put("code", code);
		return json.toJSONString();
	}
	
	public static String getJSONString(int code,String msg){
		JSONObject json=new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		return json.toJSONString();
	}
	
	public static String getJSONString(int code,Map<String,Object> map){
		JSONObject json=new JSONObject();
		json.put("code", code);
		for(Entry<String, Object> entry:map.entrySet()){
			json.put(entry.getKey(), entry.getValue());
		}
		return json.toJSONString();
	}
	
	public static String MD5(String key){
		char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // ���MD5ժҪ�㷨�� MessageDigest ����
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // ʹ��ָ�����ֽڸ���ժҪ
            mdInst.update(btInput);
            // �������
            byte[] md = mdInst.digest();
            // ������ת����ʮ�����Ƶ��ַ�����ʽ
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            //logger.error("����MD5ʧ��", e);
            return null;
        }
	}
}

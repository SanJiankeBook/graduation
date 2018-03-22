package com.yc.utils;

import redis.clients.jedis.Jedis;

public class JedisUtils {

	public final static String REDIS_URL="localhost";
	/*redis的连接端口*/
	public final static int  REDIS_PORT=6379;
	
	 protected static JedisUtils redis = new JedisUtils ();
	 
	 protected static Jedis jedis = new Jedis( REDIS_URL, REDIS_PORT);
	
	public static JedisUtils getInstance()
    {
        return redis;
    }
	
	/**set Object*/
    public String set(Object object,String key)
   {
          return jedis.set(key.getBytes(), SerializeUtil.serialize(object));
   }
   
    /**get Object*/
    public Object get(String key)
   {
          byte[] value = jedis.get(key.getBytes());
          return SerializeUtil. unserialize(value);
   }
   
    /**delete a key**/
    public boolean del(String key)
   {
          return jedis.del(key.getBytes())>0;
   }
	
	
}

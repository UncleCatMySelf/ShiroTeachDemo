package com.myself.session;

import com.myself.util.JedisUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 13:00 2018\4\18 0018
 */
public class RedisSessionDao extends AbstractSessionDAO{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private JedisUtil jedisUtil;

    private final String shiro_session_prefix = "myself-session:";

    private byte[] getKey(String key){
        return (shiro_session_prefix + key).getBytes();
    }

    private void saveSession(Session session){
        if (session != null && session.getId() != null) {
            byte[] key = getKey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            jedisUtil.set(key, value);
            jedisUtil.expire(key, 600);
        }

    }

    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session,sessionId);
        saveSession(session);
        return sessionId;
    }

    protected Session doReadSession(Serializable sessionId) {
        logger.info("read session");
        if (sessionId == null){
            return null;
        }
        byte[] key = getKey(sessionId.toString());
        byte[] value = jedisUtil.get(key);
        return (Session) SerializationUtils.deserialize(value);
    }

    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }

    public void delete(Session session) {
        if (session == null || session.getId() == null){
            return;
        }
        byte[] key = getKey(session.getId().toString());
        jedisUtil.del(key);
    }

    public Collection<Session> getActiveSessions() {
        Set<byte[]> keys = jedisUtil.keys(shiro_session_prefix);
        Set<Session> sessions = new HashSet<Session>();
        if (CollectionUtils.isEmpty(keys)){
            return sessions;
        }
        for (byte[] key:keys){
            Session session = (Session) SerializationUtils.deserialize(jedisUtil.get(key));
            sessions.add(session);
        }
        return sessions;
    }

}

package app.core.sessions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import app.core.exception.CouponSystemException;

@Component
public class SessionContext {

	@Autowired
	private ApplicationContext ctx;
	private Map<String, Session> sessionsMap = new ConcurrentHashMap<>();

	private boolean isSessionExpired(Session session) {
		return System.currentTimeMillis() - session.getLastAccessed() > session.getMaxInactiveInterval();
	}

	public Session createSession() {
		Session s = ctx.getBean(Session.class);
		sessionsMap.put(s.token, s);
		return s;
	}

	public void invalidate(Session session) {
		sessionsMap.remove(session.token);
	}

	public Session getSession(String sessionToken) throws CouponSystemException {
		Session s = sessionsMap.get(sessionToken);
		if (s != null) {
			if (!isSessionExpired(s)) {
				s.resetLastAccessed();
				return s;
			} else {
				invalidate(s);
				throw new CouponSystemException("You were disconnected");
			}
		} 
//		throw new CouponSystemException("You are not logged in");
		return null;
		
	}

	@Scheduled(initialDelay = 5_000, fixedDelay = 100_000)
	private void init() {
		System.out.println("session cleaner working");
		for (String currToken : sessionsMap.keySet()) {
			Session s = sessionsMap.get(currToken);
			if (isSessionExpired(s)) {
				invalidate(s);
			}
		}
	}

}
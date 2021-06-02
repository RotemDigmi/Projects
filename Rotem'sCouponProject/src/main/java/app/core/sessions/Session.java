package app.core.sessions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Session {

	public final String token;
	private Map<String, Object> attributes = new HashMap<>();
	private long lastAccessed;
	@Value("${session.max.inactive.interval:10}")
	private long maxInactiveInterval;
	private final static int TOKEN_MAX_LENGTH = 20;
	
	{
		this.token = UUID.randomUUID().toString().replace("-", "").substring(TOKEN_MAX_LENGTH);
		resetLastAccessed();
	}
	
	public void resetLastAccessed() {
		this.lastAccessed = System.currentTimeMillis();
	}
	
	@PostConstruct
	public void init() {
		this.maxInactiveInterval = TimeUnit.MINUTES.toMillis(maxInactiveInterval);
	}

	public void setAttributes(String attrName, Object object) {
		this.attributes.put(attrName, object);
	}
	
	public Object getAttributes(String attrName) {
		return attributes.get(attrName);
	}

	public long getLastAccessed() {
		return lastAccessed;
	}

	public long getMaxInactiveInterval() {
		return maxInactiveInterval;
	}
	
}
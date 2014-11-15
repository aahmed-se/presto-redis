package com.aahmedse.presto.redis;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import redis.clients.collections.MapStructure;
import redis.clients.collections.builder.RedisStrutureBuilder;

import com.facebook.presto.server.PluginManager;
import com.aahmedse.presto.connector.PrestoRedisPlugin;
import com.aahmedse.presto.pojo.Employee;
import com.facebook.presto.server.PrestoServer;
import com.google.common.collect.ImmutableList;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeListener;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;

import redis.clients.jedis.HostAndPort;


public class PrestoRedisServer extends PrestoServer implements Module, InjectionListener<PluginManager>{
	
	
	public static HashSet<String> tableNames = new  HashSet<String>();

	public static void main(String[] args) {
		
		Set jedisClusterNodes = new HashSet();
		jedisClusterNodes.add(new HostAndPort("127.0.0.1", 6379));		
		flushAll();
		addtestData();
		
		new PrestoRedisServer().run();
		
	}
	
	private static void flushAll(){
		
		RedisConnection.JEDIS.flushAll();
		
	}
	
	private static void addtestData(){
		
		MapStructure employeeDB = RedisStrutureBuilder.ofMap(RedisConnection.JEDIS, Employee.class).withNameSpace("employeedb").build();
		tableNames.add("employees");
		
		Map employeeMap = employeeDB.get("employees");
		
		employeeMap.put("1", new Employee(1, "Ali", true, 100.0));
		employeeMap.put("2", new Employee(2, "Dave", true, 250.7));
		employeeMap.put("3", new Employee(3, "Arnold", false, 110.6));
		
	}

	 @Override
	    protected Iterable<? extends Module> getAdditionalModules() {
	    	return ImmutableList.of(this);
	    }

	    @Override
	    public void afterInjection(PluginManager pm) {
	    	//pm.installPlugin(new ExamplePlugin());
	    	pm.installPlugin(new PrestoRedisPlugin());
	    }

		@Override
		@SuppressWarnings({"unchecked", "rawtypes"})
		public void configure(Binder binder) {

			TypeListener listener = new TypeListener() {
				@Override
				public void hear(TypeLiteral type, TypeEncounter encounter) {
					encounter.register(PrestoRedisServer.this);
				}
			};

			binder.bindListener(classMatcher(PluginManager.class), listener);
		}

		@SuppressWarnings("rawtypes")
		private static Matcher<TypeLiteral> classMatcher(final Class clazz) {
			Matcher<TypeLiteral> typeMatcher = new AbstractMatcher<TypeLiteral>() {
				@Override
				public boolean matches(TypeLiteral t) {
					return t.getRawType() == clazz;
				};
			};
			return typeMatcher;
		}
}
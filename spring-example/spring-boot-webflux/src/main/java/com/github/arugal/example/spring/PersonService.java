package com.github.arugal.example.spring;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangwei
 */
@Component
public class PersonService {

	private final Map<String, Person> store = new ConcurrentHashMap<>();

	private final AtomicInteger IDS = new AtomicInteger(0);


	public Person get(String name) {
		return store.get(name);
	}

	public Person remove(String name) {
		return store.remove(name);
	}

	public void put(Person person) {
		person.setId(IDS.incrementAndGet());
		store.put(person.getName(), person);
	}
}

package com.github.arugal.example.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author zhangwei
 */
@RestController
@RequestMapping("/restful")
public class RESTfulApi {

	@Autowired
	private PersonService personService;

	@GetMapping("/get/{name}")
	public Flux<Person> get(@PathVariable String name) {
		return Flux.create(fluxSink -> {
			Person person = personService.get(name);
			if (person != null) {
				fluxSink.next(person);
			}
			fluxSink.complete();
		});
	}

	@PostMapping("/put")
	public Mono<Person> put(@RequestBody Person person) {
		return Mono.create(monoSink -> {
			if (person != null) {
				personService.put(person);
			}
			monoSink.success(person);
		});
	}

}

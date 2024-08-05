package com.example.reactiveexamples.repository;

import com.example.reactiveexamples.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryImpl implements PersonRepository {

  Person fara = new Person(1, "Martin", "Arnesi");
  Person manolo = new Person(2, "Pablo", "Sanchez");
  Person opy = new Person(3, "Cristian", "Oppido");

  @Override
  public Mono<Person> getById(Integer id) {
    return Mono.just(fara);
  }

  @Override
  public Flux<Person> findAll() {
    return Flux.just(fara, manolo, opy);
  }
}

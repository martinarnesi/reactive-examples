package com.example.reactiveexamples.repository;

import com.example.reactiveexamples.domain.Person;
import java.util.Objects;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryImpl implements PersonRepository {

  Person fara = new Person(1, "Martin", "Arnesi");
  Person manolo = new Person(2, "Pablo", "Sanchez");
  Person opy = new Person(3, "Cristian", "Oppido");

  @Override
  public Mono<Person> getById(final Integer id) {
    return findAll().filter(person -> Objects.equals(person.getId(), id)).next();
  }

  @Override
  public Flux<Person> findAll() {
    return Flux.just(fara, manolo, opy);
  }
}

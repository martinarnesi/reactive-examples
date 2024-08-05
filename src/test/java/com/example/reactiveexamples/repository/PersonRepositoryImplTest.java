package com.example.reactiveexamples.repository;

import com.example.reactiveexamples.domain.Person;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class PersonRepositoryImplTest {
  PersonRepositoryImpl personRepository;

  @BeforeEach
  void setUp() {
    personRepository = new PersonRepositoryImpl();
  }

  @Test
  void getByIdBlock() {
    Mono<Person> personMono = personRepository.getById(1);

    Person person = personMono.block();

    System.out.println(person);

  }

  @Test
  void getByIdSubscribe() {
    Mono<Person> personMono = personRepository.getById(1);
    personMono.subscribe(person -> System.out.println(person));
  }

  @Test
  void getByIdMap() {
    Mono<Person> personMono = personRepository.getById(1);

    personMono
        .map(person -> person.getFirstName())
        .subscribe(firstName -> System.out.println(firstName));
  }

  @Test
  void fluxTestBlockFirst() {
    Flux<Person> allFlux = personRepository.findAll();

    Person person = allFlux.blockFirst();

    System.out.println(person.toString());

  }

  @Test
  void fluxTestSubscribe() {
    Flux<Person> allFlux = personRepository.findAll();

    allFlux.subscribe(person -> {
      System.out.println(person.toString());
    });

  }

  @Test
  void fluxTestSubscribeToMonoList() {
    Flux<Person> allFlux = personRepository.findAll();

    Mono<List<Person>> personListMono = allFlux.collectList();

    personListMono.subscribe(list -> {
      list.forEach(person ->
          System.out.println(person.getLastName()));
    });
  }

  @Test
  void fluxTestSubscribeFilter() {
    Flux<Person> allFlux = personRepository.findAll();

    final Integer id = 1;

    Mono<Person> personMono = allFlux.filter(person -> person.getId() == id).next();

    personMono.subscribe( person -> System.out.println(person));

  }

  @Test
  void fluxTestSubscribeFilterNotFound() {
    Flux<Person> allFlux = personRepository.findAll();

    final Integer id =8;

    Mono<Person> personMono = allFlux.filter(person -> person.getId() == id).next();

    personMono.subscribe(person -> System.out.println(person));

  }

  @Test
  void fluxTestSubscribeFilterNotFoundExeption() {
    Flux<Person> allFlux = personRepository.findAll();

    final Integer id =8;

    Mono<Person> personMono = allFlux.filter(person -> person.getId() == id).single();

    personMono
        .doOnError(throwable -> System.out.println("BOOOM! Not Found Error: " + throwable.getMessage()))
        .onErrorReturn(Person.builder().id(id).build())
        .subscribe(person -> System.out.println(person));
  }
}
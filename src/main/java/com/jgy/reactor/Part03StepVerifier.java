package com.jgy.reactor;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.function.Supplier;

import com.jgy.reactor.domain.User;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
* Learn how to use StepVerifier to test Mono, Flux or any other kind of Reactive Streams Publisher.
*
* @author Sebastien Deleuze
* @see <a href="http://projectreactor.io/docs/test/release/api/reactor/test/StepVerifier.html">StepVerifier Javadoc</a>
*/
public class Part03StepVerifier {

//========================================================================================

	// TODO Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then completes successfully.
	void expectFooBarComplete(Flux<String> flux) {
	    
	    StepVerifier.create(flux)
  	    .expectNext("foo")
  	    .expectNext("bar")
  	    .expectComplete()
  	    .verify();	    
		//fail();
	}

//========================================================================================

	// TODO Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then a RuntimeException error.
	void expectFooBarError(Flux<String> flux) {
	    
		StepVerifier.create(flux)
  	    .expectNext("foo")
  	    .expectNext("bar")
  	    .expectError()
  	    .verify();
	    //fail();
	}

//========================================================================================

	// TODO Use StepVerifier to check that the flux parameter emits a User with "swhite"username
	// and another one with "jpinkman" then completes successfully.
	void expectSkylerJesseComplete(Flux<User> flux) {
	    
	    StepVerifier.create(flux)
  	    .expectNextMatches(user -> user.getUsername() == "swhite")
  	    .expectNextMatches(user -> user.getUsername() == "jpinkman")
  	    .expectComplete()
  	    .verify();
	    
	   StepVerifier.create(flux)
  	    .expectNextMatches(user -> {
  	        assertThat(user.getUsername()).isEqualTo("swhite");
  	        return true;
  	    })
  	     .expectNextMatches(user -> {
  	        assertThat(user.getUsername()).isEqualTo("jpinkman");
  	        return true;
  	    })
  	    .verifyComplete();	   
		//fail();
	}

//========================================================================================

	// TODO Expect 10 elements then complete and notice how long the test takes.
	void expect10Elements(Flux<Long> flux) {
		StepVerifier.create(flux)
		.expectNextCount(10)
		.expectComplete()
		.verify();
		//fail();
	}

//========================================================================================

	// TODO Expect 3600 elements at intervals of 1 second, and verify quicker than 3600s
	// by manipulating virtual time thanks to StepVerifier#withVirtualTime, notice how long the test takes
	void expect3600Elements(Supplier<Flux<Long>> supplier) {
		StepVerifier.withVirtualTime(supplier)	        
	        .thenAwait(Duration.ofSeconds(3600))
	        .expectNextCount(3600)
	        .expectComplete()
	        .verify();	        
		//fail();
		
	}

	private void fail() {
		throw new AssertionError("workshop not implemented");
	}

}

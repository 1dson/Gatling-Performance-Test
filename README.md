
# Description

Example of a gatling performance test 

# Prerequisites
- Maven

# Executing

   ## Values
   To specify the environment, throughput, number of users and ramp up duration, you'll need to pass values to
   the following system props : 
   
     -Denv=
     -Dusers=
     -Dthroughput=
     -Dduration=

   ## Default Values
   If no values are passed to the system props above, their default values will be used. The default values for
   these are :-
   
      -Denv=int
      -Dusers=1
      -Dthroughput=5
      -Dduration=10
   
   ## Without specifying values
   To execute a scenario without specifying a value use the following command 
``mvn gatling:test`` in your terminal. By default the this will execute all scenarios in the suite and use the
default values. 

  
   ## Specific Scenarios
   To execute a specific scenario e.g. GetVehicleDetailsScenario, use the following command in your terminal
   
     ``mvn gatling:test -Denv= -Dusers= -Dduration= -Dthroughput= -Dgatling.simulationClass={FULL_PATH_TO_SIMULATION_CLASS}``
   
   ## Injectors
   
   You can use a single profile or a multiple combination of the profiles below to construct your load profile.
   These can be added to the simulation class
   
    nothingFor(4 seconds), // 1
    atOnceUsers(10), // 2
    rampUsers(10) over(5 seconds), // 3
    constantUsersPerSec(20) during(15 seconds), // 4
    constantUsersPerSec(20) during(15 seconds) randomized, // 5
    rampUsersPerSec(10) to 20 during(10 minutes), // 6
    rampUsersPerSec(10) to 20 during(10 minutes) randomized, // 7
    splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy(10 seconds), // 8
    splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy atOnceUsers(30), // 9
    heavisideUsers(1000) over(20 seconds) // 10
  
 
    
    nothingFor(duration): Pause for a given duration.
    atOnceUsers(nbUsers): Injects a given number of users at once.
    rampUsers(nbUsers) over(duration): Injects a given number of users with a linear ramp over a given duration.
    constantUsersPerSec(rate) during(duration): Injects users at a constant rate, defined in users per second, during a given duration. Users will be injected at regular intervals.
    constantUsersPerSec(rate) during(duration) randomized: Injects users at a constant rate, defined in users per second, during a given duration. Users will be injected at randomized intervals.
    rampUsersPerSec(rate1) to (rate2) during(duration): Injects users from starting rate to target rate, defined in users per second, during a given duration. Users will be injected at regular intervals.
    rampUsersPerSec(rate1) to(rate2) during(duration) randomized: Injects users from starting rate to target rate, defined in users per second, during a given duration. Users will be injected at randomized intervals.
    splitUsers(nbUsers) into(injectionStep) separatedBy(duration): Repeatedly execute the defined injection step separated by a pause of the given duration until reaching nbUsers, the total number of users to inject.
    splitUsers(nbUsers) into(injectionStep1) separatedBy(injectionStep2): Repeatedly execute the first defined injection step (injectionStep1) separated by the execution of the second injection step (injectionStep2) until reaching nbUsers, the total number of users to inject.
    heavisideUsers(nbUsers) over(duration): Injects a given number of users following a smooth approximation of the heaviside step function stretched to a given duration.``

  
  ##Example
  
  ``Open workload model version:
    // generate an open workload injection profile
    // with levels of 10, 15, 20, 25 and 30 arriving users per second
    // each level lasting 10 seconds
    // separated by linear ramps lasting 10 seconds
    scn.inject(
      incrementUsersPerSec(5)
        .times(5)
        .eachLevelLasting(10 seconds)
        .separatedByRampsLasting(10 seconds)
        .startingFrom(10)
    )
``
  
   ## Reports
   
   Reports can be found in the following location ``/target/gatling/results``

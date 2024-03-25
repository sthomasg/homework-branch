# Branch Homework


#### Build Requirements

* gradle 8.1.*
* Java 17+

#### To Build:
Execute the following statement at the project root:

"./gradlew clean build"

#### To Execute the Service
Execute the folling statement at the project root:

"./gradlew bootRun"

Currently there is only the default profile.

##### Calling the API on a running instance:
* http://localhost:8080/v1/users/{userID}
* examples:
  * http://localhost:8080/v1/users/octocat
  * http://localhost:8080/v1/users/dino


# Architecture and Decisions

This is a simple "layered" architcture
* At the bottom is a "datasource layer" - abstracts where the data comes from from the layer above it.
* Above this is a "business tier" (I called it "delegates").
  * Not much for this layer to do in this very simple example.
  * Orchestrates caching - if we were modifying data, we would likely invalidate modified items by key
  * Does any (simple) validation of submitted User keys.
  * simply delegates to layer below to obtain instance
* At top of this is an API tier
  * Essentially just implements the OpenAPI generated API, which we know conforms to OpenAPI spec


This was a very simple problem, but I wanted to show a few things:
* This is a public API
  * I went through a (very simple) design process to get an OpenAPI spec and JSON schema, independent of Java
  * Besides the benefits of API design (governance, versioning,....) we get guarantee of cross-platform and tooling conformance.
  * Developers CANNOT break the API, bc they never touch the "REST" API and its artifacts (they get generated every build)
* Simple use of high-level async constructs (Completable future)
  * I DID make a decision to go ahead and allow both calls to happen in parallel.
    * this has negative impact if the user key does not match (the /repos calls is "wasted")
    * would try this out..learn, measure, iterate..
*  Simplicity
  * Modern Spring Boot lets us delegate so many things we used to have to do by hand.
    * delegating to other things (caching, RestTemplate, etc) - don't re-invent the wheel.
    * The document talks about "how I would do things"...thats how.
* The document talks about "production" - LOTS of things left out:
    * Ran out of time (I took the 4 hours thing seriously)  ..so not total test coverage for sure!
    * I did not take the time to slap in "security" -- i was tempted to slap in an OKTA Oauth2 with spring security (I have an okta account, and its very easy to do)
    * Dependency scanner (Owasp has a nice one)
    * Static  code scanning (PMP, FindBugs, etc etc)
    * SBOM
    * Licensing
    * Code Formatting
    * ETC...not used to NOT having these things, but they exceed the mandate, i think.


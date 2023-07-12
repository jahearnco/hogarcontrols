This is a Hogar Controls coding assessment challenge as defined below "Java Application Developer Assessment"

SpringToolSuite4 was used as IDE. STS4 has Eclipse as its foundation. Class compilation, run and debug launch proceed in standard fashion.

Application Launch begins @ HogarApplication.java as shown below. Once running tests can proceed per curl -X command line as shown in test_cases.txt file provided at root level of project.

@SpringBootApplication
public class HogarApplication {
	public static void main(String[] args) {
		SpringApplication.run(HogarApplication.class, args);
	}
}

An Embedded Db was implemented for this challenge. Apache Maven is used along with Spring Boot for product and build management. Please see pom.xml and mvnw files. 

The following dependency was added to pom.xml to provide more RESTful functionality.

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>

Well over 90% of logic is presented according to CustomerController.java class.

Given more time I would have fleshed out test cases per src/test/java classes with use case and workflow specific logic to standarding testing procedures. Instead I chose to present testing process via comments added to test_cases.txt file. Please view this file.

An API which does lookup based on ids rather than userId will always be more secure.

I decided against a userId based lookup for Customer entries. In my experience there's no convincing use case that requires this kind of versatility. This is because Customer id will always be present and if it isn't a change should be made to ensure that Customer id or a list of ids are available to whichever user is implementing API. If Customer is using the API they should be logged in to do so. Customer id will be available upon login and API used should use this value. If some other user with higher level permissions is using the API then they will have access to the list of ids and well as userIds.

As per challenge requirements, the rewards per month were totaled and returned in the key (String) to the monthly map entries. I chose this approach, because in my experience the best way to present such totals is via UI which can show flexibility and versatility wrt plotting and tabling data per a range or ranges which should be easy to choose and manipulate by end user via UI controls. Choosing an API that is meant to return a total over some range seems un-necessary to me and I believe tends to limit functionality.

As this assessment gave me the ability to choose how far I wanted to go wrt versailitly and richness of the API, I ran out of time to present every idea and plan that I had intended to implement. A significant limitation of my API is that the rewards mapping and totals are returned specifically by month rather than allowing a more flexible API that accepts the time period gradation as an input. Given 2 more hours I would have re-written the rewards @GetMapping to return rewards by hour, day, year in addition to month. However, the instructions for the assessment appeared to be relaxed enough on this point and so I choose to designate a specific monthly time gradation for getMonthlyRewardsMap() and getMonthlyRewardsTotals() functions in CustomerController class.

Thank you for allowing me time to do this assessment. As it was somewhat open ended I chose to take me time with it to see where it would go. There's really no end to the rich features that can be added and updated and improved upon here.

-John




Java Application Developer Assessment

Business Requirement

A retailer offers a rewards program to its customers, awarding points based on each valid purchase. A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction (e.g. if one customer spends $120 total in a transaction, then points earned is 2x$20 + 1x$50 = 90 points).

Given all the transactions of every customer during a period of time(1 year for example), calculate the reward points earned for each customer per month and total.

Solution Requirement

Create some Restful APIs to get customer's reward points. By customized input like user id or user name.

Use common sense to make assumptions.

Consider the API security and confidentiality of user data.

Make up a data set to best demonstrate your solution.

The solution should demonstrate the design of API, business logic and data storage.

Use SpringBoot 2 or 3.

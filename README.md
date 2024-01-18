

![Logo](/Images/Logo.png)

# Ani.Rate

Ani.Rate is a personal fullStack web project for rating anime using __SpringBoot,Thymeleaf and thirdParty API__ from [Kitsu]( https://kitsu.docs.apiary.io/ ).

As a Ani.Rate user you can rate anime you have already watched or you can save new series to you "Want to watch list"

*From Code to the logo, all what is in this repo is made by me :).*

#


### Rating Random Anime


After you successfully register and login to Ani.Rate main page will welcome you with an Random anime that you can already start ratting.
![Main page](/Images/Main_Page.png)
![Rating](/Images/Rating_View.png)
#

### Anime List

In your header you can navigate yourself to your own anime list where you can see all you rated / saved anime or you can track which anime you are currently watching for the saved list!
![UserList page](/Images/UserList_Page.png)
#

### Searching by category

If you are not a fan of rating random generated anime, you can use search by category page, where you can with ease find anime by your chosed category.
![Searching page](/Images/Search_Page.png)
#

### Top Rated Anime

For last, but not least you can see the top 6 anime from all user so you can, always know what show you can 100% recomend to your norime friend, that haven't seen nothing yet ;).
![TopRated page](/Images/TopRated_Page.png)
#

# Project Endpoints
* The Back-end connects with the frontend through  Thymeleaf library, so my controllers neither send or fetch JSONs.

###### Q:
*Why did you decided to use Thymeleaf for this project?*
###### A:
*I chose Thymeleaf for this small monolithic project because of its simplicity and native use. Also, I not gonna lie.. I feel confident in my Thymeleaf skills.*

#### Get Main Page

```http
  GET /
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `principal` | `Principal` | **Required**.  User authentication information. |
| `model` | `Model` | **Required**.   Spring framework model for view data. |

#### Get User Anime List

```http
  GET /user_list
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `principal` | `Principal` | **Required**.  User authentication information. |
| `model` | `Model` | **Required**.   Spring framework model for view data. |

#### Add Anime to Watched List

```http
  POST /add_seen
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `principal` | `Principal` | **Required**.  User authentication information. |
| `animeDataDTO` | `AnimeDataDTO` | **Required**.   DTO containing anime data. |
| `dontAddToList` | `Optional<String>` | Optional parameter indicating whether to add the anime to the list. |
| `seenAnime` | `Optional<String>` | Optional parameter indicating whether the anime has been seen. |

#### Edit Anime Rating

```http
  POST /edit_anime
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `animeRatingDTO` | `AnimeRatingDTO` | **Required**.  DTO containing anime rating data. |

#### Get Top Rated Anime

```http
  GET /top_rated_anime
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `principal` | `Principal` | **Required**.  User authentication information. |
| `model` | `Model` | **Required**.   Spring framework model for view data. |

#### Add Anime to Currently Watching List

```http
  POST /add_watching_anime
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `principal` | `Principal` | **Required**.  User authentication information. |
| `animeId` | `Long` | **Required**.   Id of the anime. |

#### Delete Anime from List

```http
  POST /delete_anime
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `principal` | `Principal` | **Required**.  User authentication information. |
| `animeId` | `Long` | **Required**.   Id of the anime. |

#### Rate an Anime

```http
  POST /rate_anime
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `principal` | `Principal` | **Required**.  User authentication information. |
| `model` | `Model` | **Required**.   Spring framework model for view data. |
| `animeDataDTO` | `AnimeDataDTO` | **Required**.   DTO containing anime data. |

#### Edit Anime Rating

```http
  POST /edit_anime_rating
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `principal` | `Principal` | **Required**.  User authentication information. |
| `model` | `Model` | **Required**.   Spring framework model for view data. |
| `animeId` | `Long` | **Required**.   Id of the anime. |

#### Search Anime by Categories Page

```http
  GET /anime_categories
```
*No parameters needed!*

#### Search Anime

```http
  POST /search_anime
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `List<String>` | `categories` | **Required**.  User authentication information. |
| `model` | `Model` | **Required**.   Spring framework model for view data. |


# Third Party API
To easily and comfortly fetch Data form [Kitsu]( https://kitsu.docs.apiary.io/ ) API I used **WebClient** in my WebClientService and then I'm converting the fetch JSON string to FetchedAnimeDataDTO in my AnimeService class.

### Fetching Anime By id
*The this method input is random int, so I can fetch random Anime.*

```java
public Mono<String> fetchDataById(int id) {
        return webClient.get()
                .uri("/anime/{id}",id)
                .retrieve()
                .bodyToMono(String.class);
    }
    
```
### Fetching Anime By List of Categories
```java
public Mono<String> searchAnimeByCategories(List<String> categories) {
        return webClient.get()
                .uri("/anime", uriBuilder -> uriBuilder
                        .queryParam("filter[categories]", categories.toArray())
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
```
### Converting JSON string 
```java
private FetchedAnimeDataDTO convertJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode attributes = jsonNode.path("attributes");

            return new FetchedAnimeDataDTO(
                    attributes.path("titles").path("en").asText("Unknown"),
                    attributes.path("titles").path("ja_jp").asText("Unknown"),
                    attributes.path("startDate").asText("Unknown"),
                    attributes.path("episodeCount").asText("Unknown"),
                    attributes.path("synopsis").asText("No description available"),
                    attributes.path("posterImage").path("medium").asText("No image available")
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
```
# Security

My security is bit simple. I just needed to hash passwords for new  registed new Users and allow only the login page ( with css and images ) to not authentificated user. The spring security then use **Principal** to get the currect user ( using coockies ). Bcs my project dose not have any other role then User there is no need for specific authorization.

### SecurityFilterChain

```java
@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request.requestMatchers("/login", "/register", "/Style.css","/images/AniRate_background.svg","/images/Title_ani.rate.svg")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> form.loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/", true));

        return http.build();
    }
```
### PasswordEncoder
```
@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
```
# Testing

## 1. End-to-End Tests

### Purpose
In this project there are two classes with e2e test. First for testing whole project and the Second for Testing separete parts of the project. They test in the same way how I was testing the app manualy before I made them ( What a traumatic and dark past that was :,) ). Test are made with really pleasent to use [**PlayWrite** ](https://playwright.dev/java/)libary.

### Usage


1. Ensure that the **application is running.**
2. Navigate to the E2E testing directory.
   ```bash
   cd com/example/animerating/e2e/tests
3. Run Test for preferred test class
  ```bash
  ./gradlew NAME_OF_THE_CLASS
```
#

## 2. Service Tests

### Purpose
Tests for AnimeService and WebCliendService were made so I can check if the the thrid party data are fetching correctly and if the json is succsefully converted to FetchedAnimeDataDTO.

### Usage

1. Navigate to the Service testing directory.
   ```bash
   cd com/example/animerating/services
2. Run Test for preferred test class
  ```bash
  ./gradlew NAME_OF_THE_CLASS
```

# Usage
#### Project overview
This project is made with:

- **Thymeleaf:** View engine
- **MySQL:** Database
- **Hibernate:** ORM for working with data
- **Spring Web:** Web framework
- **Spring Web Devtools:** Development tools
- **Playwright:** End-to-End testing
- **Spring Security:** Security framework

#### Setting application.propereties

To run this project you need to set your propereties, that you can find in public.application.properties to your aplication.propereties.

```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.hibernate.ddl-auto=create
spring.security.user.name=
spring.security.user.password=
app.url= //here write you main url, so you can use e2e tests.
```

#### Setting implementations

Also don't forget to set these implementation to your gradel, if for some reasone they would not be there in a first place or you accidently delete some of them.

###### Q:
*Why Gradel and not Maven?*
###### A:
*I know that maven is much "cooler" and suitable for larger projects, but for my small project with no indent for growing, I decided to use gradel!*

```
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-webflux'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	developmentOnly 'org.springframework.boot:spring-boot-devtools'
	runtimeOnly 'com.mysql:mysql-connector-j'
	implementation 'org.springframework.boot:spring-boot-starter-security'
	implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity6'
	testImplementation 'org.springframework.security:spring-security-test'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'com.microsoft.playwright:playwright:1.15.1'
}
```

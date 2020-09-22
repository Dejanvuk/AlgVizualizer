# AlgVizualizer

React/Redux app made to vizualize the most common sorting algorithms in real time 2D

Features:

- 5 sorting algorithms
- sign in & register using JPA
- oauth2 sign in with support for google, facebook, github

### Installing

```
mvn install 
```

```
npm install
```

## Deployment

Build docker images

```
docker build -t <name>/<frontend/backend-api> .
```

```
docker run -t -i --name backend-api -p 8080:8080 -d <name>/backend-api
```

```
docker run -p 3000:80 <name>/frontend
```

## Built With

* [Spring](https://spring.io/projects/spring-framework) - The web framework used for user logging system
* [Maven](https://maven.apache.org/) - Dependency Management
* [ReactJS](https://reactjs.org/) - Framework of choice for the frontend

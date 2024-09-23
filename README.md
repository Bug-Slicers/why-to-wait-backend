# why-to-wait-backend

## Prerequisites

- Spring-boot
- Maven
- Docker

## Steps to run the project

`Pull and Run Postgres Sql Image:`

```
docker run --name postgres-container -e POSTGRES_USER=your_username -e POSTGRES_PASSWORD=yourpassword -e POSTGRES_DB=your_db -p 5432:5432 -d postgres
```

`Pull and Run PgAdmin Image`

```
docker run --name pgadmin-container -e PGADMIN_DEFAULT_EMAIL=example@example.com -e PGADMIN_DEFAULT_PASSWORD=your_password -p 8081:80 -d dpage/pgadmin4
```

`Download Dependencies`

```
mvn clean install
```

`This will install all the dependencies from POM.xml`

`Run the Project`

```
mvn spring-boot:run
```

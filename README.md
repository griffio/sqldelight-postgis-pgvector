# SqlDelight 2.2.x Postgis PgVector support 

https://github.com/cashapp/sqldelight

Experimental support for Postgis and PgVector types.

Example:

Uses two SqlDelight modules:

- [pgvector-module](https://github.com/griffio/sqldelight-pgvector-module-app/tree/master/pgvector-module)
- [postgis-module](https://github.com/griffio/sqldelight-postgis-module-app/tree/master/postgis-module)

``` kotlin
sqldelight {
    databases {
        create("Sample") {
            ...
            dialect(libs.sqldelight.postgresql.dialect)
            module("io.github.griffio:sqldelight-postgis:0.0.3")
            module("io.github.griffio:sqldelight-pgvector:0.0.2")
        }
    }
}
```
----

PostgresApp is preinstalled with extensions

https://postgresapp.com/extensions/

```shell
createdb geovectors &&
./gradlew build &&
./gradlew flywayMigrate
```

Flyway db migrations
https://documentation.red-gate.com/fd/gradle-task-184127407.html

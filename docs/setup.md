

#### Tools:
* Install **docker-engine** and **docker-compose**
* Java 8 (higher version of Java will not work with Cucumber)
* sbt
* Intellij (to run and debug scenarios)


#### MySQL and Postgres Setup:
* Inside your project directory execute command: **_docker-compose up -d_** to bring up the MySQL and Postgres containers
* Log in to the MySQL container using: **_docker container exec -it mysql  mysql -udata_validation_user -pdata_validation_pass_**
* From the file **_./backup/mysql_db.sql_** execute all the sql queries to setup the MySQL database which acts as the source database and then exit out of the container
* Log in to postgres container using: 
    ```bash
  $ chmod +x psql
  $ ./psql
    ```
  Then, execute the sql queries from **_./backup/postgres_db.sql_** to setup the Postgres database which acts as the target database ./backup/mysql_db.sql

#### Airflow Setup:
```bash
$ cd airflow
$ echo -e "AIRFLOW_UID=$(id -u)\nAIRFLOW_GID=0" > .env
$ docker-compose up airflow-init
```

#### Running the scenarios:
* Bring up the MySQL and Postgres containers by executing: **_docker-compose up_**
* In another terminal bring up the Airflow containers by first changing directory to airflow and then executing: **_docker-compose up_**
* Open a browser window and go to **_localhost:8080_** and activate the **__compute_training_model_accuracy__** DAG
* In yet another terminal execute: **__sbt test__** from project root directory to run all the scenarios

#### Reports:
After running all scenarios the reports will be available in **__.target/cucumber-reports/report.html__**

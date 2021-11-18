Feature: Validate data integrity between source and target databases

  Scenario: Validate that the data loaded to target table and all transformation are implemented as expected
    Given Airflow DAG job is triggered successfully
    When Airflow DAG job is executed successfully
    And Data from Postgres table to MySQL table loaded successfully
    Then Target table is loaded with transformed data and it is matching between each other

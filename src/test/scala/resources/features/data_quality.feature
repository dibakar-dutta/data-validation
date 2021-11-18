Feature: Target database data integrity

  Scenario: Validate that the data quality in target table
    Given Airflow DAG job is triggered successfully
    When Airflow DAG job is executed successfully
    And Data from Postgres table to MySQL table loaded successfully
    Then Target table data quality should be as expected

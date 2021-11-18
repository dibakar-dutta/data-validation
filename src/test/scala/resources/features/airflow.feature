Feature: Airflow DAG job validation

  Scenario: Validate that Airflow DAG job is completed for the sample DAG job created in Airflow
    Given Airflow DAG job is triggered successfully
    When Airflow DAG job is executed successfully
    Then Airflow DAG job status is marked as “Complete”
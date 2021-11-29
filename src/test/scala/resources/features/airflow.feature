Feature: Airflow DAG job validation

  Scenario Outline: Validate that Airflow DAG job is completed for the sample DAG job created in Airflow
    Given Airflow DAG job <dag_id> is triggered successfully
    When Airflow DAG job with <dag_id> is executed successfully
    Then Airflow DAG job status is marked as “Complete”
    Examples:
      | dag_id                          |
      | compute_training_model_accuracy |
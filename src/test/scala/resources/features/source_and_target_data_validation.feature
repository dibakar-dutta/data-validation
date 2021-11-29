Feature: Validate data integrity between source and target databases

  Scenario Outline: Validate that the data loaded to target table and all transformation are implemented as expected
    Given Airflow DAG job <dag_id> is triggered successfully
    When Airflow DAG job with <dag_id> is executed successfully
    And Data from <source_table> to <target_table> loaded successfully
    Then Target table is loaded with transformed data and it is matching between each other
    Examples:
      | dag_id                          | source_table      | target_table      |
      | compute_training_model_accuracy | employees_source  | employees_target  |
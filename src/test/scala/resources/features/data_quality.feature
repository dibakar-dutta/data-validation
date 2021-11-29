Feature: Target database data integrity

  Scenario Outline: Validate that the data quality in target table is as expected
    Given Airflow DAG job <dag_id> is triggered successfully
    When Airflow DAG job with <dag_id> is executed successfully
    And Data from <source_table> to <target_table> loaded successfully
    Then Target table data quality should be as expected
    Examples:
      | dag_id                          | source_table      | target_table      |
      | compute_training_model_accuracy | employees_source  | employees_target  |

  Scenario Outline: Validate that the Uniqueness of target table column is as expected for the Specified column in Target Table
    Given Airflow DAG job <dag_id> is triggered successfully
    When Airflow DAG job with <dag_id> is executed successfully
    And Data from <source_table> to <target_table> loaded successfully
    Then Target table data quality should be Unique for the Column <column_name>
    Examples:
      | dag_id                          | source_table      | target_table      | column_name |
      | compute_training_model_accuracy | employees_source  | employees_target  | id          |

  Scenario Outline: Validate that the Completeness of target table column is as expected for the Specified column in Target Table
    Given Airflow DAG job <dag_id> is triggered successfully
    When Airflow DAG job with <dag_id> is executed successfully
    And Data from <source_table> to <target_table> loaded successfully
    Then Target table data quality should be Complete for the Column <column_name>
    Examples:
      | dag_id                          | source_table      | target_table      | column_name   |
      | compute_training_model_accuracy | employees_source  | employees_target  | name          |
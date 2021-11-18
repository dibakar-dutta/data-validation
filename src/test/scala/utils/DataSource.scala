package utils

trait DataSource {
  def driver: String
  def url: String
  def user: String
  def password: String
  def dbTable: String
}

object MySQlDataSource extends DataSource {
  override def driver: String = "com.mysql.jdbc.Driver"
  override def url: String = "jdbc:mysql://localhost:3306/validation_source"
  override def user: String = "data_validation_user"
  override def password: String = "data_validation_pass"
  override def dbTable: String = "employees_source"
}

object PostgresDataSource extends DataSource {
  override def driver: String = "org.postgresql.Driver"
  override def url: String = "jdbc:postgresql://localhost:5432/validation_target"
  override def user: String = "data_validation_user"
  override def password: String = "data_validation_pass"
  override def dbTable: String = "employees_target"
}

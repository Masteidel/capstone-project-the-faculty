provider "aws" {
  region = "us-east-1"
}

resource "aws_db_instance" "learn-mysql" {
  identifier = "database-emp"
  allocated_storage = 20
  storage_type = "gp2"
  engine = "mysql"
  engine_version = "9.0.1"
  instance_class = "db.t3.micro"
  db_name = "faculty_prod"
  username = "root"
  password = ""
  parameter_group_name = "default.mysql9.0.1"

  skip_final_snapshot = true
  publicly_accessible = true
}

output "rds_endpoint" {
  value = "aws_db_instance.my_database.endpoint"
}
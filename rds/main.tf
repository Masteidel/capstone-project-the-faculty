provider "aws" {
  region = "us-east-1"
}

resource "aws_db_instance" "learn-mysql" {
  identifier = "database-emp"
  allocated_storage = 20
  storage_type = "gp2"
  engine = "mysql"
  engine_version = "8.0"
  instance_class = "db.t3.micro"
  db_name = "faculty_prod"
  username = var.db_username
  password = var.db_password
  parameter_group_name = "default.mysql8.0"

  skip_final_snapshot = true
  publicly_accessible = true
}

output "rds_endpoint" {
  value = aws_db_instance.learn-mysql.endpoint
}

variable "db_username" {
  description = "The database master username"
  type        = string
}

variable "db_password" {
  description = "The database master password"
  type        = string
  sensitive   = true
}
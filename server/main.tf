provider "aws" {
  region = "us-east-1" # replace with your_preferred_region
}

# Create EC2 instance
resource "aws_instance" "server" {
  ami                    = "ami-0c7217cdde317cfec"
  instance_type          = "t2.micro"
  key_name               = "ec2-key"
  vpc_security_group_ids = ["sg-0b61ede460cbecf2a"]
}

output "ssh_command" {
  value = "ssh -i /home/lili/ec2-key.pem ubuntu@${aws_instance.server.public_ip}"
}
import os
import time

os.chdir("..")
os.system("./build_all.sh")
os.chdir("integration")

os.system("docker network create street_network")
os.system("docker-compose -f ../docker-compose.yml up -d activemq")
os.system("docker-compose -f ../docker-compose.yml up -d mymongo")

time.sleep(10)
os.system("docker run --rm --name ctrl1 -dt -e \"FROM=1\" \"-e TO=1\" -e \"CS=activemq\" -e \"CS_PORT=5672\" --network street_network gr2/controller")
os.system("docker-compose -f ../docker-compose.yml up -d ctlms")

time.sleep(10)
os.system("docker logs ctrl1")
os.system("docker stop ctrl1")

os.system("docker-compose -f ../docker-compose.yml down")
os.system("docker network rm street_network")

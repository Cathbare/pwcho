FROM ubuntu:latest
RUN apt-get update -y
RUN apt-get install -y tzdata
RUN apt-get install apache2 -y
EXPOSE 80
CMD ["apache2ctl","-D", "FOREGROUND"]
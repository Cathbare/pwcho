FROM java:8
COPY . /
WORKDIR /
RUN javac App.java
CMD["java", "-jar", "mysql-connector-java-5.1.6.jar","App"]

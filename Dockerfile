FROM openjdk:11
ADD target/blog-app.war blog-app.war
EXPOSE 9090
ENTRYPOINT [ "java" ,"-jar","blog-app.war"]
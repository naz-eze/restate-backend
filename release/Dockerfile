FROM amazoncorretto:latest
MAINTAINER Team <naz@restateai.com>
EXPOSE 8080 3306 80
RUN mkdir -p /app
COPY restate.jar /app/restate.jar
COPY init.sh /app/init.sh
RUN chmod 755 /app/init.sh
ENTRYPOINT ["sh", "-c"]
CMD ["/app/init.sh"]

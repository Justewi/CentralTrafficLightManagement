FROM dockcross/base as builder

#RUN mkdir /work
COPY . /work
WORKDIR /work
RUN ["/bin/bash", "-e", "./buildInContainer.sh"]

FROM debian


#Add builded executable
COPY --from=builder /work/controler .
COPY --from=builder /work/startControlers.sh .
WORKDIR /build
ENTRYPOINT ["ls", "-la"]
#CMD ["2", "localhost", "5672"]
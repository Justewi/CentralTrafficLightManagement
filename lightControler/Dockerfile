FROM dockcross/base as builder

RUN mkdir /src
COPY . /src
WORKDIR /src
RUN bash build.sh

FROM debian

#Add builded executable
COPY --from=builder /src/lightCtrlR .
CMD ls -lah
CMD ./lightCtrlR $ID $CS $CS_PORT
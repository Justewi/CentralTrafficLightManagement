FROM gr2/cppbuilder as builder

COPY . .
RUN bash build.sh

######################################################

FROM debian

#Fetch executable
COPY --from=builder /usr/lib/libamqpcpp.* /usr/lib/
COPY --from=builder /src/lightCtrlR .

CMD ./lightCtrlR $ID $CS $CS_PORT
FROM gr2/cppbuilder as builder

COPY . .
RUN bash build.sh

######################################################

FROM debian

#Fetch executable
COPY --from=builder /usr/lib/libamqpcpp.* /usr/lib/
COPY --from=builder /src/build/lightCtrlR ./controler
COPY ./startControlers.sh .

CMD bash startControlers.sh $NB $CS $CS_PORT
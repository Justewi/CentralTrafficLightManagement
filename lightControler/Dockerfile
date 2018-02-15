FROM gr2/cppbuilder as builder

COPY . .
RUN bash build.sh

######################################################

FROM debian

#Fetch executable
COPY --from=builder /usr/lib/libamqpcpp.* /usr/lib/
COPY --from=builder /src/controler ./controler
COPY ./startControlers.sh .

CMD bash startControlers.sh 1 $FROM $TO $CS $CS_PORT

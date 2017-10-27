FROM alpine

#Add builded executable
COPY lightCtrlR .

RUN ["./lightCtrlR", "$ID", "$CS", "$CS_PORT"]
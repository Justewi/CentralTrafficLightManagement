#!/bin/bash
#
# File:   startControlers.bash
# Start several controlers.
#

echo "Starting controlers script"
EXE_NAME=controler

serverAddr=""
serverPort=""

[[ $# -lt 2 ]] && echo "Missing parameter, usage: $0 from to [serverAddr] [serverPort]" && exit 1;
from=$1
to=$2
[[ $# -gt 2 ]] && serverAddr=$3
[[ $# -gt 3 ]] && serverPort=$4

declare -a processes
kill_processes(){
    echo "Killing all processes..."
    kill ${processes[@]}
    echo "Done. Bye."
}
handle_sigint(){
    kill_processes
    exit 0
}
trap handle_sigint SIGINT

for ((i = $from; i <= $to; i++)); do
    ./${EXE_NAME} "ctrl$i" $serverAddr $serverPort &
    processes+=($!)
done

echo "All controlers started."

while true ; do
    echo "$PS2"
    read cmd
    case "$cmd" in
        "w" | "walker")
            kill -10 ${processes[0]}
            ;;
        "p" | "ping")
            # send SIGUSR2 to all processes
            kill -12 ${processes[@]}
            ;;
        "s" | "stop")
            break
            ;;
    esac
done

kill_processes

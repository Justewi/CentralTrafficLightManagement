#!/bin/bash
#
# File:   startControlers.bash
# Start several controlers.
#

echo "Starting controlers script"

serverAddr=""
serverPort=""

[[ $# -eq 0 ]] && echo "Missing parameter, usage: $0 numberOfCtrl [serverAddr] [serverPort]" && exit 1;
[[ $# -gt 1 ]] && serverAddr=$2
[[ $# -gt 2 ]] && serverPort=$3

declare -a processes

for ((i = 1; i <= $1; i++)); do
    ./controler "ctrl$i" $serverAddr $serverPort &
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

echo "Killing all processes..."
kill ${processes[@]}
echo "Done. Bye."

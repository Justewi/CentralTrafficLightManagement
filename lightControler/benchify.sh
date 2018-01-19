#! /bin/bash
size=(10 120 160 1 20 40 5 80 250)

#https://stackoverflow.com/questions/1527049/join-elements-of-an-array
function join_by { local d=$1; shift; echo -n "$1"; shift; printf "%s" "${@/#/$d}"; }

for i in "${size[@]}"
do :
  ./startControlers.sh $i localhost 5672 > bench/bench_$i.log
  python benchmark_ping.py < bench/bench_$i.log > bench/bench_$i.res
done
#echo \"$(join_by " " ${size[@]})\"
python benchmark_plot.py "$(join_by " " ${size[@]})"

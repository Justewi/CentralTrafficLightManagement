#! /bin/bash
size=(1 5 10 20 40 80 100 120 160 250 500 750 1000)

#https://stackoverflow.com/questions/1527049/join-elements-of-an-array
function join_by { local d=$1; shift; echo -n "$1"; shift; printf "%s" "${@/#/$d}"; }

for i in "${size[@]}"
do :
  echo "Start test for $i"
  ./startControlers.sh 1 $i $1 5672 > bench/bench_$i.log
  python benchmark_ping.py < bench/bench_$i.log > bench/bench_$i.res
done
#echo \"$(join_by " " ${size[@]})\"
python benchmark_plot.py "$(join_by " " ${size[@]})"

import re
import sys

pattern = "Ping response received in ([0-9]+)ms"
regex = re.compile(pattern)
matches = regex.findall(sys.stdin.read())
results = list(filter(lambda x : x != '0', matches))

for val in results:
    print(val + " ", end="")

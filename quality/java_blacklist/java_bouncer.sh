#!/bin/sh
echo "Checking source files for usages of blacklisted java code..."
DIR="$(dirname "$0")"
grep -R --include=*.java -F -f $DIR/java_blacklist.txt $1
if [ $? = 1 ]; then
  echo "No blacklisted java code found."
  exit 0;
else
  echo "Blacklisted java code found - failing test."
  exit 1;
fi

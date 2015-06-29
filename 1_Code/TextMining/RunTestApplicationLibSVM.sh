#!/bin/bash

if [[ $# -lt 6 ]] ; then
    echo 'Too less arguments - usage: -s <start-val> -e <end-val> -d <yes/no>'
    exit 1
fi
if [[ $# -gt 6 ]] ; then
    echo 'Too much arguments - usage: -s <start-val> -e <end-val> -d <yes/no>'
    exit 1
fi

while [[ $# > 0 ]]
do
key="$1"

case $key in
    -s|--start)
    START="$2"
    shift # past argument
    ;;
    -e|--end)
    END="$2"
    shift # past argument
    ;;
    -d|--debug)
    DEBUG="$2"
    shift # past argument
    ;;
    --*)
			# unknown option
    ;;
    *)
            # unknown option
    ;;
esac
shift # past argument or value
done

if
	[[ "$DEBUG" != "yes" ]]; then
	if
		[[ "$DEBUG" != "no" ]]; then
		echo "-d must be yes or no"
		exit 1
	fi
fi


if ! [ $START == ${START//[^0-9]/} -a $END == ${END//[^0-9]/} ]; then
	echo "error: -s and -e must be Integer" >&2; 
	exit 1
fi

if
	[[ ${END} -lt ${START} ]] ; then
	echo "-e must be equal or greater than -s"
	exit 1
fi

if [[ -n $1 ]]; then
    echo "Last line of file specified as non-opt/last argument:"
    tail -1 $1
fi

echo "Values are set to: "
echo "Start:  $START"
echo "END:  $END"
echo "Debug-Flag: $DEBUG"

for i in `seq $START $END`
do
	mvn -e exec:java -Dexec.mainClass="org.textmining.test.svm.TestApplicationLibSVM" -Dexec.classpathScope="test" -Dexec.args=$DEBUG 
	sleep 2
	mv logs/test_libsvm.log logs/test_libsvm_$i.log
	echo "Logfile was written to logs/test_libsvm_$i.log"
	sleep 2
done

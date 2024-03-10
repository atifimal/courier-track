#!/bin/bash

map=(
"41.00496976820543 28.654884770030193"
"41.00530577395132 28.65507788906752"
"41.00562153682568 28.65530319461107"
"41.005925153547246 28.655533864572323"
"41.00626115442177 28.65573234802736"
"41.00656072002194 28.655936195900093"
"41.006904813963175 28.65616150144365"
"41.00720032850203 28.65633852722786"
"41.00744321617186 28.65652628184749"
"41.00760109267714 28.656097128431202"
"41.00772658398842 28.65578062778669"
"41.007856123155825 28.65545876272448"
"41.00754441910337 28.65522272834552"
"41.007257002371745 28.654997422801966"
"41.00694124733412 28.65481503260004"
"41.006807658208864 28.655292465775666"
)

PARALLEL_REQUESTS=5
URL="http://localhost:8089/courier-track"

map_length=${#map[@]}

for ((i=0; i<$map_length; i++))
do
    latlng=${map[i]}

    lat=$(echo $latlng | cut -d' ' -f1)
    lng=$(echo $latlng | cut -d' ' -f2)

    curl -X POST -H "Content-Type: application/json" -d "{\"courierId\": 2, \"lat\":\"$lat\", \"lng\":\"$lng\"}" $URL &
    
    if (( $i % $PARALLEL_REQUESTS == 0 )); then
            wait
    fi
done

wait

echo "OK"
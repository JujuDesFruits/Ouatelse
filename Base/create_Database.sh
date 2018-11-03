#!/bin/bash

clear
base="Ouatelse_Gp4_P1"

echo "Base : $base "
echo "User : $USER"

tmp=0

error=$(mysql -u root -p < Database.txt 2>&1)

if [[ ! $error ]];
then
	while [ $tmp == 0 ]; do
	    read -p "Would you want to simulate a full database ? (Yes/No)" answer
	    if [[ "$answer" == "YES" || "$answer" == "yes" || "$answer" == "Yes" || "$answer" == "Y" || 		"$answer" == "y" ]];
	    then
	        error=$(mysql -u root -p < Simulation.txt 2>&1)
		if [[ ! $error ]];
		then
		    echo "Full database succefully built"
		else
		    echo "The database wasn't created succefully. Stopped at Simulation.txt" 
		    echo $error
		fi
		tmp=$(($tmp + 1))
	    elif [[ "$answer" == "NO" || "$answer" == "no" || "$answer" == "No" || "$answer" == "N" || 			"$answer" == "n" ]];
	    then
		echo "Empty database succefully built"
		tmp=$(($tmp + 1))
	    else 
		echo "Please answer yes or no."
	    fi
	done;
else
	echo "The database wasn't created succefully. Stopped at Database.txt"
	echo $error
fi

#!/bin/sh

php generator.php

PGPASSWORD=segel psql -U segel -h localhost segelverein < start.sql

echo "Would you like to log in segelverein [y/n]?"
read ans


if [ $ans = y -o $ans = Y -o $ans = yes -o $ans = Yes -o $ans = YES ]
then
PGPASSWORD=segel psql -U segel -h localhost segelverein
fi

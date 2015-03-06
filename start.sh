#!/bin/sh

php generator.php

PGPASSWORD=segel psql -U segel -h localhost segelverein < start.sql


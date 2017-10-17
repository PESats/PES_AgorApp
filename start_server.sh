#!/bin/bash

source /home/alumne/.rvm/scripts/rvm

git pull origin master

cd Backend/AgorApp && bin/rails server


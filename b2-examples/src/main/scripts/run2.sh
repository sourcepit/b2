#!/bin/sh
SELF=`readlink -f $0`
SCRIPTDIR=`dirname $SELF`

export BASE="$SCRIPTDIR/"
export M2_HOME=$BASE\apache-maven-3.0.3
export MAVEN_OPTS=-Duser.home="$BASE $MAVEN_OPTS"

echo User Home Directory    : $BASE
echo Maven Home Directory   : $M2_HOME
echo Maven Opts:			: $MAVEN_OPTS

echo Starting build for $1
cd $1
"$M2_HOME/bin/mvn" clean package

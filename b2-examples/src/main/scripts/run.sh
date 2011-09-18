#!/bin/sh
SELF=`readlink -f $0`
SCRIPTDIR=`dirname $SELF`

export BASE="$SCRIPTDIR/"
export M2_HOME="$BASE""apache-maven-3.0.3"
export MAVEN_OPTS="-Duser.home=$BASE $MAVEN_OPTS"

echo ------------------------------------------------------------------------
echo Setting build environment
echo ------------------------------------------------------------------------
echo User Home Directory    : $BASE
echo Maven Home Directory   : $M2_HOME
echo Maven Opts:            : $MAVEN_OPTS

export MAVEN_EXEC="$M2_HOME""/bin/mvn"
if [ -z "$MAVEN_ARGS" ] ; then
  export MAVEN_ARGS="clean verify"
fi

cd $1

if [ -f build.sh ] ; then

exec $PWD"/build.sh" $*

else

echo
echo ------------------------------------------------------------------------
echo Starting module build for \'$1\'
echo ------------------------------------------------------------------------
echo Directory    : $PWD
echo Command Line : $MAVEN_EXEC $MAVEN_ARGS
echo

exec "$MAVEN_EXEC" $MAVEN_ARGS
fi

cd $BASE

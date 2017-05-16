#!/usr/bin/env bash

version=`grep '<version>.*</version>' pom.xml | awk -F'</?version>' 'NR==1{print $2}'`
echo "Current version is: $version"
new_version="$(echo $version | sed 's/-SNAPSHOT//')"
version_num=$[${new_version##*.} + 1]
new_version=${new_version%.*}"."$version_num"-SNAPSHOT"

read -p "What is the new development version for fangcloud-java-sdk (default to $new_version, auto filled with -SNAPSHOT):" user_version
if [ $user_version ]
then
    user_version=${user_version%-*}
    new_version=$user_version"-SNAPSHOT"
fi

# update pom version
echo "update pom version..."
find . -name pom.xml | xargs sed "s/$version/$new_version/" -i
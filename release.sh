#!/usr/bin/env bash

github_token=$GITHUB_TOKEN

# enter real directory of the script
cd $(dirname $(readlink -f $0))
git checkout master
git fetch
git reset --hard origin/master
version=`grep '<version>.*</version>' pom.xml | awk -F'</?version>' 'NR==1{print $2}'`

checkout_branch=false
update_version=false
deploy_maven=false
create_tag=false
deploy_java_doc=false

while getopts "brdjtv" arg; do
	case $arg in
		r)
			create_release=true
			;;
		v)
			update_version=true
			;;
		t)
			create_tag=true
			;;
		d)
			deploy_maven=true
			;;
		j)
			deploy_java_doc=true
			;;
		?) # UNKNOWN option!
			echo "UNKNOWN option: $arg!"
			exit 1
			;;
	esac
done
shift $((OPTIND-1))



echo "Current version is: $version"
new_version="$(echo $version | sed 's/-SNAPSHOT//')"
echo "Use new version: $new_version"


$update_version && {
	echo '================================================================================'
	echo "Update version!"
	echo '================================================================================'

    # update version in docs
	echo "update maven dependency version in docs..."
    sed "s#<version>.*</version>#<version>$new_version</version>#" -i README.md
	sed "s/java-sdk:[0-9].[0-9].[0-9]/java-sdk:$new_version/" -i README.md

	git add -A
	git commit -m "release v$new_version"
	git push

	git checkout -B "v$new_version"

	# update pom version
	echo "update pom version..."
	find . -name pom.xml | xargs sed "s/$version/$new_version/" -i

	$create_tag && {
		git add -A
		git commit -m "release v$new_version"
		git tag -a "v$new_version" -m "release v$new_version" -f
		git push origin "v$new_version" -f
		echo "finish create tag v$new_version"
	}
}

$deploy_maven && {
	echo '================================================================================'
	echo 'Deploy to maven center repo...'
	echo '================================================================================'
	mvn clean deploy -Dmaven.test.skip=true -DperformRelease=true
	echo "deploy to sonatype oss successfully, enter https://oss.sonatype.org/#stagingRepositories to releasethe $new_version to maven central repository"
}


$deploy_java_doc && {
	# mvn install -Dmaven.test.skip=true -PsrcDoc

	git checkout gh-pages
	gh_version="v"${version%.*}".x"
	gh_new_version="v"${new_version%.*}".x"
	count=`grep -c "$gh_new_version" index.html`
	if [ $count == "0" ]
	then
		sed "/$gh_version/ i\    <li><a href=\"apidocs/$gh_new_version\">$gh_new_version</a></li>" -i index.html
	else
		rm -rf "apidocs/$gh_version/"
	fi
	mv target/apidocs "apidocs/$gh_new_version"
	# sed "s#\".*/index.html\"#\"$new_version/index.html\"#" -i apidocs/index.html

	git add -A
	git commit -m "add javadoc for $new_version"
	git push
	echo "push gh-pages successfully"
}

$create_release && {
    git checkout master
	version_line=`awk "/$new_version/{print NR}" ChangeLog.txt`
	end_line=`awk "NR>$[version_line + 1]&&/---------------------------------------------/{print NR ;exit;}" ChangeLog.txt`
	if [ -z $end_line ]
	then
		last_line=`awk 'END{print NR}' ChangeLog.txt`
		change_log=`sed -n "$[version_line + 2],$last_line p" ChangeLog.txt`
	else
		change_log=`sed -n "$[version_line + 2],$[end_line - 3] p" ChangeLog.txt`
	fi

	title=`sed -n "$version_line p" ChangeLog.txt`
	echo "title: $title"
	echo -e "change log:\n$change_log"

	change_log=`tr "\n" "^" <<< "$change_log"`
	change_log=`echo $change_log | sed 's/\^/\\\\n/g'`
	body="## "${title}"\\n"${change_log}


	upload_response=`curl -X POST \
			https://api.github.com/repos/yifangyun/fangcloud-java-sdk/releases \
			-H 'accept: application/vnd.github.v3+json' \
			-H "authorization: token $github_token" \
			-H 'cache-control: no-cache' \
			-H 'content-type: application/json' \
			-d '{
				"tag_name": "'"$new_version"'",
				"target_commitish": "master",
				"name": "'"$new_version"'",
				"body": "'"$body"'",
				"draft": true,
				"prerelease": false
			}'`

	upload_url="$(echo $upload_response | python -c "import sys, json; print json.load(sys.stdin)['upload_url']")"
	html_url="$(echo $upload_response | python -c "import sys, json; print json.load(sys.stdin)['html_url']")"

	tar_name="fangcloud-java-sdk-${new_version}.tar"
	zip_name="fangcloud-java-sdk-${new_version}.zip"
	tar_upload_url=${upload_url%%{*}"?name=${tar_name}"
	zip_upload_url=${upload_url%%{*}"?name=${zip_name}"

	tar_browser_download_url=`curl -X POST \
			"${tar_upload_url}" \
			-H 'accept: application/vnd.github.v3+json' \
			-H "authorization: token $github_token" \
			-H 'cache-control: no-cache' \
			-F "filename=@target/build/${tar_name}" \
			| python -c "import sys, json; print json.load(sys.stdin)['browser_download_url']"`

	if [ -z $tar_browser_download_url ]
	then
		echo "upload ${tar_name} successfully, the download url is ${tar_browser_download_url}"
	fi

	zip_browser_download_url=`curl -X POST \
			"${zip_upload_url}" \
			-H 'accept: application/vnd.github.v3+json' \
			-H "authorization: token $github_token" \
			-H 'cache-control: no-cache' \
			-F "filename=@target/build/${zip_name}" \
			| python -c "import sys, json; print json.load(sys.stdin)['browser_download_url']"`

	if [ -z $zip_browser_download_url ]
	then
		echo "upload ${zip_name} successfully,  the download url is ${zip_browser_download_url}"
	fi

	echo "$new_version release has been created successfully, enter $html_url to check and release draft"

}
<h3>очерёдность запуска всего этого добра)</h3>

mvn clean package

sudo chmod 755 -R .

docker build -f Dockerfile -t marvel_comics_app .

docker run -d -p 3306:3306 -v ~/Documents/java/marvel/mysql/etc/mysql/:/etc/mysql/conf.d -v ~/Documents/java/marvel/mysql/var/lib/mysql:/var/lib/mysql --name mysql-docker-container -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=sfg_dev -e MYSQL_USER=sfg_dev_user -e MYSQL_PASSWORD=123 mysql:latest

docker run -t --link mysql-docker-container:mysql -p 8080:8080 --name jar-app-marvel-container marvel_comics_app

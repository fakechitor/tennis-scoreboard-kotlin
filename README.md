![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Apache Tomcat](https://img.shields.io/badge/apache%20tomcat-%23F8DC75.svg?style=for-the-badge&logo=apache-tomcat&logoColor=black)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)

# Tennis scoreboard
Реализация проекта №4 из [роадмапа](https://zhukovsd.github.io/java-backend-learning-course/projects/tennis-scoreboard/) на Kotlin.

## Использованные технологии:
- Jakarta servlets
- JSP
- Hibernate
- ModelMapper
- Tomcat

## Запуск
1) Скопировать репозиторий
```bash
git clone https://github.com/fakechitor/tennis-scoreboard-kotlin
```
2) Перейти в корень проекта
```bash
cd tennis-scoreboard-kotlin/
```
3) Сбилдить war файл
```bash
mvn clean install
```
4) Переместить war файл в папку томката
```bash
cd target/
mv TennisScoreboard-1.0-SNAPSHOT.war <путь к томкату>/tomcat/webapps
```
5) Запустить томкат
```bash
cd <путь к томкату>/tomcat/bin
./startup.sh
```

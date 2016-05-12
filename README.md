Welcome to BookStoreRobot® readme!
===================
What BookStoreRobot® is?
-------------

Basicly, BookStoreRobot® is a tool that will make your life better. This will keep things simple and enjoyable. Robot will collect data associated with books that are cheaper than usually or completelly for free! Instead of clicking and checking all bookstores that you are interested in, the Robot will do it for you. This is a tool that every wise man should have. 

How do I run my BookStoreRobot®?
-------------
It depends on what you already have. If you are provided with .jar file with a Robot inside, you bacisly go to the .jar directory and type in console:
``` sh
java -jar BookStoreRobot.jar
```
This will execute robot. Otherwise if you have  just a link to github repository you can clone it by typing:
``` sh
git clone https://github.com/marcintalaga/BookStoreRobotFix.git
```
Next step you'd like to take will be importing existing maven project to your IDE. After that open StartRobot class and hit F11 key. This will run robot.

How do I run my BookStoreRobot® tests?
-------------
Open your Robot's directory and type in console:
``` sh
mvn test
```
Writing this will run all tests. Some of them may take a long time to be runned. To avoid time consuming tests you can type instead:
```sh
mvn test -Dgroups=fastTests
```


What do I need to have on my machine to run this Robot?
-------------
All you have to have is:
> - java 1.8.0_91
> - Apache Maven 3.0.5
> - internet connection

Those are all things you have to take care of. Other things that are necessary for Robot to be properly executed would be downloaded due to maven dependencies included in pom.xml file.

You don't want to update mysql version. This can cause a lot unexpected and unpredictable problems, newer versions are not supported by current version of program.

How can I change URL related with one of available bookstores?
-------------
First of all you have to find URL.properties file. It is stored in /BookStoreRobotFix/src/main/resources directory. When some changes in URL has occured, simply replace proper URL with new one. Save changes.

How can I add bookstore to my robot?
-------------
This is a little tough to do. As you can see in URL.properties file, every single bookstore has its own set of attributes and patterns that needs to be filled. If you have no experiance in parsing and scrapping html, please contact one of the creators of robot via e-mail:
```sh
paulina-koks-nie-dziewczyna@buziaczek.ru
marcintalaga@gmail.ru
```
If you are experianced in this field, this should not be a problem for you:)


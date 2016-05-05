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


What do I need to have on my machine to run this Robot?
-------------
All you have to have is:
> - java 1.8.0_91
> - Apache Maven 3.0.5
> - internet connection

Those are all things you have to take care of. Other things that are necessary for Robot to be properly executed would be downloaded due to maven dependencies included in pom.xml file.


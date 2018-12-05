#zrodlo
FROM java:8  

#skopiowanie pliku z programem
COPY . /

#zmiana folderu z ktorego wywolywane jest polecenie
WORKDIR /  

#kompilacja programu
RUN javac Program.java

#wywolanie programu przy starcie kontenera
CMD ["java", "-classpath", "mysql-connector-java-5.1.6.jar:.","Program"]

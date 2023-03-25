
## Autor:
### Sergio Andrés Otero Herrera

# Taller 7 AREP:
En este taller se crearon instancias del codigo usando AWS de forma que quedara como muestra el siguiente diagrama:
![image](https://user-images.githubusercontent.com/98189066/227730901-ebd61ee2-5dfb-4a14-b953-c4b5f5751502.png)



## Prerrequisitos
- GIT
- AWS
- JAVA
- MVN

## Instalación
De querer usar este codigo de forma local, se tiene que hacer lo siguiente:

Se clona el repositorio

```
git clone https://github.com/Sergi0tero/AREP_Taller6.git
```

Ahora, si queremos verificar la integridad del codigo

```
mvn package
```
```
mvn clean install
```
Corremos el codigo:
```
java -cp target/classes Api
```
y luego 

```
java -cp target/classes UrlReader
```
**Si se quiere probar de forma local, se debe cambiar las IPs en las clases ```URLReader``` y ```Api``` a localhost**

## Diseño
El proyecto fue realizado en Java. El ciclo de vida empieza por el usuario, quien utiliza la pagina inicial con la ruta /hello, este consulta directamente a la API URLReader. Esto posteriormente consulta a la API externa, la informacion resultante la recibe esta informacion y se le muestra en pantalla.

## Modular
Estas son las diferentes capaz que podemos ver:
- API
- URLReader
- AWS

## Pruebas y proceso

![image](https://user-images.githubusercontent.com/98189066/226756018-a16843a6-cf0c-40a4-b616-92428abec9a6.png)
consulta a la API subida en azure:
![image](https://user-images.githubusercontent.com/98189066/226789647-9e201cb9-9978-41ff-8710-9b0fdf0b92f7.png)
https://youtu.be/uWESQ2hX-G0

## Version
1.0


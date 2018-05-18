# SOA-ATTACK-TEST-A
# SOA-ATTACK-TEST-B
# Aplicación que expone permisos.

Esta aplicación expone los permisos para la escritura y lectura de contactos.
 <uses-permission android:name=""></uses-permission>
    <uses-permission android:name=""></uses-permission>


| Permisos| Referencia|
| ----- | ---- |
| android.permission.WRITE_CONTACTS | [REF](https://developer.android.com/guide/topics/permissions/overview)|
|android.permission.READ_CONTACTS | [REF](https://developer.android.com/guide/topics/permissions/overview) |




## Tabla de contenido

- [Installation](#installation).
- [Usage example](#usage-example).
- [Development set up](#development-set-up).
- [Test the solution](#test-the-solution).

### Installation

Se debe de crear un Android Virtual Device basado en API v.15 de Android para poder realizar la prueba.
Ambos repositorios se deben de clonar como proyectos de Android Studio.

Esta es la aplicación A la cual expone los permisos de lectura y escritura a la libreta de contactos utilizados por la app B.

La aplicación B, la encontrará en este repositorio. (Referencia)[https://github.com/CURSOS-TEC/SOA-ATTACK-TEST-B]

### Usage example
En la aplicación A se debe de ejecutar y luego dejar en segundo plano.
La aplicación B se debe de ingresar los datos: 

Para el campo Name: Ingresar `` Test `` como nombre y `` 12345 `` como número telefónico

### Test the solution

El formato del receiver en el archivo AnfroidManifest.xml se dede de modificar lo el siguiente para percibir la solución.
 ```xml
      <receiver android:name=".ContactReceiver" android:permission="android.permission.WRITE_CONTACTS" >
            <intent-filter>
                <action android:name="com.soa.jnavarro.soascaleattacka.EditContact" />
                <category android:name="android.intent.category.DEFAULT"/>
            </intent-filter>
      </receiver>
  ```

Link sobre receiver: 
http://programandoointentandolo.com/2014/08/broadcast-receiver-android.html

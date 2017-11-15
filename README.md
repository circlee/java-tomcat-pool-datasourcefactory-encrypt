# java-tomcat-pool-datasourcefactory-encrypt
***
 참조 : http://www.jdev.it/encrypting-passwords-in-tomcat/

 tomcat DataSource Resource 사용에서 org.apache.tomcat.jdbc.pool.DataSourceFactory 에 대한 상속구현.
 
 user, password, url 암호화된 문자열로 사용하기위해 작성.
 
 * tomcat 8.0.39 에서 테스트 작성되었음.
 
***
## com.dklee.util.Encryptor
 defaultSecretKey를 수정, encrypt 메소드를 통해 암호화된 값을 얻을수 있다.
***
## com.dklee.tomcat.jdbc.pool.EncryptDataSourceFactory
 org.apache.tomcat.jdbc.pool.DataSourceFactory 상속하여 createDataSource 오버라이딩 decrypte 로직 추가.

***

빌드된 jar에서 아래와 같이 암호화된 문자열을 출력할 수 있다.

`
java -cp encryptDataSourceFactory-1.0.jar com.dklee.util.Encryptor test
`

출력

`
test:e43f9e6623153e1a9c39ca25cdbebbb6
`

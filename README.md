# walletService
restAPI for wallet related services. TechStack : springBoot, JPA, mysql



Steps to Run
=============

1. run ./src/main/resources/sql_create.sql on your mySql db. It would create all required tables.

2. Update Database credentials in ./src/main/resources/application.properties 
		spring.datasource.url=jdbc:mysql://HOSTNAME:3306/SCHEMA_NAME
		spring.datasource.username=USERNAME
		spring.datasource.password=PASSWORD
		
3. Build the project using maven command "mvn clean install". It will create "agrostar.war" in target folder. 

4. To run it please deploy agrostar.war on a web Server [context= /agrostar]
				
				or
				
	run ApplicationStartUp.java class. [context= /]
	
	

API Documentation & Testing
============================
http://HOSTNAME:PORT/CONTEXT/swagger-ui.html

 e.g. http://localhost:8080/swagger-ui.html
 				or
 	http://localhost:8080/agrostar/swagger-ui.html


Testing Guide (Sequence)
=========================

1. /wallet/createwalletType : 	first create walletTyeps (regular,overdraft..etc)
2. /wallet/createwallet		:	then create wallet. pass relevant walletTypeId in request.  mobile no is 10digit
3. /wallet/transact			: 	credit/debit ops.   **txnType = debit/credit
4. /wallet/balance/{customerId}	:	current balance
5. /wallet/passbook/{customerId}	: passbook
6. /wallet/cancel/{txnId}/{txnType} : cancell transaction. **txnType = debit/credit
















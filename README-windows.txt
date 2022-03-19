rmi-e-commerce
--------------------
Kevin BOUDINA
Martin JOSSIC

Le projet est déjà précompilé. Il a été testé avec Java 17 donc il est recommandé d'utiliser cette version pour l'exécuter. Les instructions de compilation sont si besoin disponibles à la fin de ce fichier.

Toutes les commandes sont à exécuter depuis la racine du projet. Le processus banque doit être lancé le premier, ensuite les processus magasin et enfin le processus client. Les commandes ne fonctionnent pas dans PowerShell, elles doivent être saisies depuis l'invite de commande traditionnelle de Windows (cmd.exe).

Exécuter le processus banque :
java -cp bin;deps/sqlite-jdbc.jar com.rmiecommerce.bank.Bank 4003 db/bank.db

Exécuter le processus magasin Brico3000 :
java -cp bin;deps/sqlite-jdbc.jar com.rmiecommerce.shop.Shop 3007 db/shop-brico3000.db 127.0.0.1 4003

Exécuter le processus magasin Biomarket :
java -cp bin;deps/sqlite-jdbc.jar com.rmiecommerce.shop.Shop 3008 db/shop-biomarket.db 127.0.0.1 4003

Exécuter le processus magasin SportMax :
java -cp bin;deps/sqlite-jdbc.jar com.rmiecommerce.shop.Shop 3009 db/shop-sportmax.db 127.0.0.1 4003

Exécuter le processus client (interface graphique) :
java -cp bin --module-path deps/javafx-windows/lib --add-modules javafx.controls com.rmiecommerce.client.Client

Des comptes bancaires ont été crées en guise de démonstration. Leurs identifiants sont disponibles dans setup-sql/bank.sql

Pour recompiler dans l'ordre : le programme banque, le programme magasin et le programme client :

javac -d bin -cp src/main/java src/main/java/com/rmiecommerce/bank/Bank.java
javac -d bin -cp src/main/java src/main/java/com/rmiecommerce/shop/Shop.java
javac -d bin -cp src/main/java --module-path deps/javafx-windows/lib --add-modules javafx.controls src/main/java/com/rmiecommerce/client/Client.java

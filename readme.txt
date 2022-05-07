Projekt:
	-> UML editor do předmětu IJA na VUT FIT v roce 2021/2022

Autoři projektu:
	-> Marek Bitomský - xbitom00
	-> Ondrěj Darmopil - xdarmo03
	-> Vladimír Horák - xhorak72

Základní popis projektu:
	-> Cílem projektu je vytvořit UML editor podporující sekvenční diagram, diagram tříd 
		a komunikace v programovacím jazyce Java včetně grafického uživatelského rozhraní.
	-> Projekt využívá Javu verze 11.
	-> Projekt využívá Maven pro správu, řízení a automatizaci buildů aplikace.
	-> Projekt využívá knihovny:
		-> JavaFX pro GUI
		-> GSON pro ukládání tříd do JSON
	-> Součástí momentální implementace je základní GUI, které umí pracovat
		s diagramem tříd, konkrétně akce jako vytváření, mazání entit, atributů
		a relací.

Překlad:
	-> mvn clean validate compile test package verify install javadoc:jar -f pom.xml

Spuštění:
	-> Po překladu se vytvoří "ija-app.jar" ve složce /dest, přes který se aplikace spouští.

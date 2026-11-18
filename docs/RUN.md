# Comment executer

Prerequis : JDK 17 ou plus (`java -version` et `javac -version`).

## Git Bash (depuis la racine du projet)
```bash
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out com.uniaccounts.Main        # Travail 1 : Builder, Singleton, Prototype
java -cp out com.uniaccounts.MainWork2   # Travail 2 : State, Observer, Strategy, Composite, Adapter, Decorator, Facade
```

## PowerShell
```powershell
mkdir out -Force
javac -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out com.uniaccounts.MainWork2
```

## VS Code
Ouvrir `src/com/uniaccounts/MainWork2.java` puis cliquer sur **Run** au-dessus de `main`.

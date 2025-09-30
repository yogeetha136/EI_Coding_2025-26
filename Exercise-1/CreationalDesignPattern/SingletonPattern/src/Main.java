import SingletonExample.Singleton;
public class Main{
    public static void main(String[] args) {
        //If you see the same value, then singleton was reused 
        //If you see different values, then 2 singletons were created
        System.out.println("RESULT:" + "\n");
        Singleton singleton = Singleton.getInstance("FOO");
        Singleton anotherSingleton = Singleton.getInstance("BAR");
        System.out.println(singleton.value);
        System.out.println(anotherSingleton.value);
    }
}
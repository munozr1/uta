public interface Modifiers {

    private static void helloWorld() {
        System.out.println("Hello World!");
    }

    default void helloWorld2() {
        helloWorld();
    }

}

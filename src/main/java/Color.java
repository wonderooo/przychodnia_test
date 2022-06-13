public enum Color {
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    BLUE("\u001B[34m"),
    YELLOW("\u001B[33m"),
    RESET("\u001B[0m");


    public final String color;
    private Color(String color) {
        this.color = color;
    }
    @Override
    public String toString(){
        return this.color;
    }
}

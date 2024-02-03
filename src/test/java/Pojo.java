public class Pojo {

        String name;
        String type;
        boolean exotic;



        public Pojo(){}

    public Pojo(String name, String type, boolean exotic) {
        this.name = name;
        this.type = type;
        this.exotic = exotic;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean getExotic() {
        return exotic;
    }
}

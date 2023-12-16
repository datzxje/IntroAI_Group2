package input;

public class Vertex {
    private int id;
    private double x;
    private double y;

    public Vertex(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public static double Distance(Vertex a, Vertex b) {
        double distance = Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
        distance = (double) Math.ceil(distance * 100) / 100; // Làm tròn lấy 2 chữ số phần
        // thập phân.
        return distance;
    }
}

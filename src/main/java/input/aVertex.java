package input;

public class aVertex {
    private int id; // Địa chỉ đỉnh
    private double g; // Khoảng cách từ đỉnh xuất phát đến đỉnh hiện tại
    private double h; // Khoảng cách từ đỉnh hiện tại đến đỉnh đích
    private double f; // Tổng khoảng cách (f = g + h)

    public aVertex(int id, double g, double h) {
        this.id = id;
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }
}

package stanislav.vasilina.speditionclient.entity;

public class Weight {
    private int id;
    private float gross;
    private float tare;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public float getGross() {
        return gross;
    }
    public void setGross(float gross) {
        this.gross = gross;
    }

    public float getTare() {
        return tare;
    }
    public void setTare(float tare) {
        this.tare = tare;
    }
}

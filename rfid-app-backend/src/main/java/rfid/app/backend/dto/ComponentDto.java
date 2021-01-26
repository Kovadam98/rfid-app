package rfid.app.backend.dto;

public class ComponentDto {
    private int id;
    private boolean isReal;
    private String name;
    private String color;
    private String url;
    private int hue;
    private int brightness;
    private int saturation;

    public ComponentDto(){ }

    public ComponentDto(int id, boolean isReal, String name, String color, String url, int hue, int brightness, int saturation ){
        this.id = id;
        this.isReal = isReal;
        this.name = name;
        this.color = color;
        this.url = url;
        this.hue = hue;
        this.brightness = brightness;
        this.saturation = saturation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isReal() {
        return isReal;
    }

    public void setReal(boolean real) {
        isReal = real;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }
}

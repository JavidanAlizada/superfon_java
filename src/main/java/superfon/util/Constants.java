package superfon.util;

public enum Constants {

    PROPFILE("application.properties");
    private String propFile;

    private Constants(String propFile) {
        this.propFile = propFile;
    }

    public String getPropfile() {
        return propFile;
    }
}

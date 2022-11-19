package model;

public interface Advertisable {

    /**
     * <pre>
     * <strong>Description: </strong> It decides whether to show an advertisement or not. For standard users it is true each 3 songs or 1 podcast
     * @return status <strong>boolean</strong> true when showing advertisement.
     * </pre>
     */
    boolean showAdvertise();
}

package Animation;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/*
The code for this class was taken from (with minor changes):
https://netopyr.com/2012/03/09/creating-a-sprite-animation-with-javafx/ (last visited 21.12.2023)
 */
public class SpriteAnimation extends Transition {

    private final static int DURATION_OF_SINGLE_FRAME = 100;
    private ImageView imageView;
    private int totalFrames;
    private int columns;
    private int offsetX;
    private int offsetY;
    private int width;
    private int height;

    private Duration duration;

    private int lastIndex;

    public SpriteAnimation(ImageView imageView, int totalFrames, int columns, int offsetX, int offsetY, int width, int height) {
        this.imageView = imageView;
        this.totalFrames = totalFrames;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;

        duration = Duration.millis(DURATION_OF_SINGLE_FRAME * totalFrames);
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double frac) {
        int index = Math.min((int) Math.floor(frac * totalFrames), totalFrames - 1);
        if (index != lastIndex) {
            int x = (index % columns) * width + offsetX;
            int y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;
        }
    }
}

package cloud.lemonslice.silveroak.client.texture;

public class TexturePos {
    private final int textureX;
    private final int textureY;
    private final int width;
    private final int height;

    public TexturePos(int textureX, int textureY, int width, int height) {
        this.textureX = textureX;
        this.textureY = textureY;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return this.textureX;
    }

    public int getY() {
        return this.textureY;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}

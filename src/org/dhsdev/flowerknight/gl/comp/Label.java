package org.dhsdev.flowerknight.gl.comp;

import org.dhsdev.flowerknight.gl.*;
import org.dhsdev.flowerknight.util.Logger;
import org.dhsdev.flowerknight.util.Severity;

import java.io.IOException;

public class Label extends Component  {

    private String text;
    private float x;
    private float y;
    private float offsetX;
    private float offsetY;
    private Mesh mesh;
    private Texture tex;
    private Shader shader;

    public Label(String name, String text, float x, float y, float offsetX, float offsetY) {
        super(name);
        this.text = text;
        this.x = x;
        this.y = y;

        try {
            shader = new Shader("src/shader/trivial_vert.glsl", "src/shader/trivial_frag.glsl");
        } catch (GLException | IOException e) {
            Logger.log("Could not create trivial shader", Severity.ERROR);
        }

        var positions = new float[] {
                -x + offsetX, -y + offsetY,
                -x + offsetX,  y + offsetY,
                x + offsetX,  y + offsetY,
                x + offsetX, -y + offsetY,
        };

        var indices = new int[] {
                0, 1, 3, 3, 1, 2,
        };

        var texCoords = new float[] {
                0, 1, 0, 0, 1, 0, 1, 1,
        };

        mesh = new Mesh(positions, indices, texCoords);

        try {
            tex = new Texture("res/logo.png");
        } catch (IOException e) {
            Logger.log("Could not load texture", Severity.ERROR);
        }


    }

    @Override
    public void draw() {
        shader.bind();
        mesh.render(tex.id());
    }

    @Override
    public void clear() {
        mesh.destroy();
        shader.destroy();
        tex.delete();
    }

}


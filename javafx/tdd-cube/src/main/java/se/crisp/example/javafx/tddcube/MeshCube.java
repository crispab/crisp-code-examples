package se.crisp.example.javafx.tddcube;

import javafx.scene.shape.TriangleMesh;

public class MeshCube extends TriangleMesh {

    public MeshCube(float w, float h, float d) {
        float hw = w / 2f;
        float hh = h / 2f;
        float hd = d / 2f;

        float x0 = 0f;
        float x1 = 1f / 4f;
        float x2 = 2f / 4f;
        float x3 = 3f / 4f;
        float x4 = 1f;
        float y0 = 0f;
        float y1 = 1f / 3f;
        float y2 = 2f / 3f;
        float y3 = 1f;

        this.getPoints().addAll(
                hw, hh, hd,   //point A
                hw, hh, -hd,  //point B
                hw, -hh, hd,  //point C
                hw, -hh, -hd, //point D
                -hw, hh, hd,  //point E
                -hw, hh, -hd, //point F
                -hw, -hh, hd, //point G
                -hw, -hh, -hd //point H
        );
        this.getTexCoords().addAll(
                x1, y0,
                x2, y0,
                x0, y1,
                x1, y1,
                x2, y1,
                x3, y1,
                x4, y1,
                x0, y2,
                x1, y2,
                x2, y2,
                x3, y2,
                x4, y2,
                x1, y3,
                x2, y3
        );
        this.getFaces().addAll(
                0, 10, 2, 5, 1, 9,   //triangle A-C-B
                2, 5, 3, 4, 1, 9,    //triangle C-D-B
                4, 7, 5, 8, 6, 2,    //triangle E-F-G
                6, 2, 5, 8, 7, 3,    //triangle G-F-H
                0, 13, 1, 9, 4, 12,  //triangle A-B-E
                4, 12, 1, 9, 5, 8,   //triangle E-B-F
                2, 1, 6, 0, 3, 4,    //triangle C-G-D
                3, 4, 6, 0, 7, 3,    //triangle D-G-H
                0, 10, 4, 11, 2, 5,  //triangle A-E-C
                2, 5, 4, 11, 6, 6,   //triangle C-E-G
                1, 9, 3, 4, 5, 8,    //triangle B-D-F
                5, 8, 3, 4, 7, 3     //triangle F-D-H
        );
        this.getFaceSmoothingGroups().addAll(
                0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5
        );
    }
}

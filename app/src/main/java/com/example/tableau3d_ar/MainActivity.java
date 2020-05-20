package com.example.tableau3d_ar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {



        private ArFragment arFragment; // -------------------------------------> LE FRAGMENT
        private ModelRenderable whiteSphereRenderable; // ---------------------> L'OBJET


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.ux_fragment); // CREATION DU FRAGMENT

           /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.WHITE))//    CREATION DE L'OBJET
                    .thenAccept(                                                                      //    Pour changer par un objet 3D
                            material-> {                                                              //  FAIRE UN ModelRenderable.builder
                                whiteSphereRenderable =                                               //  après avoir généré les .sfa et .sfb avec Import Sceneform Asset
                                        ShapeFactory.makeSphere(0.1f, new Vector3(0.0f, 0.15f, 0.0f), material); });
           ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

           ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            arFragment.setOnTapArPlaneListener(                                      //
                    (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> { //
                        if (whiteSphereRenderable == null) {                             //
                            return;                                                  //     CREATION DU LISTENER POUR PLACER L'OBJET SUR UN CLIC
                        }                                                            //
                        if (plane.getType() != Plane.Type.HORIZONTAL_UPWARD_FACING) {//
                            return;                                                  //
                        }                                                            //
           ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

           //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        Anchor anchor = hitResult.createAnchor();                    //
                        AnchorNode anchorNode = new AnchorNode(anchor);              //     CREATION DU POINT D'ANCRAGE DE L'OBJET
                        anchorNode.setParent(arFragment.getArSceneView().getScene());//
           /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        TransformableNode Node = new TransformableNode(arFragment.getTransformationSystem());//
                        Node.setParent(anchorNode);                                                          //
                        Node.setRenderable(whiteSphereRenderable);                                               // ON ATTACHE L'OBJET AU POINT D'ENCRAGE DU DESSUS
                        Node.select();                                                                       //
                      /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                    }
            );

        }
}
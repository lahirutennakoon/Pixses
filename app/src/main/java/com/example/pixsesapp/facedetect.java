package com.example.pixsesapp;

import android.os.Bundle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;



public class facedetect extends AppCompatActivity {

    ImageView imageView;
    Button btnprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        imageView=(ImageView)findViewById(R.id.faced);
        btnprogress=(Button)findViewById(R.id.btnprogress);


//Crating a new bitmap decode resources method from the bitmap class
        final Bitmap mybitmap= BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.friendsgroup);
        imageView.setImageBitmap(mybitmap);

        final Paint rectPaint=new Paint();
        rectPaint.setStrokeWidth(5);
        rectPaint.setColor(Color.WHITE);
        rectPaint.setStyle(Paint.Style.STROKE);

        //Canvas to display the bitmap
        final Bitmap tempBitmap=Bitmap.createBitmap(mybitmap.getWidth(),mybitmap.getHeight(),Bitmap.Config.RGB_565);
        final Canvas canvas=new Canvas(tempBitmap);
        canvas.drawBitmap(mybitmap,0,0,null);

        btnprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceDetector faceDetector=new FaceDetector.Builder(getApplicationContext())
                        .setTrackingEnabled(false).setLandmarkType(FaceDetector.ALL_LANDMARKS)
                        .setMode(FaceDetector.FAST_MODE).build();

                if(!faceDetector.isOperational()){

                    Toast.makeText(facedetect.this,"Could not detect the face detection on this device",Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                Frame frame=new Frame.Builder().setBitmap(mybitmap).build();
                SparseArray<Face> sparseArray=faceDetector.detect(frame);

                //Width and height to the bitmap

                for(int i=0;i<sparseArray.size();i++){
                    Face face=sparseArray.valueAt(i);
                    float x1=face.getPosition().x;
                    float y1=face.getPosition().y;
                    float x2=x1+face.getWidth();
                    float y2=y1+face.getHeight();
                    RectF rectF=new RectF(x1,y1,x2,y2);
                    canvas.drawRoundRect(rectF,2,2,rectPaint);
                }
                imageView.setImageDrawable(new BitmapDrawable(getResources(),tempBitmap));

            }
        });




    }
}

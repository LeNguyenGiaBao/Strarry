package hcmute.edu.vn.mssv18110251;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import hcmute.edu.vn.mssv18110251.Adapter.SharePreferenceClass;
import hcmute.edu.vn.mssv18110251.Model.Account;
import hcmute.edu.vn.mssv18110251.mobilefacenet.MobileFaceNet;
import hcmute.edu.vn.mssv18110251.mtcnn.Align;
import hcmute.edu.vn.mssv18110251.mtcnn.Box;
import hcmute.edu.vn.mssv18110251.mtcnn.MTCNN;

public class FaceRecognizeActivity extends AppCompatActivity {

    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private MTCNN mtcnn; // Model Face Detection
    private MobileFaceNet mfn; // Model Face Comparison
    private byte[] face_account;
    private Bitmap embedding_account;
    private TextView result, match;
    private SharePreferenceClass SharedPreferenceClass;
    private static boolean start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognize);
        try {
            mtcnn = new MTCNN(getAssets());
            mfn = new MobileFaceNet(getAssets());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Account account1 = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");
        if(account1!=null){
            if(account1.getRole()==0){
                if(account1.getImage()==null)
                {
                    Intent homePage = new Intent(getApplicationContext(), CategoryActivity.class);
                    startActivity(homePage);
                }
            }
        }

//        Intent intent = this.getIntent();
//        face_account = intent.getByteArrayExtra("FACE");
        Bitmap bitmap_face_account = BitmapFactory.decodeByteArray(account1.getImage(), 0, account1.getImage().length);
        embedding_account = extract_feature(bitmap_face_account);
        start = true;

        result = findViewById(R.id.result);
        match = findViewById(R.id.match);

        previewView = findViewById(R.id.previewView);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindImageAnalysis(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));

    }

    private void bindImageAnalysis(@NonNull ProcessCameraProvider cameraProvider) {
        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder().setTargetResolution(new Size(300, 300))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new ImageAnalysis.Analyzer() {

            @Override
            public void analyze(@NonNull ImageProxy imageProxy) {
                @SuppressLint("UnsafeExperimentalUsageError")

                // Camera Feed-->Analyzer-->ImageProxy-->mediaImage-->InputImage(needed for ML kit face detection)
                Image mediaImage = imageProxy.getImage();

                if(start) {
                    if (mediaImage != null) {
                        Bitmap frame_bmp = toBitmap(mediaImage);
                        int rot = imageProxy.getImageInfo().getRotationDegrees();

    //                    Adjust orientation of Face
                        Bitmap frame_bmp1 = rotateBitmap(frame_bmp, rot, true, false);

                        if (frame_bmp != null && !frame_bmp.isRecycled()) {
                            frame_bmp.recycle();
                            frame_bmp = null;
                        }
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    Log.d("Frame", String.valueOf(start));
                                    Bitmap face = extract_feature(frame_bmp1);
                                    faceCompare(face, embedding_account);
                                }
                            });
                        }
                } else {
                    Log.d("False ne", "-------------------------------------------------------------------------------------------------");
                }
                imageProxy.close(); //v.important to acquire next frame for analysis

            }
        });
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT).build();
        preview.setSurfaceProvider(previewView.createSurfaceProvider());
        cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, imageAnalysis, preview);
    }

    private void faceCompare(Bitmap bitmapCrop1, Bitmap bitmapCrop2) {
        if (bitmapCrop1 == null || bitmapCrop2 == null) {
            start = true;
//            Toast.makeText(this, "Please detect the face first", Toast.LENGTH_LONG).show();
            return;
        }
        start = false;
        Log.d(String.valueOf(start), "-------------------------------------------------------------------------------------------------");


        float same = mfn.compare(bitmapCrop1, bitmapCrop2); // 就这一句有用代码，其他都是UI

        String text = "Face comparison result：" + same;
        Log.d("faceCompare", text);
        result.setText(String.valueOf(same));

        if (same > MobileFaceNet.THRESHOLD) {
            match.setText("MATCH");
            start = false;
            Log.d("Match", String.valueOf(start));
//            try {
//                Thread.sleep(2000);  //Camera preview refreshed every 100 millisec(adjust as required)
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Toast.makeText(getBaseContext(), "Face Match", Toast.LENGTH_LONG).show();
            Intent homePage = new Intent(getApplicationContext(), CategoryActivity.class);
            startActivity(homePage);
            return;
        }
        start = true;
    }

    private Bitmap extract_feature(Bitmap bitmap){
        Log.d("extract_feature", "-------------------------------------------");
        start = false;
        if(bitmap == null){
            start = true;
            return null;
        }
        Bitmap bitmapTemp = bitmap.copy(bitmap.getConfig(), false);
        Vector<Box> boxes = mtcnn.detectFaces(bitmapTemp, bitmapTemp.getWidth() / 5); // 只有这句代码检测人脸，下面都是根据Box在图片中裁减出人脸
        if(boxes.size()==0){
            start = true;
            return null;
        }
        Box box = boxes.get(0);
        bitmapTemp = Align.face_align(bitmapTemp, box.landmark);
        boxes = mtcnn.detectFaces(bitmapTemp, bitmapTemp.getWidth() / 5);
        if(boxes.size()==0){
            start=true;
            return null;
        }
        box = boxes.get(0);
        box.toSquareShape();
        box.limitSquare(bitmapTemp.getWidth(), bitmapTemp.getHeight());
        Rect rect = box.transform2Rect();
        Bitmap bitmapCrop = MyUtil.crop(bitmapTemp, rect);
        return bitmapCrop;
    }

    private Bitmap toBitmap(Image image) {

        byte[] nv21=YUV_420_888toNV21(image);


        YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, image.getWidth(), image.getHeight(), null);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 75, out);

        byte[] imageBytes = out.toByteArray();
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    //IMPORTANT. If conversion not done ,the toBitmap conversion does not work on some devices.
    private static byte[] YUV_420_888toNV21(Image image) {

        int width = image.getWidth();
        int height = image.getHeight();
        int ySize = width*height;
        int uvSize = width*height/4;

        byte[] nv21 = new byte[ySize + uvSize*2];

        ByteBuffer yBuffer = image.getPlanes()[0].getBuffer(); // Y
        ByteBuffer uBuffer = image.getPlanes()[1].getBuffer(); // U
        ByteBuffer vBuffer = image.getPlanes()[2].getBuffer(); // V

        int rowStride = image.getPlanes()[0].getRowStride();
        assert(image.getPlanes()[0].getPixelStride() == 1);

        int pos = 0;

        if (rowStride == width) { // likely
            yBuffer.get(nv21, 0, ySize);
            pos += ySize;
        }
        else {
            long yBufferPos = -rowStride; // not an actual position
            for (; pos<ySize; pos+=width) {
                yBufferPos += rowStride;
                yBuffer.position((int) yBufferPos);
                yBuffer.get(nv21, pos, width);
            }
        }

        rowStride = image.getPlanes()[2].getRowStride();
        int pixelStride = image.getPlanes()[2].getPixelStride();

        assert(rowStride == image.getPlanes()[1].getRowStride());
        assert(pixelStride == image.getPlanes()[1].getPixelStride());

        if (pixelStride == 2 && rowStride == width && uBuffer.get(0) == vBuffer.get(1)) {
            // maybe V an U planes overlap as per NV21, which means vBuffer[1] is alias of uBuffer[0]
            byte savePixel = vBuffer.get(1);
            try {
                vBuffer.put(1, (byte)~savePixel);
                if (uBuffer.get(0) == (byte)~savePixel) {
                    vBuffer.put(1, savePixel);
                    vBuffer.position(0);
                    uBuffer.position(0);
                    vBuffer.get(nv21, ySize, 1);
                    uBuffer.get(nv21, ySize + 1, uBuffer.remaining());

                    return nv21; // shortcut
                }
            }
            catch (ReadOnlyBufferException ex) {
                // unfortunately, we cannot check if vBuffer and uBuffer overlap
            }

            // unfortunately, the check failed. We must save U and V pixel by pixel
            vBuffer.put(1, savePixel);
        }

        // other optimizations could check if (pixelStride == 1) or (pixelStride == 2),
        // but performance gain would be less significant

        for (int row=0; row<height/2; row++) {
            for (int col=0; col<width/2; col++) {
                int vuPos = col*pixelStride + row*rowStride;
                nv21[pos++] = vBuffer.get(vuPos);
                nv21[pos++] = uBuffer.get(vuPos);
            }
        }

        return nv21;
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int rotationDegrees, boolean flipX, boolean flipY) {
        Matrix matrix = new Matrix();

        // Rotate the image back to straight.
        matrix.postRotate(rotationDegrees);

        // Mirror the image along the X or Y axis.
        matrix.postScale(flipX ? -1.0f : 1.0f, flipY ? -1.0f : 1.0f);
        Bitmap rotatedBitmap =
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        // Recycle the old bitmap if it has changed.
        if (rotatedBitmap != bitmap) {
            bitmap.recycle();
        }
        return rotatedBitmap;
    }
}
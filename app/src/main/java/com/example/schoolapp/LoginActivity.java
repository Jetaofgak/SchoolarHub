package com.example.schoolapp;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoginActivity extends AppCompatActivity implements SensorEventListener {

    private List<ImageView> imageViews; // List of ImageViews that you want to randomize
    private boolean isSensorActive = false; // Flag to control whether sensor readings should be active
    private float[] currentCoordinates; // To store the sensor data (e.g., X, Y, Z)
    private SensorManager sensorManager;
    private Sensor sensor;
    float sensorSens = 15;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize ImageViews list
        imageViews = new ArrayList<>();
        // Assuming you have ImageViews in your layout (update ids as necessary)
        imageViews.add(findViewById(R.id.imageView));
        imageViews.add(findViewById(R.id.imageView2));
        imageViews.add(findViewById(R.id.imageView3));
        imageViews.add(findViewById(R.id.imageView4));
        imageViews.add(findViewById(R.id.imageView5));
        imageViews.add(findViewById(R.id.imageView6));
        imageViews.add(findViewById(R.id.imageView7));

        // Get the parent layout (e.g., a ViewGroup that contains the ImageViews)
        View parentLayout = findViewById(R.id.background); // Replace with your actual parent layout ID

        // Wait for layout pass to get the image sizes and randomize positions
        parentLayout.post(new Runnable() {
            @Override
            public void run() {
                // Get screen dimensions
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);

                int screenWidth = metrics.widthPixels;
                int screenHeight = metrics.heightPixels;

                // Random object to generate random positions
                Random random = new Random();

                for (ImageView imageView : imageViews) {
                    // Get image dimensions
                    int imageWidth = imageView.getWidth();
                    int imageHeight = imageView.getHeight();

                    // Ensure the image is fully inside the screen by subtracting the image's width/height from the bounds
                    int randomX = random.nextInt(screenWidth - imageWidth); // Random X coordinate
                    int randomY = random.nextInt(screenHeight - imageHeight); // Random Y coordinate

                    // Set the image's position to the random coordinates
                    imageView.setX(randomX);
                    imageView.setY(randomY);
                }

                // After placing all images, check for collisions and adjust positions if necessary
                //resolveImageCollisions();
            }
        });

        // Initialize sensor manager and sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // You can change to Gyroscope if needed
        }
    }
    public void onLoginClick(View view)
    {
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);

    }
    public void onForgetClick(View view)
    {

    }

    // Method to start sensor updates
    private void startSensor() {
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
            isSensorActive = true;
        } else {
            Toast.makeText(this, "Sensor not available", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to stop sensor updates
    private void stopSensor() {
        if (isSensorActive) {
            sensorManager.unregisterListener(this);
            isSensorActive = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Store the sensor data (this example uses the accelerometer's values)
            currentCoordinates = event.values.clone();
            // Call the method to update the image positions based on sensor data
            updateImagePositions(currentCoordinates);
        }
    }

    // Method to update the image positions based on sensor data
    private void updateImagePositions(float[] coordinates) {
        if (coordinates != null) {
            // You can manipulate the coordinates to change the images' positions
            float sensorX = coordinates[0]; // Assuming this is the X-axis reading
            float sensorY = coordinates[1]; // Assuming this is the Y-axis reading

            for (ImageView imageView : imageViews) {
                // Update the positions based on the sensor values (you can scale or map them to screen coordinates)

                imageView.setX(imageView.getX() + sensorX * sensorSens);
                imageView.setY(imageView.getY() + sensorY * sensorSens);

                // Optional: Add checks to prevent images from going off-screen (within bounds)
                checkImageBounds(imageView);
            }

            // After moving images, check for collisions and resolve
            //resolveImageCollisions();
        }
    }

    // Method to ensure the image stays within the screen bounds
    private void checkImageBounds(ImageView imageView) {
        // Get screen dimensions
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        // Get image dimensions
        int imageWidth = imageView.getWidth();
        int imageHeight = imageView.getHeight();

        // Check for horizontal boundaries
        if (imageView.getX() < 0) {
            imageView.setX(0);
        } else if (imageView.getX() + imageWidth > screenWidth) {
            imageView.setX(screenWidth - imageWidth);
        }

        // Check for vertical boundaries
        if (imageView.getY() < 0) {
            imageView.setY(0);
        } else if (imageView.getY() + imageHeight > screenHeight) {
            imageView.setY(screenHeight - imageHeight);
        }
    }

    // Method to resolve image collisions


    // Method to adjust image positions after collision
    private void adjustImagePositions(ImageView imageView1, ImageView imageView2) {
        // Simple approach: move imageView2 to the right of imageView1 (or vice versa)
        imageView2.setX(imageView1.getX() + imageView1.getWidth() + 10); // Adding a small buffer space
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Can be used to handle sensor accuracy changes (optional)
    }

    @Override
    protected void onResume() {
        super.onResume();
        startSensor(); // Start listening for sensor updates when activity is resumed
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopSensor(); // Stop listening for sensor updates when activity is paused
    }
}
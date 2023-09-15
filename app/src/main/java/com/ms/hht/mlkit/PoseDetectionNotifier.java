package com.ms.hht.mlkit;

import android.graphics.Canvas;

import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseLandmark;
import com.ms.hht.data.BodyValidationFields;

import java.util.List;

public interface PoseDetectionNotifier {
    void humanDetected(List<PoseLandmark> landmarks, Pose pose, Canvas canvas);
    void nextMesage(String mesage);

    void readyToCapture(String pose_complete);
    BodyValidationFields getBodyValidationField();
    void setBodyValidationField(BodyValidationFields validationField);
}

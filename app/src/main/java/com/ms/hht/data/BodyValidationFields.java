package com.ms.hht.data;

import com.google.mlkit.vision.pose.PoseLandmark;

import java.util.ArrayList;
import java.util.List;

public class BodyValidationFields {
    public BodyValidationFields(Double armsAngle, List<PoseLandmark> landmarks, Double objDistanceCamera, Double objDistanceLegs, Double personSidePose) {
        this.armsAngle = armsAngle;
        this.landmarks = landmarks;
        this.isFullBodyVisible = isFullBodyVisible;
        this.objDistanceCamera = objDistanceCamera;
        this.objDistanceLegs = objDistanceLegs;
        this.personSidePose = personSidePose;
    }

    public Double armsAngle = -1.0;
    public List<PoseLandmark> landmarks=new ArrayList<>();
    public boolean isFullBodyVisible = false;
    public Double objDistanceCamera = -1.0;
    public Double objDistanceLegs = -1.0;
    public Double personSidePose = -1.0;
}

package org.carobotics.hardware;

/**
 * @author CaRobotics
 */
public class Camera {

    private edu.wpi.first.wpilibj.camera.AxisCamera camera;
    static Camera currentCamera;
    static boolean created = false;

    public static class Resolution {
        public static final edu.wpi.first.wpilibj.camera.AxisCamera.ResolutionT k160x120 = edu.wpi.first.wpilibj.camera.AxisCamera.ResolutionT.k160x120;
        public static final edu.wpi.first.wpilibj.camera.AxisCamera.ResolutionT k320x240 = edu.wpi.first.wpilibj.camera.AxisCamera.ResolutionT.k320x240;
        public static final edu.wpi.first.wpilibj.camera.AxisCamera.ResolutionT k640x360 = edu.wpi.first.wpilibj.camera.AxisCamera.ResolutionT.k640x360;
        public static final edu.wpi.first.wpilibj.camera.AxisCamera.ResolutionT k640x480 = edu.wpi.first.wpilibj.camera.AxisCamera.ResolutionT.k640x480;
    }
    
    public static class WhiteBalance {
        public static final edu.wpi.first.wpilibj.camera.AxisCamera.WhiteBalanceT automatic = edu.wpi.first.wpilibj.camera.AxisCamera.WhiteBalanceT.automatic;
        public static final edu.wpi.first.wpilibj.camera.AxisCamera.WhiteBalanceT fixedFlour1 = edu.wpi.first.wpilibj.camera.AxisCamera.WhiteBalanceT.fixedFlour1;
        public static final edu.wpi.first.wpilibj.camera.AxisCamera.WhiteBalanceT fixedFlour2 = edu.wpi.first.wpilibj.camera.AxisCamera.WhiteBalanceT.fixedFlour2;
        public static final edu.wpi.first.wpilibj.camera.AxisCamera.WhiteBalanceT fixedIndoor = edu.wpi.first.wpilibj.camera.AxisCamera.WhiteBalanceT.fixedIndoor;
        public static final edu.wpi.first.wpilibj.camera.AxisCamera.WhiteBalanceT fixedOutdoor1 = edu.wpi.first.wpilibj.camera.AxisCamera.WhiteBalanceT.fixedOutdoor1;
        public static final edu.wpi.first.wpilibj.camera.AxisCamera.WhiteBalanceT fixedOutdoor2 = edu.wpi.first.wpilibj.camera.AxisCamera.WhiteBalanceT.fixedOutdoor2;
        public static final edu.wpi.first.wpilibj.camera.AxisCamera.WhiteBalanceT hold = edu.wpi.first.wpilibj.camera.AxisCamera.WhiteBalanceT.hold;
    }

    private Camera(String address) {
        this.camera = edu.wpi.first.wpilibj.camera.AxisCamera.getInstance(address);
    }

    private Camera() {
        this.camera = edu.wpi.first.wpilibj.camera.AxisCamera.getInstance();
    }

    public static Camera getInstance(String address) {
        if (!created) {
            Camera cam = new Camera(address);
            created = true;
            currentCamera = cam;
            return cam;
        } else {
            return currentCamera;
        }
    }

    public static Camera getInstance() {
        if (!created) {
            Camera cam = new Camera();
            created = true;
            currentCamera = cam;
            return cam;
        } else {
            return currentCamera;
        }
    }

    public void writeBrightness(int brightness) {
        camera.writeBrightness(brightness);
    }

    public void writeColorLevel(int value) {
        camera.writeColorLevel(value);
    }
    
    public void writeCmpression(int value) {
        camera.writeCompression(value);
    }

    public void writeMaxFPS(int value) {
        camera.writeMaxFPS(value);
    }

    public void writeResolution(edu.wpi.first.wpilibj.camera.AxisCamera.ResolutionT resolution) {
        camera.writeResolution(resolution);
    }
    
    public void writeWhiteBalance(edu.wpi.first.wpilibj.camera.AxisCamera.WhiteBalanceT balance){
        camera.writeWhiteBalance(balance);
    }

    public edu.wpi.first.wpilibj.image.ColorImage getImage() throws Exception {
        return camera.getImage();
    }
}

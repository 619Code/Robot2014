package org.carobotics.subsystems;

import edu.wpi.first.wpilibj.camera.AxisCamera.WhiteBalanceT;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import java.util.Vector;
import org.carobotics.hardware.Camera;

/**
 *
 * @author CaRobotics
 */
public class Vision {

    private Camera camera;

    //gets camera?
    public Vision() {
        camera = Camera.getInstance();
    }

    //gets specific camera?
    public Vision(String address) {
        camera = Camera.getInstance(address);
    }

    //default settings of camera (resolution, colour etc.)
    public void setDefaultSettings() {
        camera.writeColorLevel(75);
        camera.writeResolution(Camera.Resolution.k320x240);
        camera.writeBrightness(50);
        camera.writeCmpression(50);
        camera.writeWhiteBalance(WhiteBalanceT.fixedFlour1);
    }

    //returns camera
    public Camera getCamera() {
        return camera;
    }

    //updates camera with the driver station (to make sure they're compatible?)
    public void updateLCD() {
        edu.wpi.first.wpilibj.DriverStationLCD.getInstance().updateLCD();
    }
    
    //how far it stays in focus?
    public static final double PIX_TO_DISTANCE = 1d;
    
    //?????????
    public static final double GOAL_ASPECT_RATIO = 1.6;
    
    //range for ^ that one
    public static final double GOAL_ASPECT_RATIO_RANGE = 0.1;
    
    //minimum size is dots (pixels) per inch?
    public static final double MINIMUM_PARTICLE_SIZE = 10d;

    //finds distance to target takes in array to average different readings?
    public static double calculateDistanceToGoal(ParticleAnalysisReport[] reports) {
        //array-like thing
        Vector vec = new Vector();
        
        for (int i = 0; i < reports.length; i++) {
            ParticleAnalysisReport report = reports[i];
            //resolution?
            double ratio = (double) report.imageWidth / (double) report.imageHeight;
            //finds if in range (of what? find out GOAL_ASPECT_RATIO) and adds report to the end of the vector
            if (ratio < GOAL_ASPECT_RATIO + GOAL_ASPECT_RATIO_RANGE && ratio > GOAL_ASPECT_RATIO - GOAL_ASPECT_RATIO_RANGE) {
                vec.addElement(report);
            }
        }
        //what is selectedReport?
        ParticleAnalysisReport selectedReport;
        
        double currentWidth = 0d;
        
        //if nothing in the vector then return the highest possible value a double can hold (why though???)
        if (vec.isEmpty()) {
            return Double.MAX_VALUE;
        } 
        //if vector only contains one item then have selectedReport equal that varible (but why type-cast it as (ParticleAnalysisReport) ?)
        else if (vec.size() == 1) {
            selectedReport = (ParticleAnalysisReport) vec.elementAt(0);
        } 
        //
        else {
            for (int i = 0; i < vec.size(); i++) {
                ParticleAnalysisReport checkingReport = (ParticleAnalysisReport) vec.elementAt(i);
                double checkingReportWidth = (double) checkingReport.imageWidth;
                if (checkingReportWidth > currentWidth) {
                    selectedReport = checkingReport;
                    currentWidth = checkingReportWidth;
                }
            }
        }
        return currentWidth * PIX_TO_DISTANCE;
    }

    public edu.wpi.first.wpilibj.image.ParticleAnalysisReport[] getFilteredReports() {
        edu.wpi.first.wpilibj.image.ColorImage img = null;
        edu.wpi.first.wpilibj.image.BinaryImage bimg = null;
        try {
            img = camera.getImage();
            bimg = img.thresholdRGB(0, 100, 200, 255, 0, 255);
            edu.wpi.first.wpilibj.image.ParticleAnalysisReport[] reports = bimg.getOrderedParticleAnalysisReports();

            Vector filteredReports = new Vector(); //Filter

            for (int i = 0; i < reports.length; i++) {
                edu.wpi.first.wpilibj.image.ParticleAnalysisReport report = reports[i];
                if (report.particleArea >= MINIMUM_PARTICLE_SIZE) {
                    filteredReports.addElement(report);
                }
            }

            reports = new edu.wpi.first.wpilibj.image.ParticleAnalysisReport[filteredReports.size()];
            filteredReports.copyInto(reports);

            img.free();
            bimg.free();

            return reports;
        } catch (Exception e) {
        } finally {
            try {
                img.free();
                bimg.free();
            } catch (Exception e) {
            }
        }
        return new edu.wpi.first.wpilibj.image.ParticleAnalysisReport[0];
    }

    public void printData() {
        edu.wpi.first.wpilibj.image.ParticleAnalysisReport[] reports = getFilteredReports();
        if (reports.length == 0) {
            System.out.println("No objects detected");
        } else {
            System.out.print("Objects detected:");
            for (int i = 0; i < reports.length; i++) {
                edu.wpi.first.wpilibj.image.ParticleAnalysisReport report = reports[i];
                if (report.particleToImagePercent > 0.05) {
                    System.out.print(" " + report.center_mass_x + "," + report.center_mass_y + ":" + report.particleToImagePercent + " |");
                }
            }
            System.out.println();
        }
    }

    public static double doubleCutTo(double value, double place) {
        return value - value % place;
    }
}
